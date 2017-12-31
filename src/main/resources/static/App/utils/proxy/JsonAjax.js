/**
 * Created by HP on 2017/5/1.
 * 自定义代理，使用Ajax传递参数
 */
Ext.define('App.utils.proxy.JsonAjax',{
    extend:'Ext.data.proxy.Ajax',
    alias:'proxy.jsonajax',
    actionMethods : {
        create: "POST",
        read: "POST",
        update: "POST",
        destroy: "POST"
    },
    buildRequest:function (operation) {
        var request = this.callParent(arguments);
        request.jsonData = request.params;
        request.params = {};
        return request;
    },
    /*
     * @override
     * Inherit docs. We don't apply any encoding here because
     * all of the direct requests go out as jsonData
     */
    applyEncoding: function(value){
        return value;
    }
});

