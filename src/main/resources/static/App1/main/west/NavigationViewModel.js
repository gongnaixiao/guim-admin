/**
 * Created by Stark on 2017/4/28.
 * 导航树的viewModel
 */
Ext.define('App.main.west.NavigationViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.NavigationViewModel',
    data: {
        root: {
            text: "导航树",
            expanded: true,
            children: [
                {
                text: "Grid表格",
                clazz: 'App.grid.view.simplegrid.SimpleGrid',
                leaf: true
                },
                {
                text: "Child 2",
                expanded: true,
                children: [{
                    text: "GrandChild",
                    leaf: true
                }]
                }
                ]
        }
    }

})
