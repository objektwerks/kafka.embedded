drop table device if exists;
drop table device_reading if exists;
create table device (
  id varchar(36) primary key,
  name varchar(128) not null,
  created bigint not null
);
create table device_reading (
  id int primary key auto_increment,
  device_id varchar(36) not null,
  current_value double not null,
  unit varchar(128) not null,
  datetime bigint not null,
  version double not null
);