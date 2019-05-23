create database shopdemo charset utf8mb4;
use shopdemo;
create table productInfo
(id int(10) primary key auto_increment,
productname varchar(60) not null,
price int(10),inventoryQuantity int(10),
pictureLink varchar(120),
productplan text,
buyingprice int(10) default 0,
productcategory varchar(60),
unique(productname)
)charset utf8mb4;
create table users
(id int(10) primary key auto_increment,
username varchar(20),
password varchar(10),
phone varchar(11),
address varchar(30), 
unique(username))
charset utf8mb4;
create table employee
	(
			id int(10) primary key,
			name varchar(30),			
			password varchar(20),			
			departmentname varchar(30),
			job varchar(30),	
			salary int(10)
		)charset utf8mb4;

create table orderinfo
	(
username varchar(20) not null,
orderid varchar(30) not null,
orderdatetime datetime not null,
productname varchar(60)not null,
productnumber int(10)not null,
price int(10) not null,
extended_attribute_string  varchar(60) default '',
buyingprice int(10) default 0,
productcategory varchar(60)
)	charset utf8mb4;

create table category(category_id int(10) primary key auto_increment,
category_name varchar(60),
parent_id int(10),
is_end tinyint(4) default 0,
category_status tinyint(4) default 1,
category_level int(10),
unique(category_name) )charset utf8mb4;

create table extendedattribute(
attribute_id int(10) primary key auto_increment,
	product_id int(10) ,
product_name varchar(60),
attribute_name varchar(60),
attribute_value varchar(60),
unique(product_name,attribute_name))charset utf8mb4;

