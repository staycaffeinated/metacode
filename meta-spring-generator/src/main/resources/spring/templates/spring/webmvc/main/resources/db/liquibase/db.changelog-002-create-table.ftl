--changeset author:id1
create table if not exists "yourschema".yourtable (
  id int primary key,
  text varchar(64),
  resource_id varchar(50) unique not null
)
