package cn.kevin.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.fest.util.Strings;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * dialog类
 * created by yongkang.zhang
 * added at 2018/2/1
 */
public class MybatisHelperDialog extends DialogWrapper {

    private JPanel jPanel;
    private JTextField addField;
    private Consumer<JTextField> jTextFieldConsumer;

    public MybatisHelperDialog(Project project) {
        super(project);
        super.init();
        super.setTitle("Mybatis Po Sync");
        this.jPanel = null;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        this.jPanel = new JPanel();
        JLabel jLabel = new JLabel("增加字段名:");
        this.addField = new JTextField("", 30);

        this.jPanel.add(jLabel);
        this.jPanel.add(this.addField);
        this.jPanel.setVisible(true);
        return this.jPanel;
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

    public void setjTextFieldConsumer(Consumer<JTextField> jTextFieldConsumer) {
        this.jTextFieldConsumer = jTextFieldConsumer;
    }

    public JTextField getAddField() {
        return addField;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        String text = this.addField.getText();
        if (Strings.isNullOrEmpty(text) || text.split("\\s+").length < 2) {
            return new ValidationInfo("请输入需要添加的内容, 需要包括类型和字段名");
        }
        return super.doValidate();
    }
}
