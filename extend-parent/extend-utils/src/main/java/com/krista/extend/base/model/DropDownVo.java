package com.krista.extend.base.model;

/**
 * DropDownVo
 *
 * @author krista
 * @version V1.0
 * @since 2018/12/23 9:10
 */
public class DropDownVo {
    /**
     * DropDown Value
     */
    private String value;
    /**
     * DropDown Text
     */
    private String text;
    /**
     * 是否选中
     */
    private boolean selected;

    public DropDownVo() {
    }

    public DropDownVo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
