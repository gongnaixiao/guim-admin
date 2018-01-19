/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.view.env.Form', {
    extend: 'Ext.form.Panel',
    alias: 'widget.EnvForm',
    width:800,
    height:500,
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
                    id:"name",
                    fieldLabel: '环境名称',
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
                    id:"appIP",
                    fieldLabel: '应用IP'
                },
                {
                    xtype: 'textfield',
                    name : 'appUsername',
                    id:"appUsername",
                    fieldLabel: '应用用户名'
                },
                {
                    xtype: 'textfield',
                    name : 'appPassword',
                    id:"appPassword",
                    fieldLabel: '应用密码'
                },
                {
                    xtype: 'textfield',
                    name : 'dbUrl',
                    id:"dbUrl",
                    fieldLabel: '数据库url'
                },
                {
                    xtype: 'textfield',
                    name : 'dbUsername',
                    id:"dbUsername",
                    fieldLabel: '数据库用户名'
                },
                {
                    xtype: 'textfield',
                    name : 'dbPassword',
                    id:"dbPassword",
                    fieldLabel: '数据库密码'
                },
                {
                    xtype: 'textfield',
                    name : 'workspace',
                    id:"workspace",
                    fieldLabel: '工作空间'
                },
                {
                    xtype: 'textfield',
                    name : 'apWarDir',
                    id:"apWarDir",
                    fieldLabel: 'AP包路径'
                },
                {
                    xtype: 'textfield',
                    name : 'upWarDir',
                    id:"upWarDir",
                    fieldLabel: 'UP包路径'
                }
            ]
        }
    ],
    initComponent: function() {
        this.callParent(arguments);
    }
});
