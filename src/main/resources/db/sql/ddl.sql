CREATE TABLE IF NOT EXISTS monitoring_service.ms_authority (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_user (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_deleted BOOLEAN);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_user_authority (
    user_id BIGINT,
    authority_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES monitoring_service.ms_user (id),
    FOREIGN KEY (authority_id) REFERENCES monitoring_service.ms_authority (id));

CREATE TABLE IF NOT EXISTS monitoring_service.ms_meter_type (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_meter_reading (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP,
    value INT,
    user_id BIGINT,
    meter_type_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES monitoring_service.ms_user (id),
    FOREIGN KEY (meter_type_id) REFERENCES monitoring_service.ms_meter_type (id));

CREATE TABLE IF NOT EXISTS audit.ms_audit_user (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP,
    auditing_entity_id BIGINT,
    action VARCHAR(255));