CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE time_record (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    description TEXT,

    CONSTRAINT fk_time_record_task
        FOREIGN KEY (task_id) REFERENCES task(id)
);