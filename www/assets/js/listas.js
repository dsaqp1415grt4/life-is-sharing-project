var API_BASE_URL = "http://localhost:8080/lifeissharing-api";
var USERNAME = "NachoTelematic";
var PASSWORD = "nacho";

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
				var listas = data;
				$.each(listas, function(i, v) {
					var lista = v;
					if (i == "links"){	
					}
					
					else{
					$.each(lista, function(j,v){
					
						var lista = v;
						$('<br><strong>Nombre: </strong>' + lista.nombre + '<br>').appendTo($("#resultlistas"));
						$('<strong> Creador: </strong> ' + lista.creador + '<br>').appendTo($("#resultlistas"));
						//$('<br><strong> ID: ' + lista.idlista + '</strong><br>').appendTo($("#resultlistas"));
						$('<strong> fecha: </strong> ' + lista.fecha_creacion + '<br>').appendTo($("#resultlistas"));
	                    $('<strong>ultima_mod: </strong>' + lista.ultima_modificacion + '<br>').appendTo($("#resultlistas"));
					})
					}

				});
				

	}).fail(function() {
		$("#resultlistas").text("Actualmente no dispones de ninguna lista.");
	});

}

//Get 
function getListabyid(listaid) {
	var url = API_BASE_URL + '/lista/' + listaid;
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