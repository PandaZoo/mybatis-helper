package cn.kevin.action;

import cn.kevin.dialog.MyNotification;
import cn.kevin.dialog.MybatisHelperDialog;
import cn.kevin.factory.ConvertorFacotry;
import cn.kevin.factory.FieldFacotry;
import cn.kevin.xml.ElementAndAttributes;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomManager;
import org.fest.util.Strings;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sync po and mybatis mapper
 * created by yongkang.zhang
 * added at 2018/2/1
 */
public class MybatisPoSyncAction extends AnAction {

    private final static String INSERT_GOURP_PATTERN = "(\\(.*\\)).*(values).*(\\(.*\\))";

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiFile currentFile = PsiUtilBase.getPsiFileInEditor(Objects.requireNonNull(editor), Objects.requireNonNull(project));
        FileType fileType = Objects.requireNonNull(currentFile).getFileType();
        if (!(fileType instanceof JavaFileType)) {
            MyNotification notification = new MyNotification("非Java文件不可调用", NotificationType.ERROR, null);
            Notifications.Bus.notify(notification, project);
        }
        // show parameter
        String input = showAndGetInput(project);
        if (input == null) {
            return;
        }
        String[] split = input.split("\\s+");
        String type = split[0];
        String propertyName = split[1];

        PsiClass clazz = FieldFacotry.getClassFromFile(currentFile);
        FieldFacotry.addField(project, clazz, propertyName, type);
        handleXml(project, currentFile.getName().substring(0, currentFile.getName().indexOf(".")), propertyName, type);
    }

    private String showAndGetInput(final Project project) {
        MybatisHelperDialog dialog = new MybatisHelperDialog(project);
        dialog.show();
        int exitCode = dialog.getExitCode();
        if (exitCode != 0) {
            return null;
        }
        return dialog.getAddField().getText();
    }



    private void handleXml(final Project project, final String name, String propertyName, String type) {
        // find xmlFile
        XmlFile xmlFile = findXmlFileByName(project, name);
        if (xmlFile != null) {
            writeXml(project, xmlFile, name, propertyName, type);
        }
    }

    private XmlFile findXmlFileByName(final Project project, final String name) {
        PsiFile[] mapperFiles = FilenameIndex.getFilesByName(project, name.concat("Mapper.xml"), GlobalSearchScope.allScope(project));
        if (mapperFiles.length == 0) {
            MyNotification notification = new MyNotification("没有找到对应的xml文件", NotificationType.ERROR, null);
            Notifications.Bus.notify(notification, project);
        }
        if (mapperFiles.length != 1) {
            MyNotification notification = new MyNotification("找到" + mapperFiles.length + "个xml文件, 应该只有一个", NotificationType.ERROR, null);
            Notifications.Bus.notify(notification, project);
        }
        PsiFile psiFile = mapperFiles[0];
        if (psiFile instanceof XmlFile) {
            return (XmlFile) psiFile;
        }
        MyNotification notification = new MyNotification("没有找到对应的xml文件", NotificationType.ERROR, null);
        Notifications.Bus.notify(notification, project);
        return null;
    }

    private void writeXml(final Project project, final XmlFile xmlFile, final String name,final String propertyName, final String type) {
        ElementAndAttributes.Root rootElement = Objects.requireNonNull(DomManager.getDomManager(project).getFileElement(xmlFile, ElementAndAttributes.Root.class)).getRootElement();

        List<ElementAndAttributes.ResultMap> results = rootElement.getResultMaps();
        List<ElementAndAttributes.Sql> sqls = rootElement.getSqls();
        List<ElementAndAttributes.Insert> inserts = rootElement.getInsertTags();

        addToResultMap(project, results, name, propertyName, type);
        addToUpdate(project, sqls, propertyName);
        addToInsert(project, inserts, propertyName);
    }

    private void addToResultMap(final Project project, List<ElementAndAttributes.ResultMap> list, String name, String propertyName, String type) {
        list.forEach(r -> {
            // 只有是这个bean的type才会进行设置
            if (r.getType().toString().endsWith(name)) {
                XmlTag xmlTag = r.getXmlTag();
                XmlTag resultTag = xmlTag.createChildTag("result", xmlTag.getNamespace(), null, false);
                // 设置resultTag的属性
                resultTag.setAttribute("column", ConvertorFacotry.camlToUnderScore(propertyName));
                resultTag.setAttribute("jdbcType", ConvertorFacotry.javaTypeToJdbcType(type));
                resultTag.setAttribute("property", propertyName);
                FieldFacotry.addXmlTag(project, xmlTag, resultTag);
            }
        });
    }

    private void addToUpdate(final Project project, List<ElementAndAttributes.Sql> list, String propertyName) {
        list.forEach(s -> {
            String sqlId = s.getId().toString();
            boolean isUpdate = Pattern.compile(Pattern.quote("update"), Pattern.CASE_INSENSITIVE).matcher(sqlId).find();
            if (isUpdate) {
                XmlTag xmlTag = s.getSetTag().getXmlTag();
                XmlTag ifTag = xmlTag.createChildTag("if", xmlTag.getNamespace(),  "\n" + ConvertorFacotry.camlToUnderScore(propertyName) + " = #{" + propertyName + "},\n", false);
                ifTag.setAttribute("test", propertyName + " != null");
                FieldFacotry.addXmlTag(project, xmlTag, ifTag);
            }
        });
    }

    private void addToInsert(final Project project, List<ElementAndAttributes.Insert> list, String propertyName) {
        list.forEach(i -> {
            // 替换所有的换行符
            String value = i.getValue().replace("\n", "");
            Pattern pattern = Pattern.compile(INSERT_GOURP_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(value);
            String columnStr = "";
            String fieldStr = "";
            while(matcher.find()) {
                columnStr = matcher.group(1);
                fieldStr = matcher.group(3);
            }

            if (!Strings.isNullOrEmpty(columnStr) && !Strings.isNullOrEmpty(fieldStr)) {
                String afterColumnStr = columnStr.replace(")", "," + ConvertorFacotry.camlToUnderScore(propertyName) + ")");
                String temp = value.replace(columnStr, afterColumnStr + "\n");
                String newInsertStr = temp.substring(0, temp.lastIndexOf(")")).concat(", #{" + propertyName + "})");

                // set value
                FieldFacotry.setXmlTagValue(project, i, newInsertStr);
            }
        });

    }


}
