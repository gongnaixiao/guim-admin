Ext.onReady(function() {
    // 初始化标签中的Ext:Qtip属性。
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    // 提交按钮处理方法
    var submitForm = function() {
        if(loginForm.getForm().isValid()){
            loginForm.getEl().mask('登录验证中', 'x-mask-loading');
            loginForm.getForm().submit({
                waitTitle:'正在登录',
                waitMsg:'正在向服务器申请登录验证，请稍后...',
                success:function(basicform, action){
                    loginForm.getEl().unmask();
                    document.location='index';
                },
                failure: function(basicform, action) {
                    Ext.Msg.alert('验证错误', action.result.msg);
                    loginForm.getEl().unmask();
                }
            });
        }else{
            Ext.Msg.alert('提示','登录信息错误,请核对！');
        }
    }
    // 重置按钮"点击时"处理方法
    var resetForm = function() {
        loginForm.getForm().reset();
    }

    var subEnterLister = {
        specialkey : function(field, e) {
            if (e.getKey() == e.ENTER) {
                submitForm();
            }
        }
    };//定义键盘Enter事件

    // 用户名input
    var userName = new Ext.form.TextField({
        id : 'userName',
        fieldLabel : '用户名',

        labelAlign : 'right',
        allowBlank : false,
        maxLength : 20,

        blankText : '请输入用户名',
        maxLengthText : '用户名不能超过20个字符',
        listeners : subEnterLister
    });
    // 密码input
    var passWord = new Ext.form.TextField({
        id:'passWord',
        fieldLabel : '密　码',

        labelAlign : 'right',
        allowBlank : false,
        maxLength : 20,
        inputType : 'password',

        blankText : '请输入密码',
        maxLengthText : '密码不能超过20个字符',
        listeners : subEnterLister
    });

    var accountFS = new Ext.form.FieldSet({
        title : '请登录',
        collapsible : false,
        items : [userName,passWord]
    });

    // 表单
    var loginForm = new Ext.form.FormPanel({
        id : 'loginForm',
        url : '/login',
        method: 'POST',

        frame : true,
        labelAlign : 'center',
        buttonAlign : 'center',
        bodyStyle : 'padding:30 0 0 20;',
        border : false,

        items : [
            accountFS
        ],
        buttons : [
            {
                text : '提 交',
                handler : submitForm,
                scope : this
            },
            {
                text : '重 置',
                handler : resetForm,
                scope : this
            }
        ]
    });
    // 窗体
    var win = new Ext.Window({
        id:'loginWindow',
        title : '用户登陆',
        iconCls : 'loginicon',
        plain : true,
        width : 350,
        resizable : false,
        shadow : true,
        modal : true,
        closable : false,
        animCollapse : true,
        items : [loginForm]
    }).show();
});