var API_BASE_URL = "http://localhost:8080/lifeissharing";
var USERNAME = "";
var PASSWORD = "";

$.ajaxSetup({
    headers: { 'Authorization': "Basic " + btoa(USERNAME + ':' + PASSWORD) }
});


$("#boton_login").click(function(e){
    e.preventDefault();
    setCookie("usuario", $("#user").val());
    var z = getCookie("usuario");
    console.log(z);
});

//List

$(document).ready(function(){
    var url = API_BASE_URL + '/listas';
	getListsfromUser(url);
});

//get

$("#button_get").click(function(e){
    e.preventDefault();
    getGamebyid($("#id").val());
//}
});

//List
function getListsfromUser(url) {
	
	$("#resultlistas").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var repos = data.games;
				
				$.each(repos, function(i, v) {
					var file = v;

					$('<br><strong> ID: ' + file.id + '</strong><br>').appendTo($('"#resultlistas"'));
					$('<strong> URL: </strong> ' + file.url + '<br>').appendTo($('"#resultlistas"'));
					$('<strong> Description: </strong> ' + file.description + '<br>').appendTo($('#resultlistas'));
                    $('<strong>Creation Date: </strong>' + file.creationdate + '<br>').appendTo($('#resultlistas'));
                    $('<strong>Userlist: </strong>' + file.userlist + '<br>').appendTo($('#resultlistas'));
                    
				});
				

	}).fail(function() {
		$("#resultlistas").text("Actualmente no dispones de ninguna lista.");
	});

}

//Get 
function getGamebyid(gameid) {
	var url = API_BASE_URL + '/game/' + gameid;
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var partida = data;

				$('<br><strong> id: ' + partida.id + '</strong><br>').appendTo($('#result'));
				$('<strong> URL: </strong> ' + partida.url + '<br>').appendTo($('#result'));
				$('<strong> Description: </strong> ' + partida.description + '<br>').appendTo($('#result'));
                $('<strong>Creation Date: </strong>' + partida.creationdate + '<br>').appendTo($('#result'));
                $('<strong>Userlist: </strong>' + partida.userlist + '<br>').appendTo($('#result'));

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> game not found </div>').appendTo($('#result'));
	});

}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}