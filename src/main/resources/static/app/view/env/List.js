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
        {header: '应用IP', dataIndex: 'appIP'},
        {header: '应用用户名', dataIndex: 'appUserName'},
        {header: '应用密码', dataIndex: 'appPassWord'},
        {header: '数据库IP', dataIndex: 'dbIP'},
        {header: '数据库用户名', dataIndex: 'dbUserName'},
        {header: '数据库密码', dataIndex: 'dbPassWord'},
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

