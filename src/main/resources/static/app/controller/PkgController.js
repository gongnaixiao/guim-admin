/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.controller.PkgController', {
    extend: 'AM.ux.GridController',

    views: [
        'pkg.List',
    ],
    stores: ['PkgStore'],
    models: ['Pkg'],

    init: function() {
        var me = this;
        this.control({
            //渲染事件
            'EnvList': {
                render: 'renderAction',
                itemdblclick: 'viewRowAction'
            },

            //新增事件
            'PkgList button[action=add]': {
                click: function (b) {
                    var win = Ext.create("AM.ux.Window", {
                        title: '新增柜面环境',
                        items: [{xtype: 'EnvForm'}],
                        buttons: [
                            {
                                text: '保存',
                                handler: function (btn) {
                                    Ext.create("AM.ux.Action").submit(win.down("form"), _Api + "/env/add", b.up('grid'), win);
                                }
                            },
                            {
                                text: '重置',
                                handler: function (btn) {
                                    btn.up("window").down("form").form.reset();
                                }
                            },
                            {
                                text: '取消', handler: function (btn) {
                                btn.up("window").close();
                            }
                            }
                        ]
                    });
                }
            },
            //搜索
            'PkgList textfield[action=search]':{
                specialkey: function(field,e){
                    if (e.getKey()==Ext.EventObject.ENTER){
                        var bug = field.up('toolbar').down('#bug').getValue();
                        var pkg = field.up('toolbar').down('#pkg').getValue();
                        var obj = {
                            bug : bug,
                            pkg: pkg
                        };
                        Ext.create("AM.ux.Action").search(field.up("grid"), obj);
                    }
                }
            },
        });
    }
});

