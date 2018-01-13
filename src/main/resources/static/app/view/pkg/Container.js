Ext.define('AM.view.deploy.Container' ,{
    extend: 'Ext.panel.Panel',
    alias: 'widget.DeployContainer',

    items: [
        {
            xtype: 'panel',
            layout : 'anchor',
            items: [
                {
                    xtype: 'panel',
                    title: 'Inner Panel One',

                    anchor : '100% 40%',
                },
                {
                    xtype: 'panel',
                    title: 'Inner Panel Two',
                    layout : 'fit',
                    anchor : '100% 60%',
                },
            ]
        }
    ],

    initComponent: function() {
        this.callParent(arguments);
    }
})