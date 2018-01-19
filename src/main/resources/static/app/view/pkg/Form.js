/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.view.pkg.Form', {
    extend: 'Ext.form.Panel',
    alias: 'widget.PkgForm',
    width:800,
    height:300,
    layout: 'fit',
    requires: ['AM.ux.form.FormPanel'],
    items:[
        {
            xtype:'ux-form',
            items: [
                {xtype:'hiddenfield',name:'id'},
                {
                    xtype: 'filefield',
                    name : 'file',
                    id:'file',
                    allowBlank: false,
                    fieldLabel: '投产包'
                },
                {
                    xtype: 'textfield',
                    name : 'bugId',
                    id:"bugId",
                    allowBlank: true,
                    fieldLabel: '缺陷编号'
                },
                {
                    xtype: 'textfield',
                    name : 'author',
                    id:"author",
                    allowBlank: true,
                    fieldLabel: '提交者'
                },
            ]
        }
    ],
    initComponent: function() {
        this.callParent(arguments);
    }
});
