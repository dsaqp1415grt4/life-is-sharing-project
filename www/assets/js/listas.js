var API_BASE_URL = "http://147.83.7.159:8080/lifeissharing-api";
username = getCookie("username");
password = getCookie("password");

data = location.search.substring(1,location.search.length);
iddatalista = data.split("$");
id_lista = iddatalista[1];
dataItem = data.split("@");
id_item = dataItem[1];



$.ajaxSetup({
    headers: { 'Authorization': "Basic " + btoa(username + ':' + password) }
});



$("#logout").click(function(e) {
   e.preventDefault();
   Logout();
});

$("#boton_cancelar").click(function(e){
    e.preventDefault();
    window.location.href="index.html";
});
$("#boton_cancelar2").click(function(e){
    e.preventDefault();
    window.location.href="main.html";
});

//REGISTRAR
$("#button_registrar").click(function(e){
    e.preventDefault();
    $('#registrar_result').text('');
    if($("#registrar_username").val() == ""||$("#registrar_password").val() == ""||$("#registrar_email").val() == ""||$("#registrar_nombre").val() == ""){
    	$('<br><br><div class="alert alert-danger"> <strong>Has de rellenar todos los campos</strong></div>').appendTo($('#registrar_result'));
    }
    else{
    	var newusuario = new Object(); 
    newusuario.name = $("#registrar_nombre").val();
    newusuario.email = $("#registrar_email").val();
    newusuario.username = $("#registrar_username").val();
	newusuario.password = $("#registrar_password").val();
	
	
    	registrar(newusuario);
    }
});

$("#button_no").click(function(e){
    e.preventDefault();
    window.location.href="mostrar.html?$"+id_lista;
});

$("#button_no2").click(function(e){
    e.preventDefault();
    window.location.href="main.html";
});

$("#button_no3").click(function(e){
    e.preventDefault();
    window.location.href="main.html";
});
$("#cancelar").click(function(e){
    e.preventDefault();
    window.location.href="mostrar.html?$"+id_lista;
});


//List

$(document).ready(function(){
    $("#nombre_usuario_mostrado").text(username);
	getListsfromUser();
    	getListabyname(id_lista);
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


//CREAR
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
    	createitem(newitem,id_lista);
    }
});


//DELETE


//Delete item
$("#button_delete_item").click(function(e) {
	e.preventDefault();
	deleteItem(id_lista, id_item);
			

});


//Delete lista
$("#button_delete_lista").click(function(e) {
	e.preventDefault();
	deleteLista(id_lista);
			

});

//salir
$("#button_salir").click(function(e) {
	e.preventDefault();
	salir(id_lista);
			

});



//Put item de una lista

$("#button_update_item").click(function(e){
    e.preventDefault();
   $("#actualizar_item").text();
    var now = new Date().getTime();
if($("#update_nombre").val() == "")
{
    $('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún nombre al ítem</strong></div>').appendTo($('#actualizar_item'));  
}

   else{
   		var updated = new Object();
    	updated.description = $("#update_nombre").val();
   			
	   	updateItem(updated,id_lista,id_item);
   }

});

//Invitar

