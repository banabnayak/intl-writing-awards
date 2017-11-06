var app = angular.module('SWAApp.Security.services', []);
app.factory('authAPIService', ['$http','$q','$window','$rootScope',function ($http, $q, $window, $rootScope) {
    var url = $window.location.pathname+'rest';
	var userInfo = null;
    authAPI = {};
    authAPI.login = function(user) {
        var deferred = $q.defer();
    	var remParams = {transformRequest: angular.identity};
    	return $http.post(url+'/public/auth/login', JSON.stringify(user), remParams).success(function (result, status, headers, config) {
    		userInfo = result;
    		userInfo.accessToken = result.user.userId;
    		userInfo.isLoggedIn = true;
    		userInfo.status =  status;
    		userInfo.tabIndex =  0;
    		userInfo.storyIndex =  0;
    		userInfo.activity = null;
    		$window.sessionStorage.userInfo = JSON.stringify(userInfo);
            $rootScope.loggedInUser = userInfo;
			deferred.resolve(userInfo);
		}).error(function (data, status, headers, config) {
			userInfo = {status:status};
			deferred.resolve(userInfo);
		});
        return deferred.promise;
    };

    authAPI.logout = function() {
        var deferred = $q.defer();
		var remParams = {transformRequest: angular.identity};
        $http.post(url+'/public/auth/logout', null,remParams).success(function (result, status, headers, config) {
        	$window.sessionStorage.userInfo = null;
        	$rootScope.loggedInUser = null;  
        	userInfo = null;
            return deferred.resolve(userInfo);
        }, function (error) {
            return deferred.reject(error);
        });
        return deferred.promise;
    };

    authAPI.getUserInfo = function() { 
    	 if (!userInfo || userInfo == 'null') {
    		 var sessionUser = $window.sessionStorage.userInfo;
    		 if (sessionUser && sessionUser != 'null') {
    			 userInfo = JSON.parse(sessionUser);
    		 }
    	 }
    	 $rootScope.loggedInUser = userInfo;
    	 return userInfo;
    };
    
    authAPI.getURL = function() { 
    	userInfo = authAPI.getUserInfo();
    	if (userInfo && userInfo != 'null') {
   	 		return '/' + userInfo.user.role.toLowerCase();
   	 	}
    	return '/login';
   };
    authAPI.setUserInfo = function(user) {
    	userInfo = user;
    	var sesInfo = JSON.parse($window.sessionStorage.userInfo);
		userInfo.accessToken= sesInfo.accessToken;
		userInfo.isLoggedIn = sesInfo.isLoggedIn;
		userInfo.status =  sesInfo.status;
		userInfo.rememberMe =  sesInfo.rememberMe;
		if(sesInfo.activity != null)
			userInfo.activity = sesInfo.activity;
        $window.sessionStorage.userInfo = JSON.stringify(userInfo);
        $rootScope.loggedInUser = userInfo;
    };
    return authAPI;
}]);

app.run(['$rootScope', '$location', '$templateCache', function ($rootScope, $location, $window) {
    $rootScope.$on('$routeChangeSuccess', function (userInfo) {
        //console.log(userInfo);
    });
    
    $rootScope.$on('$routeChangeError', function (event, current, previous) {
    	//alert('$routeChangeError');
    });
    
    $rootScope.$on('$routeChangeStart', function (event, current, previous) {
   		//alert('$routeChangeStart');
    });
    
    $rootScope.$on('$stateChangeStart', function (event, current, previous) {
    	//alert('$stateChangeStart');
    });
}]);