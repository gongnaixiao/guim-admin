/**
 * Created by xg on 2018/1/5.
 */

Ext.define('AM.store.EnvStore', {
    extend: 'AM.ux.GridStore',
    model: 'AM.model.Env',
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