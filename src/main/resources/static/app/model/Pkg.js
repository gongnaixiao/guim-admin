
Ext.define('AM.model.Pkg', {
    extend: 'Ext.data.Model',
    fields: [
        "pkgId","name","bug","envName", "envId", "prd", "version", "author",
        {
            name:"ctime",
            type:"date",
            convert: function(value){
                var createTime = Ext.Date.format(new Date(value),"Y-m-d H:i:s");
                return createTime;
            }
        }
        ]
});