$("#button_invitar_usuario").click(function(e){
    e.preventDefault();
    $('#invitar_result').text('');
    if($("#crear_nombre_lista").val() == ""){
    	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún username</strong></div>').appendTo($('#invitar_result'));
    }
    else{
    	var newEditor = new Object();
    	newEditor.username = $("#invitar_nombre").val();
        invitar(newEditor, id_lista);
    }
});







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
						$('<br><strong> Nombre: <a class="btn btn-link btn-xs" href="mostrar.html?$'+lista.idlista+'">' + lista.nombre + '</strong></a> <a class="btn btn-link btn-xs" type="button" href="borrarlista.html?$'+lista.idlista+'" ><i class="fa fa-trash-o"></i></a><br>').appendTo($("#resultlistas"));
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
//Get cuando seleccionas el nombre de la lista
function getListabyname(listaid) {
    
	var url = API_BASE_URL + '/listas/' + listaid;
	$("#resultlistasxname").text('');
if(listaid == ""){
	$('<br><br><div class="alert alert-danger"> <strong>No has introducido ningún valor de ID</strong></div>').appendTo($('#resultlistasxname'));
}

else{
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

				var listaxid = data;

				$('<br><br><strong>			Nombre: ' + listaxid.nombre + '</strong><br>').appendTo($("#resultlistasxname"));
				$('<strong>			Creador: </strong> ' + listaxid.creador + '<br>').appendTo($("#resultlistasxname"));
				//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
                var dateTime1 = new Date(listaxid.fecha_creacion);
				$('<strong>			Fecha de creación: </strong> ' + dateTime1.toLocaleTimeString() +'   '+ dateTime1.toLocaleDateString() + '<br>').appendTo($("#resultlistasxname"));
                var dateTime2 = new Date(listaxid.ultima_modificacion);
	            $('<strong>			Última modificación: </strong>' + dateTime2.toLocaleTimeString() +'   '+ dateTime2.toLocaleDateString()+ '<br>').appendTo($("#resultlistasxname"));
 
         getItemsbylistaname(id_lista);
         getEditoresdelistaname(id_lista);
			}).fail(function() {
				$('<br><br><div class="alert alert-danger"> <strong>No existe ninguna lista de la que seas editor con ese id</strong></div>').appendTo($('#resultlistasxname'));
				$("#resultitemsxname").text('');
				$("#resulteditoresxname").text('');
	});	
}

}

//Get items de la lista por nombre

function getItemsbylistaname(listaid) {
	var url = API_BASE_URL + '/listas/' + listaid + '/items';
	$("#resultitemsxname").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var items = data;
		$.each(items, function(i, v) {
				var item = v;
					$('<br><strong>			Items: <a class="btn btn-link btn-sm" href="crearitem.html?$'+listaid+'" id="enlace_crear_items" ><i class="fa fa-plus"></i>    </a></strong></strong><br>').appendTo($("#resultitemsxname"));
				$.each(item, function(i,v){
					var item = v;
					if(i == "item"){
						$('<br><br><div class="alert alert-danger"> <strong>Lista sin items</strong></div>').appendTo($('#resultitemsxname'));
					}
					else{
						$('<strong>			> ' + item.description + '</strong>     <a class="btn btn-link btn-xs" href="updateitem.html?$'+listaid+'$@'+item.iditem+'" id="enlace_editar_items" ><i class="fa fa-pencil"></i>    </a>  <a class="btn btn-link btn-xs" type="button" href="borraritem.html?$'+listaid+'$@'+item.iditem+'" ><i class="fa fa-trash-o"></i></a><br>').appendTo($("#resultitemsxname"));
						//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
						// ID Item pensarlo bien si hace falta o no, en principio no
					}
					
				})
		})
		
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Se ha producido un error al mostrar la lista de ítems </strong></div>').appendTo($('#resultitemsxname'));
	});
	
}

//Get Editores de una lista por nombre

function getEditoresdelistaname(listaid) {
	var url = API_BASE_URL + '/listas/'+ listaid +'/editores';
	$("#resulteditoresxname").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		var editores = data;
		$.each(editores, function(i, v) {
				var editor = v;
				$('<br><strong>			Editores: </strong> <a class="btn btn-link btn-xs" type="button" href="invitar.html?$'+listaid+'" ><i class="fa fa-plus"></i><i class="fa fa-user"></i></a>   <a class="btn btn-link btn-xs" type="button" href="salir.html?$'+listaid+'" ><i class="fa fa-minus"></i><i class="fa fa-user"></i></a><br>').appendTo($("#resulteditoresxname"));
				$.each(editor, function(i,v){
					var editor = v;
					
					$('<strong>			> ' + editor.username + '</strong><br>').appendTo($("#resulteditoresxname"));
					
				})
		})
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Lista sin editores</strong></div>').appendTo($('#resulteditoresxname'));
	});
	
}

