-- apply changes
create table dict_dict (
  id                            numeric(19) identity(1,1) not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  category                      varchar(32) not null,
  value                         varchar(32) not null,
  version                       numeric(19) not null,
  when_created                  datetime2 not null,
  when_updated                  datetime2 not null,
  constraint uq_dict_dict_primary_key unique (primary_key),
  constraint pk_dict_dict primary key (id)
);

