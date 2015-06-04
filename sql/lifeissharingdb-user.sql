drop user 'life'@'localhost';
create user 'life'@'localhost' identified by 'life';
grant all privileges on lifeissharingdb.* to 'life'@'localhost';
flush privileges; 