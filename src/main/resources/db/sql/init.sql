create user "admin";
alter user "admin" with PASSWORD 'admin';
create schema "monitoring_service";
alter schema "monitoring_service" owner to "admin";
create schema "audit";
alter schema "audit" owner to "admin";
create schema "settings";
alter schema "settings" owner to "admin";