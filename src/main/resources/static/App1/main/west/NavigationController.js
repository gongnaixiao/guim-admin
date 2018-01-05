/**
 * Created by HP on 2017/4/28.
 * 导航树控制层
 */
Ext.define('App.main.west.NavigationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.NavigationController',
    itemclick: function ($this, record, item, index, e, eOpts) {
        var clazz = record.data.clazz;
        var leaf = record.data.leaf;
        var name = record.data.text;
        if (!leaf || Ext.isEmpty(clazz)) return;
        var tabPanel = this.getView().up().down('tabpanel[title="示例演示区"]');
        var target = tabPanel.down('panel[title="' + name + '"]');
        if (!target) {
            target = Ext.create(clazz, {title: name, closable: true})
            tabPanel.add(target)
        }
        tabPanel.setActiveItem(target);
    }
});