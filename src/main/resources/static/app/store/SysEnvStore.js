/**
 * Created by xg on 2018/1/5.
 */

Ext.define('store.Users', {
    extend: 'AM.ux.GridStore',
    model: 'AM.model.User',
    proxy: {
        type: 'ajax',
        url: _Api + "/user/list",
        reader: {
            type: 'json',
            root: 'data',
            totalProperty:'total'
        }
    }
});