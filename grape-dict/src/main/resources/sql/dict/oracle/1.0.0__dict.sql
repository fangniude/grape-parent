-- apply changes
create table dict_dict (
  id                            number(19) not null,
  primary_key                   varchar2(64) not null,
  name                          varchar2(255),
  remark                        varchar2(255),
  category                      varchar2(32) not null,
  value                         varchar2(32) not null,
  version                       number(19) not null,
  when_created                  timestamp not null,
  when_updated                  timestamp not null,
  constraint uq_dict_dict_primary_key unique (primary_key),
  constraint pk_dict_dict primary key (id)
);
create sequence dict_dict_seq;

