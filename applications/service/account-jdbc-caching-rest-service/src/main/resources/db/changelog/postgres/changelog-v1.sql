CREATE SCHEMA IF NOT EXISTS gf_cache;

CREATE TABLE gf_cache.accounts (
                                          id varchar(255) not NULL,
                                          name varchar(255) not NULL,
                                          CONSTRAINT account_vpkey PRIMARY KEY (id)
);