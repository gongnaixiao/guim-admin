/**
 * Created by jameszhou on 2017/8/9.
 */
Ext.define('AM.controller.EnvController', {
    extend: 'AM.ux.GridController',

    views: [
        'env.List',
        'env.Form'
    ],
    stores: [
        'EnvStore'
    ],
    models: ['Env'],

    init: function() {
        var me = this;
        this.control({
            //渲染事件
            'EnvList': {
                render:'renderAction',
                itemdblclick: 'viewRowAction'
            },

            //新增事件
            'EnvList button[action=add]':{
                click:function (b) {
                    var win = Ext.create("AM.ux.Window",{
                        title:'新增柜面环境',
                        items:[{xtype:'EnvForm'}],
                        buttons:[
                            {
                                text:'保存',
                                handler:function (btn) {
                                    Ext.create("AM.ux.Action").submit(win.down("form"),_Api+"/env/add",b.up('grid'),win);
                                }
                            },
                            {
                                text:'重置',
                                handler:function (btn) {
                                    btn.up("window").down("form").form.reset();
                                }
                            },
                            {
                                text:'取消',handler:function (btn) {
                                    btn.up("window").close();
                                }
                            }
                        ]
                    });
                }
            },
            //批量删除
            'EnvList button[action=remove]':{
                click:function (btn) {
                    me.removeBatchAction(_Api+"/env/delete",btn.up("grid"));
                }
            },
            //搜索
            'EnvList textfield[action=search]':{
                specialkey: function(field,e){
                    if (e.getKey()==Ext.EventObject.ENTER){
                        Ext.create("AM.ux.Action").search(field.up("grid"),{search:field.getValue()});
                    }
                }
            },

            //行编辑事件
            'EnvList actioncolumn':{
                //行删除
                deleteRow:function (grid, record) {
                    me.removeRowAction(_Api+"/env/delete",{ids:record.get("id")},grid);
                },
                //行编辑
                editRow:function (grid, record) {
                    var win = Ext.create("AM.ux.Window",{
                        title:'编辑用户',
                        items:[{xtype:'EnvForm'}],
                        buttons:[
                            {
                                text:'保存',
                                handler:function (btn) {
                                    Ext.create("AM.ux.Action").submit(win.down("form"),_Api+"/env/edit",grid,win);
                                }
                            },
                            {
                                text:'重置',handler:function (btn) {
                                win.down("form").loadRecord(record);
                            }
                            },{
                                text:'取消',handler:function (btn) {
                                    win.close();
                                }
                            }
                        ],
                        listeners:{
                            show:function () {
                                var form = this.down("form").down('ux-form');
                                form.loadRecord(record);
                            }
                        }
                    })
                }
            }
        });
    }
});
/**
 * Created by xg on 2018/1/8.
 */
