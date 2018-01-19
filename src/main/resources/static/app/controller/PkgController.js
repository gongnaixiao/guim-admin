/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.controller.PkgController', {
    extend: 'AM.ux.GridController',

    views: [
        'pkg.List',
        'pkg.Form',
        'pkg.DeployForm',
    ],
    stores: ['PkgStore'],
    models: ['Pkg'],

    init: function() {
        var me = this;
        this.control({
            //渲染事件
            'PkgList': {
                render: 'renderAction',
                itemdblclick: 'viewRowAction'
            },

            //新增事件
            'PkgList button[action=add]': {
                click: function (b) {
                    var win = Ext.create("AM.ux.Window", {
                        title: '上传打包文件',
                        items: [{xtype: 'PkgForm'}],
                        buttons: [
                            {
                                text: '保存',
                                handler: function (btn) {
                                    Ext.create("AM.ux.Action").submit(win.down("form"), _Api + "/pkg/add", b.up('grid'), win);
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
            //新增事件
            'PkgList button[action=deploy]': {
                click: function (b) {
                    var data = b.up('grid').getSelectionModel().getSelection();

                    if(data.length==0){
                        Ext.error("请选择要部署的行！");
                        return;
                    }
                    var params = new Array();
                    Ext.each(data,function(obj,index,countriesItSelf){
                        params.push({
                            'pkgId':obj.get('pkgId'),
                            'envId':obj.get('envId')
                        });

                    })

                    Ext.create("AM.ux.Action").doJSONAction({
                        url:'/pkg/deploy',
                        msg:'请确认进行部署操作',
                        params:  params,
                        grid:b.up('grid'),
                    });
                }
            },
            //批量删除
            'PkgList button[action=remove]':{
                click:function (btn) {
                    var _url = _Api+"/pkg/delete";
                    var _grid = btn.up("grid");
                    var data = _grid.getSelectionModel().getSelection();
                    if(data.length==0){
                        Ext.error("请选择要删除的行！");
                        return;
                    }
                    var arr = [];
                    Ext.each(data,function(obj,index,countriesItSelf){
                        Ext.Array.include(arr,obj.get('pkgId'));
                    });
                    Ext.create("AM.ux.Action").remove({ids:arr},_url,_grid);
                }
            },
            //搜索
            'PkgList textfield[action=search]':{
                specialkey: function(field,e){
                    if (e.getKey()==Ext.EventObject.ENTER){
                        var bug = field.up('toolbar').down('#bug').getValue();
                        var pkg = field.up('toolbar').down('#pkg').getValue();
                        var env = field.up('toolbar').down('#env').getValue();
                        var obj = {
                            'bug' : bug,
                            'pkg': pkg,
                            'env': env
                        };
                        Ext.create("AM.ux.Action").search(field.up("grid"), obj);
                    }
                }
            },
            //行编辑事件
            'PkgList actioncolumn': {
                //行删除
                deleteRow:function (grid, record) {
                    Ext.create("AM.ux.Action").remove({ids:record.get('pkgId')}, _Api+"/pkg/delete",grid );
                },
                //行部署
                deployRow:function (grid, record) {
                    var params = new Array();
                    params.push({
                        'pkgId':record.get('pkgId'),
                        'envId':record.get('envId')
                    });
                    Ext.create("AM.ux.Action").doJSONAction({
                        url:'/pkg/deploy',
                        msg:'请确认进行部署操作',
                        params:params,
                        grid:grid,
                    });
                },
            }
        });
    }
});