//Get cuando buscas la lista por id
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
 
         getItemsbylistaid($("#idlista").val());
         getEditoresdelistaid($("#idlista").val());
			}).fail(function() {
				$('<br><br><div class="alert alert-danger"> <strong>No existe ninguna lista de la que seas editor con ese id</strong></div>').appendTo($('#resultlistasxid'));
				$("#resultitemsxid").text('');
				$("#resulteditoresxid").text('');
	});	
}

}



//Get items de la lista por id

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
					$('<br><strong>			Items: <a class="btn btn-link btn-sm" href="crearitem.html?$'+listaid+'" id="enlace_crear_items" ><i class="fa fa-plus"></i>    </a></strong><br>').appendTo($("#resultitemsxid"));
				$.each(item, function(i,v){
					var item = v;
					if(i == "item"){
						$('<br><br><div class="alert alert-danger"> <strong>Lista sin items</strong></div>').appendTo($('#resultitemsxid'));
					}
					else{
						$('<strong>			> ' + item.description + '</strong>     <a class="btn btn-link btn-xs" href="updateitem.html?$'+listaid+'$@'+item.iditem+'" id="enlace_editar_items" ><i class="fa fa-pencil"></i>    </a> <a class="btn btn-link btn-xs" type="button" href="borraritem.html?$'+listaid+'$@'+item.iditem+'" ><i class="fa fa-trash-o"></i></a><br>').appendTo($("#resultitemsxid"));
						//$('<strong> ID: </strong>' + lista.idlista + '<br>').appendTo($("#resultlistas"));
						// ID Item pensarlo bien si hace falta o no, en principio no
					}
					
				})
		})
		
	}).fail(function() {
					$('<br><br><div class="alert alert-danger"> <strong>Se ha producido un error al mostrar la lista de ítems </strong></div>').appendTo($('#resultitemsxid'));
	});
	
}


//Get Editores de una lista por id

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
				$('<br><strong>			Editores: </strong>  <a class="btn btn-link btn-xs" type="button" href="invitar.html?$'+listaid+'" ><i class="fa fa-plus"></i><i class="fa fa-user"></i></a>   <a class="btn btn-link btn-xs" type="button" href="salir.html?$'+listaid+'" ><i class="fa fa-minus"></i><i class="fa fa-user"></i></a><br>').appendTo($("#resulteditoresxid"));
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
		sleep(1000);
        	location.href = "main.html";
		
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Se ha producido un error</strong> </div>').appendTo($("#create_result"));
	});

}

//Crear un item en una lista

function createitem(newitem,id_lista){
	var url = API_BASE_URL + '/listas/'+id_lista+'/items';
	var data = JSON.stringify(newitem);

	$("#create_result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType : "application/vnd.life.api.lista+json; charset=utf-8",
		data : data,
	}).done(function(data, status, jqxhr) {
        
		$('<div class="alert alert-success"><strong>Item añadido a la lista con éxito</strong></div>').appendTo($("#create_item_result"));
		sleep(1000);        
		window.location.href="mostrar.html?$"+id_lista;
		
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Se ha producido un error</strong> </div>').appendTo($("#create_item_result"));
	});

}

//DELETE ITEM


function deleteItem(id_lista, id_item){

	var url = API_BASE_URL + '/listas/'+ id_lista + '/items/'+ id_item;
	$("#delete_item").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
		statusCode: {
    		202: function() {$('<div class="alert alert-danger"> <strong>Ok!</strong> Item Borrado </div>').appendTo($("#delete_item"));},
		404: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> Item no encontrado </div>').appendTo($("#delete_item"));}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Item borrado correctamente </div>').appendTo($("#delete_item"));
		sleep(1000);
		window.location.href="mostrar.html?$"+id_lista;				
  	});




}

