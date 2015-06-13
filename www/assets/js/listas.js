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

//Get info completa de una lista

$("#button_getalistaxid").click(function(e){
    e.preventDefault();
    getListabyid($("#idlista").val());
});

//Post Lista

$("#button_crear_lista").click(function(e){
    e.preventDefault();
    if($("#crear_nombre_de_la_lista").val() == ""){
    	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún nombre a la lista</strong></div>').appendTo($('#create_result'));  	
    }
    else{
    	var newlista = new Object();
        newlista.nombre = $("#crear_nombre_de_la_lista").val();
        newlista.creador =
        newlista.idlista = 
        newlista.fecha_creacion =
        newlista.ultima_modificacion =
    	$("#create_result").text('ha funcionado bien');
        createlista(newlista);
    }
});

//Put lista

$("#button_put").click(function(e){
    e.preventDefault();
   $("#result").text('');
if($("#file_name").val() == "" || $("#url").val() == "")
{
     $('<div class="alert alert-success">  Algún campo requerido <strong>no está rellenado</strong></div>').appendTo($("#result"));
}

   else{
   var newResource = new Object();
	newResource.name = $("#file_name").val();
	newResource.url = $("#url").val();
   newResource.description = $("#descripcion").val();
	newResource.taglist = $("#tags").val();
	newResource.creationdate = $("#fecha").val();
   newResource.size = $("#tamano").val();
	
	updateResource(newResource);
   }

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
						$('<br><strong> Nombre:' + lista.nombre + '</strong><br>').appendTo($("#resultlistas"));
						$('<strong> Creador: </strong> ' + lista.creador + '<br>').appendTo($("#resultlistas"));
						$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
						$('<strong> Fecha de creación: </strong> ' + lista.fecha_creacion + '<br>').appendTo($("#resultlistas"));
	                    $('<strong>Última modificación: </strong>' + lista.ultima_modificacion + '<br>').appendTo($("#resultlistas"));
					})
					}

				});
				

	}).fail(function() {
		$("#resultlistas").text("Actualmente no dispones de ninguna lista.");
	});

}

//Get 
function getListabyid(listaid) {
	var url = API_BASE_URL + '/listas/' + listaid;
	$("#resultlistasxid").text('');
if(listaid == ""){
	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún valor de ID</strong></div>').appendTo($('#resultlistasxid'));
}

else{
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var listaxid = data;

				$('<br><br><strong>			Nombre: ' + listaxid.nombre + '</strong><br>').appendTo($("#resultlistasxid"));
				$('<strong>			Creador: </strong> ' + listaxid.creador + '<br>').appendTo($("#resultlistasxid"));
				//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
				$('<strong>			Fecha: </strong> ' + listaxid.fecha_creacion + '<br>').appendTo($("#resultlistasxid"));
                $('<strong>			Ultima modificación: </strong>' + listaxid.ultima_modificacion + '<br><br>').appendTo($("#resultlistasxid"));
 
         getItemsbylistaid($("#idlista").val());
         getEditoresdelistaid($("#idlista").val());
			}).fail(function() {
				$('<br><br><div class="alert alert-danger"> <strong>No existe ninguna lista de la que seas editor con ese id</strong></div>').appendTo($('#resultlistasxid'));
				$("#resultitemsxid").text('');
				$("#resulteditoresxid").text('');
	});	
}

}

//Get items de la lista

function getItemsbylistaid(listaid) {
	var url = API_BASE_URL + '/listas/' + listaid + '/items';
	$("#resultitemsxid").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var items = data;
		$.each(items, function(i, v) {
				var item = v;
				$.each(item, function(i,v){
					var item = v;
					
					$('<strong>			> ' + item.description + '</strong><br>').appendTo($("#resultitemsxid"));
					//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
					// ID Item pensarlo bien si hace falta o no, en principio no
				})
		})
        //hay que arreglar esto
       
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Lista sin items</strong></div>').appendTo($('#resultitemsxid'));
	});
	
}

//Get Editores de una lista

function getEditoresdelistaid(listaid) {
	var url = API_BASE_URL + '/listas/'+ listaid +'/editores';
	$("#resulteditoresxid").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var editores = data;
		$.each(editores, function(i, v) {
				var editor = v;
				$('<br><strong>			Editores: </strong><br>').appendTo($("#resulteditoresxid"));
				$.each(editor, function(i,v){
					var editor = v;
					
					$('<strong>			> ' + editor.username + '</strong><br>').appendTo($("#resulteditoresxid"));
					
				})
		})
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Lista sin editores</strong></div>').appendTo($('#resulteditoresxid'));
	});
	
}

// Crear lista nueva

function createLista(newlista){
	var url = API_BASE_URL + '/listas';
	var data = JSON.stringify(newlista);

	$("#create_result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Lista creada con éxito</div>').appendTo($("#create_result"));				
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Se ha producido un error </div>').appendTo($("#create_result"));
	});

}

//Actualizar la lista

function updateResource(file) {
	var url = API_BASE_URL + '/file/' + file.name;
	var data = JSON.stringify(file);

	$("#result").text('');

	$.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		dataType : 'json',
		data : data,
        contentType : "application/json; charset=utf-8",
		statusCode: {
    		404: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> Page not found </div>').appendTo($("#result"));}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Repository Updated</div>').appendTo($("#result"));
        $('<br><strong> Name: ' + file.name + '</strong><br>').appendTo($('#result'));
        $('<strong> URL: </strong> ' + file.url + '<br>').appendTo($('#result'));
		$('<strong> Description: </strong> ' + file.description + '<br>').appendTo($('#result'));
        $('<strong>Size: </strong>' + file.size + '<br>').appendTo($('#result'));
        $('<strong>Creation Date: </strong>' + file.creationdate + '<br>').appendTo($('#result'));
                $('<strong>Taglist: </strong>' + file.taglist + '<br>').appendTo($('#result'));
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#result"));
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

