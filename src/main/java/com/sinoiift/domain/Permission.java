package com.sinoiift.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xg on 2017/12/10.
 */
public class Permission {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long permissionId;
    /**
     * 名称
     */
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    @Column(nullable = false)
    private Byte type;

    /**
     * 权限值
     */
    @Column(nullable = false)
    private String permissionValue;

    /**
     * 路径
     */
    @Column(nullable = true)
    private String uri;

    /**
     * 图标
     */
    @Column(nullable = true)
    private String icon;

    /**
     * 状态(0:禁止,1:正常)
     */
    @Column(nullable = true)
    private Byte status;

    /**
     * 创建时间
     */
    @Column(nullable = true)
    private Long ctime;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }
}
