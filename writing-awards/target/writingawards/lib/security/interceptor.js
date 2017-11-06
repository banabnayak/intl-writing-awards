angular.module('SWAApp.Security.interceptors', [])
.factory('authInterceptor', function ($rootScope, $q, $window, $location) {
	return {
		request: function (config) {
			try{
				config.headers = config.headers || {};
				$rootScope.errorMsg = null;
				config.headers.Authorization = null;
				$rootScope.regExpired = $window.sessionStorage.regExpired;
				if(config.url.indexOf('.html') == -1){ 
					config.headers.Authorization = APP.TOKEN.PRIVATE;
					if(config.method == 'GET') config.url += '?rand='+ new Date();
					if(config.url.indexOf(APP.RESOURCES.PUBLIC) > -1){
						config.headers.Authorization = APP.TOKEN.PUBLIC;
					}else{
						config.headers.Authorization = APP.TOKEN.PRIVATE;
						var sessionUser = $window.sessionStorage.userInfo;
						if(sessionUser && sessionUser != 'null'){
							var userInfo = JSON.parse(sessionUser);
							var token = userInfo.accessToken;
							var role = userInfo.user.role;
							if((config.url.indexOf(APP.RESOURCES.ADMIN) >-1 && role != APP.ROLES.ADMIN) || (config.url.indexOf(APP.RESOURCES.STUDENT) >-1 && role != APP.ROLES.STUDENT)){
								return $q.reject({data : 'Unauthorized access', status : 401});
							}else{
								config.headers.Authorization = 'SWA ' + token + ':' + role;
							}
						}else{
							return $q.reject({data: 'Invalid Session', status : 402});
						}
					}
				}
			}catch(e){
				$window.sessionStorage.userInfo = null;
				userInfo = null;
				$rootScope.loggedInUser = null;  
				return $q.reject({data: 'Unexpected error', status : 406});
			}
			return config || $q.when(config);
			//return config;
		},

		requestError: function (rejection) {
			return $q.reject(rejection);
		},
		response: function (response) {
			return response || $q.when(response);
		},
		responseError: function (response) {
			try{
				//alert('interceptor@@'+response.data);
				//alert('interceptor@@'+response.status);
				$rootScope.errorMsg = ERROR[response.status];
			}catch(e){
				//alert(e.message);
			}
			//if(response.status != 500){
				$window.sessionStorage.userInfo = null;
				userInfo = null;
				$rootScope.loggedInUser = null;  
				$location.path('/login');
				return $q.reject(response);
			//}			
			return response || $q.when(response);
		}
	};
}).factory('refreshInterceptor', function ($rootScope, $q, $window) {
	//alert('refreshInterceptor');		
	function handleRefresh() {
		$rootScope.regExpired = $window.sessionStorage.regExpired;
	    var sessionUser = $window.sessionStorage.userInfo;
	    try{
	        if (sessionUser && sessionUser != 'null') {
	        	$rootScope.loggedInUser = JSON.parse(sessionUser);
	        }
    	}catch(e){
    	}
	}
	handleRefresh();
	return [];
}).config(function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
	$httpProvider.interceptors.push('refreshInterceptor');
});


