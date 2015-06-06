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

//Get

$("#button_getalistaxid").click(function(e){
    e.preventDefault();
    getGamebyid($("#idlista").val());
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
						$('<br><strong>Nombre:' + lista.nombre + '</strong><br>').appendTo($("#resultlistas"));
						$('<strong> Creador: </strong> ' + lista.creador + '<br>').appendTo($("#resultlistas"));
						$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
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
	$("#resultlistasxid").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var listaxid = data;

				$('<br><strong>Nombre:' + lisxidta.nombre + '</strong><br>').appendTo($("#resultlistasxid"));
				$('<strong> Creador: </strong> ' + listaxid.creador + '<br>').appendTo($("#resultlistasxid"));
				//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
				$('<strong> fecha: </strong> ' + listaxid.fecha_creacion + '<br>').appendTo($("#resultlistasxid"));
                $('<strong>ultima_mod: </strong>' + listaxid.ultima_modificacion + '<br>').appendTo($("#resultlistasxid"));

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>No existe ninguna lista de la que seas creador con ese id</strong></div>').appendTo($('#resultlistasxid'));
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