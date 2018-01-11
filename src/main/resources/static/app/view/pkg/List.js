/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.view.pkg.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.PkgList',

    selType: 'checkboxmodel',
    emptyText:'暂无数据',
    rowLines:true,
    forceFit : true,
    layout:'fit',
    columnLines : true,

    store: 'PkgStore',

    columns:[
        {header: 'No', xtype: 'rownumberer',width:50},
        {header: '包名称', dataIndex: 'name'},
        {header: '缺陷编号',  dataIndex: 'bugId'},
        {header: '投产环境', dataIndex: 'env'},
        {header: '是否投产', dataIndex: 'isPrd'},

        {
            header:'操作',
            menuDisabled: true,
            sortable: false,
            xtype: 'actioncolumn',
            action: 'rowOpt',
            width: 70,
            items: [{
                iconCls: 'delete',
                tooltip: '删除',
                handler:function (grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    this.fireEventArgs('deleteRow',[grid, rec]);
                }
            },{
                iconCls: 'edit',
                tooltip: '编辑',
                handler:function (grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    this.fireEventArgs('editRow',[grid, rec]);
                }
            }]
        }
    ],

    dockedItems: [
        {
            xtype : 'toolbar',
            dock : 'top',
            items: [
                {
                    xtype:'button',
                    text:'新增',
                    action: 'add',
                    iconCls:'add'
                },
                {
                    id: 'bug',
                    xtype: 'textfield',
                    action: 'search',
                    emptyText:'缺陷编号'
                },
                {
                    id: 'pkg',
                    xtype: 'textfield',
                    action: 'search',
                    emptyText:'包名称'
                },
                {
                    id: 'env',
                    xtype: 'combobox',
                    action: 'search',
                    emptyText: '投产环境'
                }

            ]
        },
        {
            xtype: 'pagingtoolbar',
            dock: 'bottom',
            displayInfo: true,
            pageSize: 50,
            //displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
            emptyMsg:"没有数据",
            store: this.store,
            plugins: Ext.create('AM.ux.ProgressBarPager')
        }
    ],

    initComponent: function() {
        this.callParent(arguments);
    }
});

