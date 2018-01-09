/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.store.EnvStore', {
    extend: 'Ext.data.Store',
    model: 'AM.model.Env',
    pageSize: 10,
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: _Api + "/env/list",
        reader: {
            type: 'json',
            root: 'data',
            totalProperty:'total'
        }
    }
});