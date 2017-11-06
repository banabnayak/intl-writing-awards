angular.module('SWAApp', [
  'SWAApp.controllers',
  'SWAApp.services',
  'SWAApp.Security.interceptors',
  'SWAApp.Security.services',
  'DialogApp.services',
  'ngRoute','ui.bootstrap', 
  'ngTouch','ngSanitize'
]).config(['$routeProvider', function($routeProvider) {
  $routeProvider.
	when('/login', {templateUrl: 'templates/login.html', controller: 'LoginController',
		resolve: {
            chkEnrollment: function ($window, swaAPIService) {
            	swaAPIService.initAppl();
            }
        }
	}).
	when('/enroll', {templateUrl: 'templates/enroll.html', controller: 'SWAController',
		resolve: {
            chkEnrollment: function ($window, $location, swaAPIService, authAPIService) {
            	swaAPIService.initAppl();
            	if($window.sessionStorage.regExpired == 'true'){
	            	authAPIService.logout();
	            	$location.path('/login');
            	}
            }
        }
	}).
	when('/student', {templateUrl: 'templates/student.html',controller: 'StudentController'}).
	when('/story', {templateUrl: 'templates/story.html', controller: 'StoryController',
		resolve: {
            chkEnrollment: function ($window, $location, swaAPIService, authAPIService) {
            	swaAPIService.initAppl();
            	if($window.sessionStorage.regExpired == 'true'){
	            	//authAPIService.logout();
	            	$location.path('/student');
            	}
            }
        }
	}).
	when('/view', {templateUrl: 'templates/view.html', controller: 'StoryController'}).
	when('/review', {templateUrl: 'templates/review.html', controller: 'ViewController',
		resolve: {
            chkReview: function ($window, $location, swaAPIService, authAPIService) {
            	swaAPIService.initAppl();
				var userInfo = authAPIService.getUserInfo();
				var revStarted = userInfo.isReviewStarted;
				var revClosed = userInfo.isReviewClosed;
            	if(!revStarted || revClosed ){
	            	//authAPIService.logout();
	            	$location.path('/student');
            	}
            }
        }}).
	when('/admin', {templateUrl: 'templates/admin.html', controller: 'AdminController'}).
	when('/contact', {templateUrl: 'templates/contact.html', controller: null}).
	when('/logout', {templateUrl: 'templates/login.html', controller: 'LoginController',
		resolve: {
            auth: function ($window, $location, authAPIService) {
            	authAPIService.logout();
            	$location.path('/login');
            }
        }
	}).otherwise({redirectTo: '/login'});
}]).directive('fileread', [function () {
    return {
        scope: {
            fileread: '='
        },
        link: function (scope, element, attributes) {
            element.bind('change', function (changeEvent) {
                scope.$apply(function () {
                    scope.fileread = changeEvent.target.files[0];
                    /* view */
                    var output = $("#viewPane");
                    var file = scope.fileread;                            
                    var div = document.createElement("div");
                    var size = parseInt(file.size/1024).toFixed(1);
                    //Only png pics
                    if(file && file.type.match('image/png')){
	                    var picReader = new FileReader();
	                    picReader.addEventListener("load",function(event){
	                        var picFile = event.target;
	                        div.innerHTML = "<img class='thumbnail' src='" + picFile.result + "'" +
	                            "title='File Name : " + file.name + "\nFile Size : "+size+"KB'  width='350'/>";
	                        $(output).html(div);
	                    });
	                     //Read the image
	                    picReader.readAsDataURL(file);
                    }else{
                    	$(output).html('<div style="color:blue;">File Name : '+ file.name +
                    			'<br>File fromat : '+file.type +
                    			'<br>File Size : '+size+'KB' +
                    			'<br>This format is not supported for baner.' +
                    			'<br>Please try only png format image.</div>');
                    }
                    /*view*/
                });
            });
        }
    };
}]).directive('onReadFile', function ($parse) {
	return {
		restrict: 'A',
		scope: false,
		link: function(scope, element, attrs) {
            var fn = $parse(attrs.onReadFile);            
			element.on('change', function(onChangeEvent) {
				var file = onChangeEvent.target.files[0];
				var msg = '';
				if(file && file.type.match('text/plain')){
					var reader = new FileReader();
					reader.onload = function(onLoadEvent) {
						scope.$apply(function() {
							msg = 'Story uploaded successfully ...';
							fn(scope, {$fileContent:onLoadEvent.target.result, $message : msg});
						});
					};
			        reader.onerror = function(event) {
			        	msg = 'File could not be read! Code ' + event.target.error.code;
			        };
					reader.readAsText((onChangeEvent.srcElement || onChangeEvent.target).files[0]);
                }else{
                	msg = 'Only text file can be uploaded ...';
                }
				scope.$apply(function() {
					fn(scope, {$message : msg});
				});
			});
		}
	};
}).filter('makeRange', function() {
    return function(input) {
        var lowBound, highBound;
        switch (input.length) {
        case 1:
            lowBound = 0;
            highBound = parseInt(input[0]) - 1;
            break;
        case 2:
            lowBound = parseInt(input[0]);
            highBound = parseInt(input[1]);
            break;
        default:
            return input;
        };
        var result = [];
        for (var i = lowBound; i <= highBound; i++)
            result.push(i);
        return result;
    };
});