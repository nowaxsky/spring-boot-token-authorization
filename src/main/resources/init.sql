create table T_USER (
  USER_ID varchar(20) unique not null,
  PASSWORD varchar(20) not null,
  primary key(USER_ID)
);

create table T_USER_DETAIL (
  USER_ID varchar(20) unique not null,
  USERNAME varchar(30) not null,
  primary key(USER_ID)
);

insert into T_USER values ('admin', 'password');
insert into T_USER_DETAIL values ('admin', 'chuck');
