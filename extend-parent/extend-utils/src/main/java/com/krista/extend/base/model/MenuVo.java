package com.krista.extend.base.model;

import java.util.List;

/**
 * MenuVo 菜单视图
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2019/1/4 9:36
 */
public class MenuVo {
    /**
     * 菜单id
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 子菜单
     */
    private List<MenuVo> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
