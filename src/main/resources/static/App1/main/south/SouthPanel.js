/**
 * Created by Stark on 2017/5/1.
 *
 */
Ext.define('App.main.south.SouthPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SouthPanel',
    layout: 'border',
    items: [
        {
            xtype: 'panel',
            region: 'center',
            html: '<div></div>',
        }
        ]
})