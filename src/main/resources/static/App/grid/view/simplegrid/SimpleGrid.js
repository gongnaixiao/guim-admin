/**
 * Created by HP on 2017/4/4.
 */
Ext.define('App.grid.view.simplegrid.SimpleGrid', {
    extend : 'App.grid.view.extend.ExtendGrid',
    alias : "widget.SimpleGrid",
    requires : ['App.grid.viewmodel.TestViewModel'],
    viewModel : 'TestViewModel',
    bind: {
        columns: '{columns}',
        store :'{store}'
    },
    extraParams : {'tanshao':'quick'}
});