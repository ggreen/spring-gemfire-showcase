-- liquibase formatted sql
-- changeset liquibaseuser:1


CREATE TABLE web_sessions.user_session (
	session_id  text PRIMARY KEY,
	user_id text NOT NULL,
	start_time_ms bigint NOT NULL,
	duration_ms bigint NULL,
	end_time_ms bigint NULL
);
-- rollback drop table user_session;
