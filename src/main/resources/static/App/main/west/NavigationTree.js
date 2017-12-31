/**
 * Created by Stark on 2017/4/27.
 * 导航树,制作简单的树用于指导展示用例
 */
Ext.define('App.main.west.NavigationTree', {
    extend: 'Ext.tree.Panel',
    requires: ['App.main.west.NavigationViewModel', 'App.main.west.NavigationController'],
    viewModel: 'NavigationViewModel',
    alias: 'widget.NavigationTree',
    controller: 'NavigationController',
    root: {
        expanded: true,
        text: "导航树",
        children: [
            {
                text: "Grid表格",
                clazz: 'App.grid.view.simplegrid.SimpleGrid',
                leaf: true
            },
            {
                text: "Child 2",
                expanded: true,
                children: [
                    {
                        text: "GrandChild",
                        leaf: true
                    }
                    ]
            }
            ]
    },
    listeners: {
        itemclick: 'itemclick'
    }
});
