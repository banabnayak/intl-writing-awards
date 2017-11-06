	var MONTHS = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
	var isChrome = !!window.chrome || (navigator.userAgent.indexOf("Chrome") != -1);
	function todayDate(){
		var today = new Date();
		var dd = parseInt(today.getDate()) - 1;
		var nd = dd + "." + (today.getMonth()+1) + "." + today.getFullYear();
		return nd;
	}

	function niceDate(dt){
		if(dt == null || dt == '') return;
		var d = dt.split('-');
		return d[2] + '-'+ MONTHS[d[1]-1] + '-' + d[0];
	}

	function cuteDate(dt){
		if(dt == null || dt == '') return;
		var d = dt.split('.');
		return d[0] + '-'+ MONTHS[d[1]-1] + '-' + d[2];
	}

	function boundDate(dt, c){
		if(dt == null || dt == '') return;
		var d = dt.split('.');
		var dd = parseInt(d[0]) + c;
		if(dd < 10) dd = '0'+dd;
		var mm = parseInt(d[1]);
		if(mm < 10) mm = '0'+mm;
		var str =  d[2] + '-' + mm + '-' + dd;
		return str;
	}
	
	function getBoundDate(dt, c){
		if(dt == null || dt == '') return null;
		var date = new Date(dt);
		if(date != 'Invalid Date'){
			if(isChrome){
				dt = (date.getMonth()+1) + "." + date.getDate() + "." + date.getFullYear();				
			}else{
				dt = date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear();
			}
		}
		return boundDate(dt, c);
	}

	function niceDuration(sd, ed){
		if(sd == null || ed == null || sd == '' || ed == '' ) return 'Please click here to set the dates';
		return cuteDate(sd) + ' to ' + cuteDate(ed);
	}

