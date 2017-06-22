-- apply changes
create table account_account (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  mail                          varchar(64),
  phone                         varchar(32),
  password                      varchar(32),
  salt                          varchar(16),
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_account_account_primary_key unique (primary_key),
  constraint pk_account_account primary key (id)
);

create table permission_account_resource_relation (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    integer not null,
  resource_cls                  varchar(255) not null,
  resource_id                   integer not null,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_account_resource_relation primary key (id)
);

create table permission_account_role_relation (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    integer not null,
  role_id                       integer not null,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_role_relation_primary_key unique (primary_key),
  constraint pk_permission_account_role_relation primary key (id)
);

create table group_group (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  type_id                       integer,
  leader                        varchar(255),
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_primary_key unique (primary_key),
  constraint pk_group_group primary key (id),
  foreign key (type_id) references group_group_type (id) on delete restrict on update restrict
);

create table group_group_type (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  root                          int default 0 not null,
  cls                           varchar(255),
  parent_id                     integer,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_type_primary_key unique (primary_key),
  constraint pk_group_group_type primary key (id),
  foreign key (parent_id) references group_group_type (id) on delete restrict on update restrict
);

create table permission_resource_relation (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  parent_cls                    varchar(255),
  parent_id                     integer,
  child_cls                     varchar(255),
  child_id                      integer,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_resource_relation primary key (id)
);

create table permission_role (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_primary_key unique (primary_key),
  constraint pk_permission_role primary key (id)
);

create table permission_role_resource_relation (
  id                            integer not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  role_id                       integer not null,
  resource_cls                  varchar(255) not null,
  resource_id                   integer not null,
  version                       integer not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_role_resource_relation primary key (id)
);

