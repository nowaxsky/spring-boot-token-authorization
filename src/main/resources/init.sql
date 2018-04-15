create table T_USER (
  ID varchar(20) unique not null,
  PASSWORD varchar(20) not null,
  primary key(ID)
);

insert into T_USER values ('admin', 'password');