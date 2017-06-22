-- apply changes
create table account_account (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  mail                          varchar(64),
  phone                         varchar(32),
  password                      varchar(32),
  salt                          varchar(16),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_account_account_primary_key unique (primary_key),
  constraint pk_account_account primary key (id)
);

create table permission_account_resource_relation (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    bigint not null,
  resource_cls                  varchar(255) not null,
  resource_id                   bigint not null,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_account_resource_relation primary key (id)
);

create table permission_account_role_relation (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  account_id                    bigint not null,
  role_id                       bigint not null,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_role_relation_primary_key unique (primary_key),
  constraint pk_permission_account_role_relation primary key (id)
);

create table group_group (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  type_id                       bigint,
  leader                        varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_primary_key unique (primary_key),
  constraint pk_group_group primary key (id)
);

create table group_group_type (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  root                          boolean default false not null,
  cls                           varchar(255),
  parent_id                     bigint,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_type_primary_key unique (primary_key),
  constraint pk_group_group_type primary key (id)
);

create table permission_resource_relation (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  parent_cls                    varchar(255),
  parent_id                     bigint,
  child_cls                     varchar(255),
  child_id                      bigint,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_resource_relation primary key (id)
);

create table permission_role (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_primary_key unique (primary_key),
  constraint pk_permission_role primary key (id)
);

create table permission_role_resource_relation (
  id                            bigint auto_increment not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  role_id                       bigint not null,
  resource_cls                  varchar(255) not null,
  resource_id                   bigint not null,
  version                       bigint not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_role_resource_relation primary key (id)
);

alter table group_group add constraint fk_group_group_type_id foreign key (type_id) references group_group_type (id) on delete restrict on update restrict;
create index ix_group_group_type_id on group_group (type_id);

alter table group_group_type add constraint fk_group_group_type_parent_id foreign key (parent_id) references group_group_type (id) on delete restrict on update restrict;
create index ix_group_group_type_parent_id on group_group_type (parent_id);

