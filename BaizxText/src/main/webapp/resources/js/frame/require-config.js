
require.config({
	/**
	 * 基础路径  baseUrl 就是指当前的路径
	 */
	baseUrl : server_consts.root + '/resources',
	/**
	 * JS库相对于主路径的相对路径
	 */
    paths : {
    	/**
    	 * JS路径
    	 */
        'jquery'       : './easyui/jquery.min',
        'easyui-core'  : './easyui/jquery.easyui.min',
        'easyui-base'  : './easyui/locale/easyui-lang-'+server_consts.locale,
        'locale'       : './js/i18n/system/system-'+server_consts.locale,
        'webAppLocale' : './js/i18n/webApp/webApp-'+server_consts.locale,
        'JSEncrypt'    : './js/thirdparty/javascriptEncrypt/jsencrypt',
    },
    shim: {
    	'easyui-core' : {
    		deps: ['jquery']
    	},
    	'easyui-base' : {
    		deps: ['easyui-core']
    	},
    	'JSEncrypt' : {
    		deps: ['jquery'],
    		exports: 'JSEncrypt'
    	},
		'locale':{
			exports: 'locale'
		}
    	
    }
});