/**
 * Created by Stark on 2017/4/4.
 *
 */
Ext.define('App.grid.viewmodel.TestViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.TestViewModel',
    requires: ['App.grid.model.TestModel'],
    data: {
        columns: [
            {
            text: "ID",
            dataIndex: 'id',
            hidden: true
            },
            {
            text: "姓名",
            dataIndex: 'name'
            },
            {
            text: '生日',
            dataIndex: 'birthday',
            renderer: function (value) {
                var dt = new Date(value);
                return Ext.Date.format(dt, 'Y-m-d')
            }
            },
            {
            text: '编号',
            dataIndex: 'number'
            },
            {
            text: '空闲',
            dataIndex: 'flag',
            renderer: function (value) {
                if (value) {
                    return '是';
                }
                else {
                    return '否';
                }
            }
            },
            {
            text: "地址",
            dataIndex: 'address'
            }
        ],
        store: {
            extend: 'Ext.data.Store',
            model: 'App.grid.model.TestModel',
            autoLoad: false,
            pageSize: 25,
            proxy: {
                type: 'postajax',
                url: '/first/query',
                reader: {
                    type: 'json',
                    rootProperty: 'data'
                }
            }
        }
    }
});