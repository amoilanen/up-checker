# Rename group_table to groups

# --- !Ups

alter table group_table rename to groups;

# --- !Downs

alter table groups rename to group_table;
