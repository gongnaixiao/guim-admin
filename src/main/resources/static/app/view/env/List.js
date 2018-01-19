/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.view.env.List' ,{
    extend: 'AM.ux.PageGrid',
    alias: 'widget.EnvList',
    store: 'EnvStore',
    columns:[
        {header: 'No', xtype: 'rownumberer',width:50},
        {header: '环境名称',  dataIndex: 'name'},
        {header: '应用服务器',  dataIndex: 'app'},
        {header: '应用IP', dataIndex: 'appIP'},
        {header: '应用用户名', dataIndex: 'appUsername'},
        {header: '应用密码', dataIndex: 'appPassword'},
        {header: '数据库url', dataIndex: 'dbUrl'},
        {header: '数据库用户名', dataIndex: 'dbUsername'},
        {header: '数据库密码', dataIndex: 'dbPassword'},
        {header: '工作空间', dataIndex: 'workspace'},
        {header: 'AP包路径', dataIndex: 'apWarDir'},
        {header: 'UP包路径', dataIndex: 'upWarDir'},
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
    tbar:[
        {
            xtype:'button',
            text:'新增',
            action: 'add',
            iconCls:'add'
        },
        {
            xtype:'button',
            text:'删除',
            action:'remove',
            iconCls:'delete'
        },
        {
            xtype:'textfield',
            action:'search',
            emptyText:'搜索'
        }
    ],

    initComponent: function() {
        this.callParent(arguments);
    }
});

