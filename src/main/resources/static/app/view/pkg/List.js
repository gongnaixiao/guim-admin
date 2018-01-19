/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.view.pkg.List', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.PkgList',

    selType: 'checkboxmodel',
    emptyText: '暂无数据',
    rowLines: true,
    forceFit: true,
    layout: 'fit',
    columnLines: true,

    store: 'PkgStore',

    columns: [
        {header: 'No', xtype: 'rownumberer', width: 50},
        {header: '包名称', dataIndex: 'name'},
        {header: '缺陷编号', dataIndex: 'bug'},
        {header: '投产环境', dataIndex: 'envName'},
        {header: '是否投产', dataIndex: 'prd'},
        {header: '提交者', dataIndex: 'author'},
        {header: '版本', dataIndex: 'version'},
        {header: '打包时间', dataIndex: 'ctime'},
        {
            header: '操作',
            menuDisabled: true,
            sortable: false,
            xtype: 'actioncolumn',
            action: 'rowOpt',
            width: 70,
            items: [
                {
                    iconCls: 'deploy',
                    tooltip: '部署',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        this.fireEventArgs('deployRow', [grid, rec]);
                    }
                },
                {
                    iconCls: 'delete',
                    tooltip: '删除',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        this.fireEventArgs('deleteRow', [grid, rec]);
                    }
                }]
        }
    ],

    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'top',
            items: [
                {
                    xtype: 'button',
                    text: '新增',
                    action: 'add',
                    iconCls: 'add'
                },
                {
                    xtype: 'button',
                    text: '删除',
                    action: 'remove',
                    iconCls: 'delete'
                },
                {
                    xtype: 'button',
                    text: '部署',
                    action: 'deploy',
                    iconCls: 'deploy'
                }
            ]
        },
        {
            xtype: 'toolbar',
            dock: 'top',
            items: [
                {
                    id: 'pkg',
                    xtype: 'textfield',
                    action: 'search',
                    emptyText: '包名称'
                },
                {
                    id: 'bug',
                    xtype: 'textfield',
                    action: 'search',
                    emptyText: '缺陷编号'
                },
                {
                    id: 'env',
                    xtype: 'combobox',
                    displayField: 'name',
                    valueField: 'id',
                    editable: false,
                    forceSelection: true,
                    tpl: '<tpl for="."><div class="x-boundlist-item">{name}&nbsp;</div></tpl>',
                    store: {
                        fields: ['name'],
                        autoLoad: true,
                        proxy: {
                            type: 'ajax',
                            url: _Api + "/env/envNames",
                            reader: {
                                type: 'json',
                                root: 'data',
                            }
                        },
                        listeners: {
                            load: function (store, records, options) {
                                store.insert(0, {name: ''});
                            }
                        }
                    },
                    emptyText: '投产环境',
                    listeners: {
                        select: function (field, record, opts) {
                            var bug = field.up('toolbar').down('#bug').getValue();
                            var pkg = field.up('toolbar').down('#pkg').getValue();
                            var envId = field.up('toolbar').down('#env').getValue();
                            var obj = {
                                'bug': bug,
                                'pkg': pkg,
                                'envId': envId
                            };
                            Ext.create("AM.ux.Action").search(field.up("grid"), obj);
                        }
                    },
                },
                {
                    id: 'prd',
                    xtype: 'textfield',
                    action: 'search',
                    emptyText: '是否投产'
                },
            ]
        },
        {
            xtype: 'pagingtoolbar',
            dock: 'bottom',
            displayInfo: true,
            pageSize: 50,
            //displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
            emptyMsg: "没有数据",
            store: 'PkgStore',
            plugins: Ext.create('AM.ux.ProgressBarPager')
        }
    ],

    initComponent: function () {
        this.callParent(arguments);
    }
});

