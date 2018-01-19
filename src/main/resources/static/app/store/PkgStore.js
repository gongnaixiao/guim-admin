/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.store.PkgStore', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Pkg',
    pageSize: 50,
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: _Api + "/pkg/list",
        reader: {
            type: 'json',
            root: 'data',
            totalProperty:'total'
        }
    }
});