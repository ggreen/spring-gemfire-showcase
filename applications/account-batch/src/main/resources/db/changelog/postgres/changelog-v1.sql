-- liquibase formatted sql
-- changeset liquibaseuser:1

-- Autogenerated: do not edit this file

CREATE TABLE cache_accounts.account (
    id varchar(100) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

-- rollback drop table account;