/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.view.pkg.DeployForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.DeployForm',
    width:800,
    height:300,
    layout: 'fit',
    requires: ['AM.ux.form.FormPanel', 'AM.ux.MultiComboBox'],
    items:[
        {
            xtype:'ux-form',
            items: [
                {
                    id: 'deploy',
                    xtype: 'multicombobox',
                    displayField: 'name',
                    valueField: 'id',
                    editable : false,
                    store: {
                        fields: ['id','name'],
                        proxy: {
                            type: 'ajax',
                            url: _Api + "/env/envNames",
                            reader: {
                                type: 'json',
                                root: 'data',
                            }
                        }
                    },
                }
            ]
        }
    ],
    initComponent: function() {
        this.callParent(arguments);
    }
});
