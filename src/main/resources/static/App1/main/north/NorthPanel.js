/**
 * Created by Stark on 2017/5/1.
 *
 */
Ext.define('App.main.north.NorthPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.NorthPanel',
    layout: 'border',
    items: [
        {
            xtype: 'panel',
            region: 'center',
            html: '<h1 align="center"><font color="blue">ExtJS</font></h1>'
        }
        ]
})