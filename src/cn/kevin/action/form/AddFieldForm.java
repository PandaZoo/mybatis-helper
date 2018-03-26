package cn.kevin.action.form;

import javax.swing.*;

/**
 * add field form
 * created by yongkang.zhang
 * added at 2018/3/26
 */
public class AddFieldForm {

    private JPanel mainPanel;
    private JLabel Type;
    private JComboBox fieldType;
    private JTextField fieldName;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JLabel getType() {
        return Type;
    }

    public JComboBox getFieldType() {
        return fieldType;
    }

    public JTextField getFieldName() {
        return fieldName;
    }
}