//DELETE


function deleteLista(id_lista){

	var url = API_BASE_URL + '/listas/'+ id_lista;
	$("#delete_lista").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
		statusCode: {
    		202: function() {$('<div class="alert alert-success"> <strong>Ok!</strong> Lista Borrada </div>').appendTo($("#delete_lista"));},
		403: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> No eres el creador de la lista </div>').appendTo($("#delete_lista"))
		sleep(1000);
		window.location.href="main.html";		;}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> Lista borrada correctamente </div>').appendTo($("#delete_lista"));
		window.location.href="main.html";				
  	});




}

//INVITAR
function invitar(newEditor, id_lista){
	var url = API_BASE_URL + '/listas/'+ id_lista + '/editores';
	var data = JSON.stringify(newEditor);

	$("#invitar_result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType : "application/vnd.life.api.lista+json; charset=utf-8",
		data : data,
		statusCode: {
		403: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> No eres el creador de la lista </div>').appendTo($("#invitar_result"));},
		404: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> No existe un usuario con ese Username </div>').appendTo($("#invitar_result"));}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong>Usuario invitado</div>').appendTo($("#invitar_result"));
		sleep(1000);
		window.location.href="mostrar.html?$"+id_lista;	
		
  	}).fail(function() {
		//$('<div class="alert alert-danger"> <strong>Se ha producido un error</strong> </div>').appendTo($("#invitar_result"));
	});

}

//SALIR
function salir(id_lista){

	var url = API_BASE_URL + '/listas/'+ id_lista +'/editores';
	$("#salir_lista").text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
		statusCode: {
    		202: function() {$('<div class="alert alert-success"> <strong>Ok!</strong> ¡¡Hasta pronto!! </div>').appendTo($("#salir_lista"));},
		404: function() {$('<div class="alert alert-danger"> <strong>Oh!</strong> No perteneces a esta lista </div>').appendTo($("#salir_lista"))
		sleep(1000);
		window.location.href="main.html";		;}
    	}
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>Ok!</strong> ¡¡Hasta pronto!! </div>').appendTo($("#salir_lista"));
		window.location.href="main.html";				
  	});




}


//Actualizar item de una lista

function updateItem(description, id_lista, id_item) {
	var url = API_BASE_URL + '/listas/' + id_lista + '/items/' + id_item;
	console.log(url);
	
	var data = JSON.stringify(description);
	console.log(data); 	 	
	$("#actualizar_item").text('');

	$.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		dataType : 'json',
		contentType : "application/vnd.life.api.lista+json; charset=utf-8",
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-success"> <strong>OK!</strong> Item añadido correcto </div>').appendTo($("#actualizar_item"));
		sleep(1000);
		window.location.href="mostrar.html?$"+id_lista;
  	}).fail(function() {
		
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#actualizar_item"));
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

//Registrar


function registrar(user) {
  var url = API_BASE_URL + '/users';
  var data = JSON.stringify(user);
  
    $.ajax({
    url : url,
    type : 'POST',
    crossDomain : true,
    dataType : 'json',  
    contentType : 'application/vnd.life.api.user+json',
    data : data,      
    }).done(function(data, status, jqxhr) {
         $('<div class="alert alert-success"> <strong>OK!</strong> ¡Ya estás registrado! </div>').appendTo($("#registrar_result"));
	  sleep(1000);
	  window.location.href="index.html";
    }).fail(function() {
         $('<div class="alert alert-danger"> <strong>OK!</strong> Error al registrarte </div>').appendTo($("#registrar_result"));
          sleep(1000);
	  window.location.href="index.html";
  });   


};


function Logout(){

document.cookie="username = "+ "";
document.cookie="loginSucessful = " + "";
window.location.href="index.html";
 
}

//SLEEP


function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}







