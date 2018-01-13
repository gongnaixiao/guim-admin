/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.view.env.Form', {
    extend: 'Ext.form.Panel',
    alias: 'widget.EnvForm',
    width:800,
    height:300,
    layout: {
        type:'hbox',
        align:'stretch'
    },
    requires: ['AM.ux.form.FormPanel'],
    items:[
        {
            xtype:'ux-form',
            flex:2,
            items: [
                {xtype:'hiddenfield',name:'id'},
                {
                    xtype: 'textfield',
                    name : 'name',
                    vtype:'alphanum',
                    id:"name",
                    fieldLabel: '环境名称'
                },
                {
                    xtype: 'textfield',
                    name : 'app',
                    vtype:'alphanum',
                    id:"app",
                    fieldLabel: '应用服务器'
                },
                {
                    xtype: 'textfield',
                    name : 'appIP',
                    vtype:'alphanum',
                    id:"appIP",
                    allowBlank: true,
                    fieldLabel: '应用IP'
                },
                {
                    xtype: 'textfield',
                    name : 'appUserName',
                    vtype:'alphanum',
                    id:"appUserName",
                    allowBlank: true,
                    fieldLabel: '应用用户名'
                },
                {
                    xtype: 'textfield',
                    name : 'appPassWord',
                    vtype:'alphanum',
                    id:"appPassWord",
                    allowBlank: true,
                    fieldLabel: '应用密码'
                },
                {
                    xtype: 'textfield',
                    name : 'dbIP',
                    vtype:'alphanum',
                    id:"dbIP",
                    allowBlank: true,
                    fieldLabel: '数据库IP'
                },
                {
                    xtype: 'textfield',
                    name : 'dbUserName',
                    vtype:'alphanum',
                    id:"dbUserName",
                    allowBlank: true,
                    fieldLabel: '数据库用户名'
                },
                {
                    xtype: 'textfield',
                    name : 'dbPassWord',
                    vtype:'alphanum',
                    id:"dbPassWord",
                    allowBlank: true,
                    fieldLabel: '数据库密码'
                }
            ]
        }
    ],
    initComponent: function() {
        this.callParent(arguments);
    }
});
