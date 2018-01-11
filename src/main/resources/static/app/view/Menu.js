/**
 * Created by xg on 2018/1/8.
 */
Ext.define('AM.view.Menu',{
    extend: 'Ext.panel.Panel',
    alias: 'widget.menu',
    title: '菜单',
    width: 200,
    glyph:0xf03a,
    layout: {
        // layout-specific configs go here
        type: 'accordion',
        titleCollapse: true,
        animate: true,
        activeOnTop: true
    },
    initComponent: function() {
        this.callParent(arguments);
    }
});