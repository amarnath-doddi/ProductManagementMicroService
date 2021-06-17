--create schema fund_management;
CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NOCYCLE;
create table category(id number primary key, name varchar(30));
create table product(id number primary key, category_id number,name varchar(50),unit varchar(10),price number(5,2), foreign key(category_id) references category(id));
