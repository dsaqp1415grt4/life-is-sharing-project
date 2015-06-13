var API_BASE_URL = "http://localhost:8080/lifeissharing-api";
var USERNAME = "NachoTelematic";
var PASSWORD = "nacho";

data = location.search.substring(1,location.search.length);
iddatalista = data.split("$");
id_lista = iddatalista[1];

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
	getListsfromUser();
    getListabyid(id_lista);
});

//Get info completa de una lista

$("#button_getalistaxid").click(function(e){
    e.preventDefault();
    getListabyid($("#idlista").val()); 
});

//Post Lista

$("#button_crear_lista").click(function(e){
    e.preventDefault();
    $('#create_result').text('');
    if($("#crear_nombre_lista").val() == ""){
    	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún nombre a la lista</strong></div>').appendTo($('#create_result'));
    }
    else{
        $('<br><br><div class="alert alert-success"> <strong>Estas creando una lista</strong></div>').appendTo($("#create_result"));
    	var newlista = new Object();
    	newlista.nombre = $("#crear_nombre_lista").val();
        createLista(newlista);
    }
});

$("#button_crear_item").click(function(e){
    e.preventDefault();
    $('#create_item_result').text('');
    if($("#crear_nombre_lista").val() == ""){
    	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún nombre a la lista</strong></div>').appendTo($('#create_item_result'));
    }
    else{
        $('<br><br><div class="alert alert-success"> <strong>Estas creando un item</strong></div>').appendTo($("#create_item_result"));
    	var newitem = new Object(); 
    	newitem.description = $("#crear_nombre_item").val();
    	createitem(newitem);
    }
});

//Put item de una lista

/*$("#button_update_item").click(function(e){
    e.preventDefault();
   $("#update_result").text('');
    var now = new Date().getTime();
if($("#update_item_lista").val() == "")
{
    $('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún nombre al ítem</strong></div>').appendTo($('#update_result'));  
}

   else{
   		var updateitem = new Object();
   		var updatelista = new Object();
       	updateitem.description = $("#update_nombre_item").val();
       	$("#update_result").text(now);
       	updatelista.ultima_modificacion = now;	
	   	updateitem(updatelista);
   }

});*/

//List
function getListsfromUser(url) {
	var url = API_BASE_URL + '/listas';
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
						$('<br><strong> Nombre: <a class="btn btn-link btn-xs" href="mostrar.html?$'+lista.idlista+'" id="button_getalistaxid" >' + lista.nombre + '</strong></a><br>').appendTo($("#resultlistas"));
						$('<strong> Creador: </strong> ' + lista.creador + '<br>').appendTo($("#resultlistas"));
						$('<strong> ID: </strong>'+lista.idlista+'<br>').appendTo($("#resultlistas"));
                        var dateTime1 = new Date(lista.fecha_creacion);
						$('<strong> Fecha de creación: </strong> ' + dateTime1.toLocaleTimeString() +'   '+ dateTime1.toLocaleDateString() + '<br>').appendTo($("#resultlistas"));
                        var dateTime2 = new Date(lista.ultima_modificacion);
	                    $('<strong>Última modificación: </strong>' + dateTime2.toLocaleTimeString() +'   '+ dateTime2.toLocaleDateString()+ '<br>').appendTo($("#resultlistas"));
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
                var dateTime1 = new Date(listaxid.fecha_creacion);
				$('<strong>			Fecha de creación: </strong> ' + dateTime1.toLocaleTimeString() +'   '+ dateTime1.toLocaleDateString() + '<br>').appendTo($("#resultlistasxid"));
                var dateTime2 = new Date(listaxid.ultima_modificacion);
	            $('<strong>			Última modificación: </strong>' + dateTime2.toLocaleTimeString() +'   '+ dateTime2.toLocaleDateString()+ '<br>').appendTo($("#resultlistasxid"));
 
         getItemsbylistaid(id_lista);
         getEditoresdelistaid(id_lista);
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
					$('<br><strong>			Items: </strong><br>').appendTo($("#resultitemsxid"));
				$.each(item, function(i,v){
					var item = v;
					if(i == "item"){
						$('<br><br><div class="alert alert-danger"> <strong>Lista sin items</strong></div>').appendTo($('#resultitemsxid'));
					}
					else{
						$('<strong>			> ' + item.description + '</strong>     <button class="btn btn-link btn-xs" type="button" id="enlace_editar_items" ><i class="fa fa-pencil"></i>    </button> <button class="btn btn-link btn-xs" type="button" id="enlace_eliminar_items" ><i class="fa fa-trash-o"></i></button><br>').appendTo($("#resultitemsxid"));
						//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
						// ID Item pensarlo bien si hace falta o no, en principio no
					}
					
				})
		})
		
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Se ha producido un error al mostrar la lista de ítems </strong></div>').appendTo($('#resultitemsxid'));
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
		contentType : "application/vnd.life.api.lista+json; charset=utf-8",
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong>Lista creada con éxito</div>').appendTo($("#create_result"));
		
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Se ha producido un error</strong> </div>').appendTo($("#create_result"));
	});

}

//Crear un item en una lista

function createitem(newitem){
	var url = API_BASE_URL + '/listas'+idlista+'/items';
	var data = JSON.stringify(newitem);

	$("#create_result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType : "application/vnd.life.api.lista.collection+json; charset=utf-8",
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"><strong>Item añadido a la lista con éxito</strong></div>').appendTo($("#create_result"));
		
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Se ha producido un error</strong> </div>').appendTo($("#create_result"));
	});

}

//Actualizar item de una lista

/*function updateItem(lista) {
	var url = API_BASE_URL + '/listas/' + file.name;
	var data = JSON.stringify(lista);

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
		$('<br><br><strong>			Nombre: ' + lista.nombre + '</strong><br>').appendTo($("#resultlistasxid"));
		$('<strong>			Creador: </strong> ' + lista.creador + '<br>').appendTo($("#resultlistasxid"));
		$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
		$('<strong>			Fecha: </strong> ' + lista.fecha_creacion + '<br>').appendTo($("#resultlistasxid"));
        $('<strong>			Ultima modificación: </strong>' + lista.ultima_modificacion + '<br><br>').appendTo($("#resultlistasxid"));
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#result"));
	});

}*/

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

function getURIParameter(param, asArray) {
    return document.location.search.substring(1).split('&').reduce(function(p,c) {
        var parts = c.split('=', 2).map(function(param) { return decodeURIComponent(param); });
        if(parts.length == 0 || parts[0] != param) return (p instanceof Array) && !asArray ? null : p;
        return asArray ? p.concat(parts.concat(true)[1]) : parts.concat(true)[1];
    }, []);
}

