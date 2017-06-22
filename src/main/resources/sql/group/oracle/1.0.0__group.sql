-- apply changes
create table account_account (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  mail                          varchar2(64),
  phone                         varchar2(32),
  password                      varchar2(32),
  salt                          varchar2(16),
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_account_account_primary_key unique (primary_key),
  constraint pk_account_account primary key (id)
);
create sequence account_account_seq;

create table permission_account_resource_relation (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  account_id                    number(19) not null,
  resource_cls                  varchar2(255) not null,
  resource_id                   number(19) not null,
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_account_resource_relation primary key (id)
);
create sequence permission_account_resource_relation_seq;

create table permission_account_role_relation (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  account_id                    number(19) not null,
  role_id                       number(19) not null,
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_account_role_relation_primary_key unique (primary_key),
  constraint pk_permission_account_role_relation primary key (id)
);
create sequence permission_account_role_relation_seq;

create table group_group (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  type_id                       number(19),
  leader                        varchar2(255),
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_primary_key unique (primary_key),
  constraint pk_group_group primary key (id)
);
create sequence group_group_seq;

create table group_group_type (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  root                          number(1) default 0 not null,
  cls                           varchar2(255),
  parent_id                     number(19),
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_group_group_type_primary_key unique (primary_key),
  constraint pk_group_group_type primary key (id)
);
create sequence group_group_type_seq;

create table permission_resource_relation (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  parent_cls                    varchar2(255),
  parent_id                     number(19),
  child_cls                     varchar2(255),
  child_id                      number(19),
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_resource_relation primary key (id)
);
create sequence permission_resource_relation_seq;

create table permission_role (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_primary_key unique (primary_key),
  constraint pk_permission_role primary key (id)
);
create sequence permission_role_seq;

create table permission_role_resource_relation (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  role_id                       number(19) not null,
  resource_cls                  varchar2(255) not null,
  resource_id                   number(19) not null,
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_permission_role_resource_relation_primary_key unique (primary_key),
  constraint pk_permission_role_resource_relation primary key (id)
);
create sequence permission_role_resource_relation_seq;

alter table group_group add constraint fk_group_group_type_id foreign key (type_id) references group_group_type (id);
create index ix_group_group_type_id on group_group (type_id);

alter table group_group_type add constraint fk_group_group_type_parent_id foreign key (parent_id) references group_group_type (id);
create index ix_group_group_type_parent_id on group_group_type (parent_id);

