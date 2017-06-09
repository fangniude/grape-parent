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

