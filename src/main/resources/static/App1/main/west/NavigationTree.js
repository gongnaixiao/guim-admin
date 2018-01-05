/**
 * Created by Stark on 2017/4/27.
 * 导航树,制作简单的树用于指导展示用例
 */
Ext.define('App.main.west.NavigationTree', {
    extend: 'Ext.tree.TreePanel',
    alias: 'widget.NavigationTree',
    requires: ['App.main.west.NavigationViewModel', 'App.main.west.NavigationController'],
    viewModel: 'NavigationViewModel',
    controller: 'NavigationController',

    rootVisible : false,
    root: {
        text: "导航树",
        expanded: true,
        children: [
            {
                text: "柜面环境",
                children: [
                    {
                        text: "GrandChild",
                        clazz: 'App.grid.view.simplegrid.SimpleGrid',
                        leaf: true
                    }
                ]
            },
            {
                text: "Grid表格",
                clazz: 'App.grid.view.simplegrid.SimpleGrid',
                leaf: true
            },
            {
                text: "Child 2",
                children: [
                    {
                        text: "GrandChild",
                        leaf: true
                    }
                    ]
            },
            {
                text: "dd",
                leaf: true
            }
            ]
    },
    listeners: {
        itemclick: 'itemclick'
    }
});
