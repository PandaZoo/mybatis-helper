package cn.kevin.factory;

import cn.kevin.xml.ElementAndAttributes;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlTag;

import java.util.ArrayList;

/**
 * 用来添加和设置字段值的工厂类
 * created by yongkang.zhang
 * added at 2018/2/6
 */
public class FieldFacotry {

    public static PsiClass getClassFromFile(final PsiFile psiFile) {
        java.util.List<PsiClass> clazzList = new ArrayList<>();
        psiFile.acceptChildren(new JavaRecursiveElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiClass) {
                    System.out.println("找到了class" + element);
                    clazzList.add((PsiClass) element);
                }
            }
        });
        return clazzList.get(0);
    }

    public static void addField(final Project project, final PsiClass psiClass, String fieldName, String type) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
            PsiField field = elementFactory.createField(fieldName, PsiType.getTypeByName(type, project, GlobalSearchScope.allScope(project)));
            psiClass.add(field);
        });
    }

    public static void addXmlTag(final Project project, final XmlTag parentTag, final XmlTag childTag) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            parentTag.add(childTag);
        });
    }

    public static void setXmlTagValue(final Project project, final ElementAndAttributes.Insert insert, String value) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            insert.setTagValue(value);
        });
    }

}
