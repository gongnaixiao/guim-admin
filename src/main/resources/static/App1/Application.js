/**
 创建应用程序的实例
 **/
Ext.application({
    name: 'App',
    appFolder: 'App1',//主文件夹文件夹
    requires: ['App.main.Viewport', 'App.utils.proxy.POSTAjax', 'App.utils.proxy.JsonAjax'],
    mainView: 'App.main.Viewport'
});
