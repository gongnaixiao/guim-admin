/**
 * Created by HP on 2017/4/9.
 */
Ext.define('App.grid.view.extend.ExtendGrid', {
    extend: 'Ext.grid.Panel',
    height: '100%',
    width: '100%',
    forceFit : true,
    reserveScrollbar : true, //预留滚动条
    config: {
        extraParams: {}, //用于页面额外请求参数
        pageConfig : { //page的控制，方便子类修改
            dock: 'bottom',
            displayInfo: true
        }
    },
    pagable: true, //标记是否添加分页

    initComponent: function () {
        this.callParent(arguments);
        this.initPageTool();
    },
    setStore: function (store) {
        var grid = this;
        //重新生成store 继承Ext.data.Store
        store = Ext.create('Ext.data.Store', store)
        store.on(
            'beforeload', function (store, operation, eOpts) {
                if (!Ext.isEmpty(grid.extraParams)) {
                    Ext.apply(store.proxy.extraParams, grid.extraParams)
                }
            }
        );
        if (grid.isVisible()) {
            if (!store.isLoaded() || !store.isLoading()) {
                store.load();
            }
        }
        this.callParent(arguments);
    },
    initPageTool: function () {
        var grid = this;
        if (!grid.pagable) {
            return;
        }
        var pageConfig = grid.pageConfig;
        var pageTool  = Ext.create('Ext.toolbar.Paging',pageConfig);
        pageTool.setStore(grid.getStore());
        grid.addDocked(pageTool)
    }
});