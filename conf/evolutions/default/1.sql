# --- !Ups

create table group_table (
  id serial,
  name varchar(255) not null,
  primary key (id)
);

# --- !Downs

drop table group_table;