source lifeissharingdb-schema.sql;

insert into usuario values('Paco','paco@gmail.com','paco2015', MD5('paco'));
insert into user_roles values ('paco2015', 'registered');
insert into usuario values('Nacho','nacho@gmail.com','NachoTelematic', MD5('nacho'));
insert into user_roles values ('NachoTelematic', 'registered');
insert into usuario values('Angel','angel@gmail.com','AngelSupervia', MD5('angel'));
insert into user_roles values ('AngelSupervia', 'registered');
insert into usuario values('David','david@gmail.com','DavidArroyo', MD5('david'));
insert into user_roles values ('DavidArroyo', 'registered');
insert into usuario values('Josep','josep@gmail.com','JosepLopez', MD5('josep'));
insert into user_roles values ('JosepLopez', 'registered');
insert into usuario values('Jorge','jorge@gmail.com','jorge_messi', MD5('jorge'));
insert into user_roles values ('jorge_messi', 'registered');

select sleep(1);insert into lista(nombre, creador) values ('Fiesta', 'paco2015');
select sleep(1);insert into lista(nombre, creador) values ('Cena', 'NachoTelematic');
select sleep(1);insert into lista(nombre, creador) values ('Barbacoa', 'AngelSupervia');
select sleep(1);insert into lista(nombre, creador) values ('Navidad', 'AngelSupervia');
select sleep(1);insert into lista(nombre, creador) values ('Compra Casa', 'NachoTelematic');
select sleep(1);insert into lista(nombre, creador) values ('MediaMarkt', 'DavidArroyo');
select sleep(1);insert into lista(nombre, creador) values ('Empresa', 'JosepLopez');
select sleep(1);insert into lista(nombre, creador) values ('Vacaciones', 'NachoTelematic');
select sleep(1);insert into lista(nombre, creador) values ('Asesinato', 'NachoTelematic');


insert into editores(username, idlista) values ('paco2015',1);

insert into editores(username, idlista) values ('NachoTelematic',2);
insert into editores(username, idlista) values ('paco2015',2);
insert into editores(username, idlista) values ('AngelSupervia',2);


insert into editores(username, idlista) values ('AngelSupervia',3);
insert into editores(username, idlista) values ('DavidArroyo',3);
insert into editores(username, idlista) values ('NachoTelematic',3);

insert into editores(username, idlista) values ('jorge_messi',4);
insert into editores(username, idlista) values ('NachoTelematic',4);
insert into editores(username, idlista) values ('AngelSupervia',4);

insert into editores(username, idlista) values ('DavidArroyo',5);
insert into editores(username, idlista) values ('NachoTelematic',5);

insert into editores(username, idlista) values ('JosepLopez',6);
insert into editores(username, idlista) values ('paco2015',6);
insert into editores(username, idlista) values ('DavidArroyo',6);

insert into editores(username, idlista) values ('jorge_messi',7);
insert into editores(username, idlista) values ('JosepLopez',7);

insert into editores(username, idlista) values ('jorge_messi',8);
insert into editores(username, idlista) values ('NachoTelematic',8);
insert into editores(username, idlista) values ('AngelSupervia',8);

insert into editores(username, idlista) values ('NachoTelematic',9);
insert into editores(username, idlista) values ('DavidArroyo',9);

insert into item(description,id) values ('Cocacola',1);
insert into item(description,id) values ('Cerveza',1);

insert into item(description,id) values ('Fanta',2);
insert into item(description,id) values ('Ron',2);
insert into item(description,id) values ('Bacardi',2);
insert into item(description,id) values ('Vodka',2);
insert into item(description,id) values ('JB',2);

insert into item(description,id) values ('Carne',3);
insert into item(description,id) values ('Hamburguesas',3);
insert into item(description,id) values ('Patatas',3);
insert into item(description,id) values ('Bebida',3);

insert into item(description,id) values ('Neulas',4);
insert into item(description,id) values ('Turrones',4);
insert into item(description,id) values ('Cava Freixenet',4);
insert into item(description,id) values ('Gambas',4);
insert into item(description,id) values ('Entrecot',4);
insert into item(description,id) values ('Orujo',4);
insert into item(description,id) values ('Regalos',4);
insert into item(description,id) values ('Pescado',4);
insert into item(description,id) values ('Pica Pica',4);

insert into item(description,id) values ('Yogures',5);
insert into item(description,id) values ('Estrella Damm',5);

insert into item(description,id) values ('Disco duro',6);
insert into item(description,id) values ('Portatil',6);
insert into item(description,id) values ('DVD',6);
insert into item(description,id) values ('Auriculares',6);

insert into item(description,id) values ('Folios',7);
insert into item(description,id) values ('Tinta impresora',7);
insert into item(description,id) values ('Cable Ethernet RJ45 para switch',7);

insert into item(description,id) values ('Ropa interior',8);
insert into item(description,id) values ('Jabon',8);
insert into item(description,id) values ('Toalla',8);
insert into item(description,id) values ('Chanclas',8);
insert into item(description,id) values ('Camisetas',8);
insert into item(description,id) values ('Pantalones',8);


