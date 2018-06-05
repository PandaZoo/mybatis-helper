package cn.kevin.dialog;

import cn.kevin.action.form.AddFieldForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.fest.util.Strings;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * dialog类
 * created by yongkang.zhang
 * added at 2018/2/1
 */
public class MybatisHelperDialog extends DialogWrapper {

    private MybatisHelperForm helperForm;

    public MybatisHelperDialog(Project project) {
        super(project);
        super.init();
        super.setTitle("Add Field");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        this.helperForm = new MybatisHelperForm();
        return this.helperForm.getMainPanel();
    }

    @Override
    protected void doOKAction() {
        ValidationInfo validationInfo = doValidate();
        if (validationInfo == null) {
            super.close(0);
        }
    }

    @Override
    public void doCancelAction() {
        super.doCancelAction();
    }

    public String getFieldType() {
        return (String) this.helperForm.getFieldType().getSelectedItem();
    }

    public String getFiledName() {
        return this.helperForm.getFieldName().getText();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        String text = getFiledName();
        if (Strings.isNullOrEmpty(text)) {
            return new ValidationInfo("请输入需要添加的字段名");
        }
        return super.doValidate();
    }
}
