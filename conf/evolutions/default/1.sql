create table customer (
  id                            integer not null,
  name                          varchar(255),
  notes                         varchar(255),
  version                       integer not null,
  constraint pk_customer primary key (id)
);