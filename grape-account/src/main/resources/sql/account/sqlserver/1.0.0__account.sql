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

