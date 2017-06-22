-- apply changes
create table account_account (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  mail                          varchar(64),
  phone                         varchar(32),
  password                      varchar(32),
  salt                          varchar(16),
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_account_account_primary_key unique (primary_key),
  constraint pk_account_account primary key (id)
);

create table permission_account_resource_relation (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    numeric(19) not null,
  resource_cls                  varchar(255) not null,
  resource_id                   numeric(19) not null,
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_permission_account_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_account_resource_relation primary key (id)
);

create table permission_account_role_relation (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    numeric(19) not null,
  role_id                       numeric(19) not null,
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_permission_account_role_relation_primary_key unique (primary_key),
  constraint pk_permission_account_role_relation primary key (id)
);

create table permission_resource_relation (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  parent_cls                    varchar(255),
  parent_id                     numeric(19),
  child_cls                     varchar(255),
  child_id                      numeric(19),
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_permission_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_resource_relation primary key (id)
);

create table permission_role (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_permission_role_primary_key unique (primary_key),
  constraint pk_permission_role primary key (id)
);

create table permission_role_resource_relation (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  role_id                       numeric(19) not null,
  resource_cls                  varchar(255) not null,
  resource_id                   numeric(19) not null,
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_permission_role_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_role_resource_relation primary key (id)
);

