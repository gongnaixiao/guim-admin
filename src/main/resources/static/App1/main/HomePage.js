/**
 * Created by HP on 2017/4/4.
 */
Ext.define('App.main.HomePage', {
    extend: 'Ext.panel.Panel', //表示继承Panel类，然后可以增加属性，覆盖属性达到显示效果
    alias: 'widget.HomePage',
    closable: false,
    html: '练习ExtJs和SpringBoot<br/>板块接受：左侧是导航树，点击节点加载对应的练习模块，下方展示自己练习的内容'
})