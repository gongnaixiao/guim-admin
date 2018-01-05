Ext.define('App.main.Viewport', {
    extend: 'Ext.container.Viewport',
    layout: 'border',
    requires: ['App.main.HomePage', 'App.main.west.NavigationTree', 'App.main.north.NorthPanel', 'App.main.south.SouthPanel'],
    items:
        [
            {
                xtype:'NorthPanel',
                region: 'north',
                border: true,
                height:100,
                margin: '0 0 5 0'
            },
            {
                xtype: 'SouthPanel',
                region: 'south',
                border: true,
                height: 20
            },
            {
                xtype: 'NavigationTree',
                region: 'west',
                collapsible: true,
                title: '导航树',
                width: 200
            },
            {
                xtype: 'tabpanel',
                region: 'center',
                title: '示例演示区',
                plugins: [{ptype: 'tabclosemenu'}],
                activeTab: 0,
                items: [{
                    title: '首页',
                    xtype: 'HomePage'
                }]
            }
        ]
});