package com.sinoiift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xg on 2018/1/4.
 */
@Entity
public class Menu extends Entitys {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;
    /**
     * 菜单名称
     */
    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String text;
    /**
     * 父级菜单ID
     */
    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String pid;
    /**
     * 连接地址
     */
    @Column
    @Getter
    @Setter
    private String url;
    /**
     * 图标
     */
    @Column
    @Getter
    @Setter
    private String iconCls;
    /**
     * 排序
     */
    @Column
    @Getter
    @Setter
    private Integer sort;
    /**
     * 深度
     */
    @Column
    @Getter
    @Setter
    private Integer deep;
    /**
     * 编码
     */
    @Column
    @Getter
    @Setter
    private String code;
    /**
     * 资源名称
     */
    @Column
    @Getter
    @Setter
    private String resource;
    /**
     * Ext试图别名
     */
    @Column
    @Getter
    @Setter
    private String xtype;

    /**
     * 字体图标
     */
    @Column
    @Getter
    @Setter
    private String glyph;
}
