/**
 * Created by jameszhou on 2017/8/9.
 */
Ext.define('AM.controller.MainController', {
    extend: 'Ext.app.Controller',
    views: ['Main', 'Menu', 'Center', 'Welcome', 'Header'],
    stores: [],
    models: [],

    init: function () {
        this.control({
            'viewport > main': {},
            'menu': {
                render: 'initMenu'
            },
            'menu > treepanel': {
                itemclick: 'menuClick'
            }
        });
    },

    //初始化菜单
    initMenu: function (p) {
        var me = this;
        var myMask = new Ext.LoadMask(p, {msg: "Please wait..."});
        myMask.show();
        p.add([
                {
                    xtype: 'treepanel',
                    title: '系统管理',
                    rootVisible: false,
                    layout: 'fit',
                    iconCls: '',
                    glyph: '',
                    root: {
                        expanded: true,
                        children: [
                            {
                                id: '01',
                                xtype: 'userlist',
                                text: '用户管理',
                                leaf: true
                            },
                            {
                                id: '02',
                                xtype: '',
                                text: '角色管理',
                                leaf: true
                                ,
                            },
                            {
                                id: '03',
                                xtype: '',
                                text: '菜单管理',
                                leaf: true
                            }
                        ]
                    }
                },
                {
                    xtype: 'treepanel',
                    title: '部署管理',
                    rootVisible: false,
                    layout: 'fit',
                    iconCls: '',
                    glyph: '',
                    root: {
                        expanded: true,
                        children: [
                            {
                                id: '11',
                                xtype: 'sysEnv',
                                text: '所有柜面环境',
                                leaf: true
                            },
                            {
                                id: '12',
                                text: 'bbbb',
                                leaf: true
                            }
                        ]
                    }
                },
                {
                    xtype: 'treepanel',
                    title: '交易管理',
                    rootVisible: false,
                    layout: 'fit',
                    iconCls: '',
                    glyph: '',
                    root: {
                        expanded: true,
                        children: [
                            {
                                id: '21',
                                text: 'aaaa',
                                leaf: true
                            },
                            {
                                id: '22',
                                text: 'bbbb',
                                leaf: true
                            }
                        ]
                    }
                }
            ]
        );
        me.initTab();
        myMask.hide();
    },

    //菜单点击事件
    menuClick: function (tree, record) {
        this.addTabData(record.raw);
        location.hash = record.raw.xtype;
    },

    //初始化Tab
    initTab: function () {
        this.addTabXtype('welcome');
    },

    //根据数据添加tab
    addTabData: function (data) {
        alert(data.text);
        var id = "_id_" + data.id;
        var tab = Ext.getCmp('centerId');
        var item = tab.getComponent(id);
        if (item) {
            alert(1);
            item.show();
        } else {
            tab.add({
                id: id,
                xtype: data.xtype || 'panel',
                layout: 'fit',
                closable: true,
                title: data.text,
                glyph: eval(data.glyph) || '',
            }).show();
        }
    },
    //根据类型添加tab
    addTabXtype: function (_xtype) {
        var tab = Ext.getCmp('centerId');
        tab.add({
            xtype: _xtype,
        }).show();
    },
});
