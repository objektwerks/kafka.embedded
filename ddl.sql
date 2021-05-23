drop table device if exists;
drop table device_reading if exists;
create table device (
  id varchar(36) primary key,
  name varchar(128) not null,
  created varchar(26) not null
);
create table device_reading (
  id int primary key auto_increment,
  device_id varchar(36) not null,
  value double not null,
  unit varchar(128) not null,
  observed varchar(26) not null,
  version int not null
);