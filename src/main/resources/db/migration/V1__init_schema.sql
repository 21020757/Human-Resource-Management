CREATE TABLE application
(
    candidate_id BIGINT NOT NULL,
    job_id       BIGINT NOT NULL,
    CONSTRAINT pk_application PRIMARY KEY (candidate_id, job_id)
);

CREATE TABLE attendance
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    employee_id        BIGINT                NULL,
    date               date                  NULL,
    check_in_time      time                  NULL,
    check_out_time     time                  NULL,
    total_working_time DECIMAL(10,2)         NULL,
    work_days          DECIMAL(10,2)         NULL,
    status             VARCHAR(255)          NULL,
    CONSTRAINT pk_attendance PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    title       VARCHAR(255)          NULL,
    message     VARCHAR(255)          NULL,
    is_read     BIT(1)               NOT NULL,
    created_at  datetime              NULL,
    receiver_id BIGINT                NULL,
    sender_id   BIGINT                NULL,
    is_public   BIT(1)                NOT NULL,
    type        SMALLINT              NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE candidate
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    full_name          VARCHAR(255)          NULL,
    email              VARCHAR(255)          NULL,
    phone              VARCHAR(255)          NULL,
    address            VARCHAR(255)          NULL,
    gender             VARCHAR(255)          NULL,
    date_of_birth      date                  NULL,
    id_number          VARCHAR(255)          NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    resume             LONGTEXT              NULL,
    cv_letter          VARCHAR(255)          NULL,
    applied_date       date                  NULL,
    CONSTRAINT pk_candidate PRIMARY KEY (id),
    INDEX idx_candidate_fullname (full_name)
);

CREATE TABLE candidate_interview
(
    candidate_id BIGINT NOT NULL,
    interview_id BIGINT NOT NULL,
    CONSTRAINT pk_candidate_interview PRIMARY KEY (candidate_id, interview_id)
);

CREATE TABLE contract
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    start_date         date                  NULL,
    end_date           date                  NULL,
    salary             DECIMAL(10,2)         NULL,
    employee_id        BIGINT                NULL,
    active             BIT(1)                NOT NULL DEFAULT b'1',
    CONSTRAINT pk_contract PRIMARY KEY (id)
);

CREATE TABLE department
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    department_code VARCHAR(255)          NULL,
    department_name VARCHAR(255)          NULL,
    manager_id      BIGINT                NULL,
    CONSTRAINT pk_department PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    full_name          VARCHAR(255)          NULL,
    email              VARCHAR(255)          NULL,
    phone              VARCHAR(255)          NULL,
    address            VARCHAR(255)          NULL,
    gender             VARCHAR(255)          NULL,
    date_of_birth      date                  NULL,
    id_number          VARCHAR(255)          NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    employee_code      VARCHAR(255)          NULL,
    position           VARCHAR(255)          NULL,
    hire_date          date                  NULL,
    contract_type      VARCHAR(255)          NULL,
    department_id      BIGINT                NULL,
    active             BIT(1)                NOT NULL DEFAULT b'1',
    CONSTRAINT pk_employee PRIMARY KEY (id),
    INDEX idx_employee_fullname (full_name),
    INDEX idx_employee_position (position)
);

CREATE TABLE employee_review
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    date               date                  NULL,
    score              DECIMAL(10,2)         NULL,
    employee_id        BIGINT                NULL,
    reviewer_id        BIGINT                NULL,
    comment            VARCHAR(255)          NULL,
    CONSTRAINT pk_employee_review PRIMARY KEY (id)
);

CREATE TABLE interview
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    title              VARCHAR(255)          NULL,
    date               date                  NULL,
    time               time                  NULL,
    location           VARCHAR(255)          NULL,
    notes              VARCHAR(255)          NULL,
    interviewer_id     BIGINT                NULL,
    CONSTRAINT pk_interview PRIMARY KEY (id)
);

