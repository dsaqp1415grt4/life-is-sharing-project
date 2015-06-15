var API_BASE_URL = "http://147.83.7.159:8080/lifeissharing-api";

var username = getCookie("username");
var password = getCookie("password");
console.log(username);
console.log(password);

$.ajaxSetup({
    headers: { 'Authorization': "Basic " + btoa(username + ':' + password) }
});


$("#boton_signin").click(function(e){
    e.preventDefault();
	var login = new Object();
	login.username=$('#user').val();
	login.password=$('#password').val();

	document.cookie = "username = " + login.username;
	document.cookie = "password = " + login.password;
	Login(login);
	
});


function Login(login){
   var url= API_BASE_URL + '/users/login';
   var data = JSON.stringify(login);
   console.log(data);
   
   $.ajax({
      url:url,
      type:'POST',
      crossDomain: true,
      dataType:'json',
      contentType: 'application/vnd.life.api.user+json',
      data: data,
   }).done(function(data, status, jqxhr) {
            console.log(data.loginSuccessful);
            if (data.loginSuccessful == true){
		
         		document.cookie="username = " + data.username;
				document.cookie="loginSuccessful = " + data.loginSuccesful;
				window.location.href="main.html";
 

            }
            else {      
				alert('Usuario o contraseña incorrectos'); 
                console.log(data.loginSuccessful);
                        
         }
       

   }).fail(function() {
       alert('Usuario o contraseña incorrectos');  
   });


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
