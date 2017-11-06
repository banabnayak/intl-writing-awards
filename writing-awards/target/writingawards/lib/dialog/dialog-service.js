angular.module('DialogApp.services', []).factory('dialogAPIService', function ($document, $compile, $rootScope, $controller, $timeout) {
	var defaults = {
      id: null,
      title: 'Default Title',
      top : 20,
      height : 200,
      width : 400,
      type : 'dialog',
      text : '',
      backdrop: true,
      withFooter : true,
      success: {label: 'OK', hide : true, fn: null},
      close: {label: 'Close', fn: null},
      controller: null, //just like route controller declaration
      backdropClass: 'modal-backdrop',
      footerTemplate: null,
      modalClass: 'modal'
    };
	var dialogAPI = {};
    var body = $document.find('body');
    
    dialogAPI.createDialog = function(template, options, passedInLocals) {
      options = angular.extend({}, defaults, options); //options defined in constructor
      var idAttr = options.id ? ' id="' + options.id + '" ' : '';
//      var defaultFooter = '<button class="btn" ng-click="$modalCancel()">Close</button>' +
//        '<button class="btn btn-primary" ng-click="$modalSuccess()">{{$modalSuccessLabel}}</button>';
      
      var defaultFooter = '';
      var bodyContent = '';
      if(options.type == 'frame'){
			var loading = '<div class="alert alert-center text-center loadmsg">'+
			'<div>Loading ...<hr><b>'+options.text+'</b></div>'+
			'</div>';
    	  bodyContent +='  <div class="modal-body b'+ options.type +'">'+loading+'<iframe src="' + template + '" class="frmDialog" onload="$(\'.alert\').remove()"></iframe></div>';
      }else if(options.type.indexOf('dialog') >=0 ){
    	  bodyContent +='  <div class="modal-body b'+ options.type +'" ng-include="\'' + template + '\'"></div>';
          defaultFooter = '<div class="text-center">' +
          '	<button type="button" class="btn btn-default buttonDecor" data-dismiss="modal" ng-click="$modalCancel()">Close</button>' +
  		  '	<button type="button" class="btn btn-primary buttonDecor" ng-click="$modalSuccess()">{{$modalSuccessLabel}}</button>'+
  		  //defaultFooter += '<button type="button" class="btn btn-primary buttonDecor" ng-click="'+options.submit.fn+'">{{$modalSuccessLabel}}</button>';
  		  '</div>'; 
      }else if(options.type == 'alert'){
    	  bodyContent +='  <div class="modal-body b'+ options.type +'">' + options.text + '</div>';
      }else if(options.type == 'confirm'){
    	  bodyContent +='  <div class="modal-body b'+ options.type +'">' + options.text + '</div>';
          defaultFooter = '<div class="text-center" id="dBtn">'+
    		'	<button type="button" class="btn btn-default confirm-btn" data-dismiss="modal" ng-click="$modalCancel()">Close</button>'+ 
    		'	<button type="button" class="btn btn-default confirm-btn" ng-click="$modalSuccess()">{{$modalSuccessLabel}}</button>' +
    		//'	<button type="button" class="btn btn-primary buttonDecor" ng-click="'+options.success.fn+'">{{$modalSuccessLabel}}</button>'+
    		'</div>'; 
      }else if(options.type == 'prompt'){
    	  bodyContent +='  <div class="modal-body b'+ options.type +'">' + options.text + '</div>';
      }

      var footerTemplate = '';
      if(options.withFooter){
    	  footerTemplate = '<div class="modal-footer f'+ options.type +'" style="border-top: none;">' +
    	  (options.footerTemplate || defaultFooter) + '</div>';
      }

      //alert(footerTemplate);
      
      var modalEl = angular.element(
        '<div class="show ' + options.modalClass + ' fade d'+ options.type +'"' + idAttr + '>' +
        '	<div class="modal-'+ options.type +'">' +
        '  		<div class="modal-content bgContent">'+
		' 			<div class="modal-header">'+
		'				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ng-click="$modalCancel()">&times;</button>' +
		'				<h4 class="modal-title text-center" style="color: #1d72a6;">{{$title}}</h4>' +
        '			</div>' + bodyContent + footerTemplate +
        '		</div>' +
        '	</div>' +  
        '</div>');
      
      modalEl.css('top', options.top);
      modalEl.css('height', options.height);
      modalEl.css('width', options.width);
      modalEl.css('margin', '0 auto');
      
      var backdropEl = angular.element('<div class="bd'+ options.type +'" ng-click="$modalCancel()"></div>');
      backdropEl.addClass(options.backdropClass);
      backdropEl.addClass('fade in');
     
      var handleEscPressed = function(event) {
        if (event.keyCode === 27) {
          scope.$modalCancel();
        }
      };

     // alert(backdropEl.html());

      var closeFn = function() {
        body.unbind('keydown', handleEscPressed);
        modalEl.remove();
        if (options.backdrop) {
          backdropEl.remove();
        }
      };

      body.bind('keydown', handleEscPressed);
      var ctrl, locals,scope = options.scope || $rootScope.$new();
      scope.$title = options.title;
      //scope.$modalCancel = closeFn;
      scope.$modalCancel = function() {
          var callFn = options.close.fn;
          if(callFn) callFn.call(this);
          closeFn();
          //scope.$modalCancel();
      };     
      
      scope.$modalSuccess = function() {
        var callFn = options.success.fn || closeFn;
        callFn.call(this);
        if(options.success.hide)
        	scope.$modalCancel();
      };
      scope.$modalSuccessLabel = options.success.label;
      if (options.controller) {
        locals = angular.extend({$scope: scope}, passedInLocals);
        ctrl = $controller(options.controller, locals);
        // Yes, ngControllerController is not a typo
        modalEl.contents().data('$ngControllerController', ctrl);
      }
      
      $compile(modalEl)(scope);
      $compile(backdropEl)(scope);
      body.append(modalEl);
      if (options.backdrop){
    	  body.append(backdropEl);
      }
      
      $timeout(function() {
        modalEl.addClass('in');
      }, 20);
    };
    
    dialogAPI.createAlert = function(template, options, passedInLocals) {
    	//need to work on this
    	var box = angular.element('<div ng-click="$modalCancel()">Test</div>');
    	$compile(box)(scope);
        body.append(box); 
        $timeout(function() {
        	box.addClass('in');
        }, 20);
    };
    
    dialogAPI.createPrompt = function(template, options, passedInLocals) {
    	//need to work on this
    	var box = angular.element('<div ng-click="$modalCancel()">Test</div>');
    	$compile(box)(scope);
        body.append(box); 
        $timeout(function() {
        	box.addClass('in');
        }, 20);
    };

    return dialogAPI;
});
