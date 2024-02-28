CREATE TABLE IF NOT EXISTS monitoring_service.ms_authority (
    id uuid default random_uuid() PRIMARY KEY,
    name VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_user (
    id uuid default random_uuid() PRIMARY KEY,
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_deleted BOOLEAN);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_user_authority (
    user_id uuid,
    authority_id uuid,
    FOREIGN KEY (user_id) REFERENCES monitoring_service.ms_user (id),
    FOREIGN KEY (authority_id) REFERENCES monitoring_service.ms_authority (id));

CREATE TABLE IF NOT EXISTS monitoring_service.ms_meter_type (
    id uuid default random_uuid() PRIMARY KEY,
    type VARCHAR(255) UNIQUE);

CREATE TABLE IF NOT EXISTS monitoring_service.ms_meter_reading (
    id uuid default random_uuid() PRIMARY KEY,
    date TIMESTAMP,
    value INT,
    user_id uuid,
    meter_type_id uuid,
    FOREIGN KEY (user_id) REFERENCES monitoring_service.ms_user (id),
    FOREIGN KEY (meter_type_id) REFERENCES monitoring_service.ms_meter_type (id));

CREATE TABLE IF NOT EXISTS audit.ms_audit_user (
    id uuid default random_uuid() PRIMARY KEY,
    audit_time TIMESTAMP,
    auditing_args VARCHAR(255),
    audit_action VARCHAR(255));