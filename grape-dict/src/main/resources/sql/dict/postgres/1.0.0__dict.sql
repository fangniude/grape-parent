-- apply changes
create table dict_dict (
  id                            bigserial not null,
  primary_key                   varchar(64) not null,
  name                          varchar(255),
  remark                        varchar(255),
  category                      varchar(32) not null,
  value                         varchar(32) not null,
  version                       bigint not null,
  when_created                  timestamptz not null,
  when_updated                  timestamptz not null,
  constraint uq_dict_dict_primary_key unique (primary_key),
  constraint pk_dict_dict primary key (id)
);