CREATE TABLE job
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    job_title          VARCHAR(255)          NULL,
    job_description    VARCHAR(255)          NULL,
    salary             DECIMAL(10,2)         NULL,
    exp                INT                   NULL,
    location           VARCHAR(100)          NOT NULL,
    position           VARCHAR(255)          NULL,
    requirements       VARCHAR(255)          NULL,
    posted_date        date                  NULL,
    closed_date        date                  NULL,
    active             BIT(1)                NOT NULL DEFAULT b'1',
    department_id      BIGINT                NULL,
    CONSTRAINT pk_job PRIMARY KEY (id),
    FULLTEXT(job_description)
);

CREATE TABLE report
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    title              VARCHAR(255)          NULL,
    content            VARCHAR(255)          NULL,
    report_type        VARCHAR(255)          NULL,
    employee_id        BIGINT                NULL,
    CONSTRAINT pk_report PRIMARY KEY (id)
);

CREATE TABLE request
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    employee_id        BIGINT                NULL,
    request_type       VARCHAR(255)          NULL,
    requested_date     date                  NULL,
    start_time         time                  NULL,
    end_time           time                  NULL,
    note               VARCHAR(255)          NULL,
    comment            VARCHAR(255)          NULL,
    status             VARCHAR(100)          NULL DEFAULT 'PENDING',
    approved           BIT(1)                NULL default b'0',
    deleted            BIT(1)                NULL default b'0',
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- Index for efficient querying of history for a specific contract
CREATE TABLE salary
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    month              INT                   NOT NULL,
    year               INT                   NOT NULL,
    base_salary        DECIMAL(10,2)         NULL,
    bonus              DECIMAL(10,2)         NULL,
    deduction          DECIMAL(10,2)         NULL,
    net_salary         DECIMAL(10,2)         NULL,
    employee_id        BIGINT                NULL,
    CONSTRAINT pk_salary PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50)           NOT NULL DEFAULT 'system',
    created_date       datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   VARCHAR(50)           NULL DEFAULT 'system',
    last_modified_date datetime              NULL DEFAULT CURRENT_TIMESTAMP,
    full_name          VARCHAR(255)          NULL,
    email              VARCHAR(255)          NOT NULL,
    password           VARCHAR(255)          NOT NULL,
    active             BIT(1)                DEFAULT b'1',
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

ALTER TABLE department
    ADD CONSTRAINT uc_department_manager UNIQUE (manager_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_email UNIQUE (email);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_id_number UNIQUE (id_number);

ALTER TABLE candidate
    ADD CONSTRAINT uc_candidate_id_number UNIQUE (id_number);

ALTER TABLE job
    ADD CONSTRAINT uc_job_title UNIQUE (job_title);

ALTER TABLE attendance
    ADD CONSTRAINT FK_ATTENDANCE_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE contract
    ADD CONSTRAINT FK_CONTRACT_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE department
    ADD CONSTRAINT FK_DEPARTMENT_ON_MANAGER FOREIGN KEY (manager_id) REFERENCES employee (id);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE employee_review
    ADD CONSTRAINT FK_EMPLOYEE_REVIEW_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE employee_review
    ADD CONSTRAINT FK_EMPLOYEE_REVIEW_ON_REVIEWER FOREIGN KEY (reviewer_id) REFERENCES employee (id);

ALTER TABLE interview
    ADD CONSTRAINT FK_INTERVIEW_ON_EMPLOYEE FOREIGN KEY (interviewer_id) REFERENCES employee (id);

ALTER TABLE job
    ADD CONSTRAINT FK_JOB_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES department (id);

ALTER TABLE report
    ADD CONSTRAINT FK_REPORT_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE request
    ADD CONSTRAINT FK_REQUEST_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE salary
    ADD CONSTRAINT FK_SALARY_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE application
    ADD CONSTRAINT fk_app_on_candidate FOREIGN KEY (candidate_id) REFERENCES candidate (id);

ALTER TABLE application
    ADD CONSTRAINT fk_app_on_job FOREIGN KEY (job_id) REFERENCES job (id);

ALTER TABLE candidate_interview
    ADD CONSTRAINT fk_canint_on_candidate FOREIGN KEY (candidate_id) REFERENCES candidate (id);

ALTER TABLE candidate_interview
    ADD CONSTRAINT fk_canint_on_interview FOREIGN KEY (interview_id) REFERENCES interview (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);