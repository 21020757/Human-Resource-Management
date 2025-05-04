INSERT INTO role (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_ADMIN');

INSERT INTO role (name)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_USER');

INSERT INTO role (name)
SELECT 'ROLE_MANAGER'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_MANAGER');

INSERT INTO department (department_code, department_name) VALUES
                                                   ('HR', 'Human Resources'),
                                                   ('IT', 'Information Technology'),
                                                   ('FIN', 'Finance'),
                                                   ('MKT', 'Marketing'),
                                                   ('SALES', 'Sales'),
                                                   ('OPS', 'Operations'),
                                                   ('LEGAL', 'Legal'),
                                                   ('RND', 'Research & Development'),
                                                   ('CS', 'Customer Support'),
                                                   ('PR', 'Public Relations');
INSERT INTO employee (full_name, email, phone, address, gender, date_of_birth, id_number, employee_code, position, hire_date, contract_type, active, department_id) VALUES
                    ('Nguyen Dang Duong', 'soi123', '0859039596', '123 ngo gia tu bac ninh', 'Nam', '2003-05-07', '123', 'IT1', 'BE dev', '2024-05-03', 'FULL_TIME', true, '2'),
                    ('Nguyen Viet Anh', 'va123', '0859039591', '123 ngo gia tu bac ninh', 'Nam', '2003-05-06', '1234', 'IT2', 'BE dev', '2024-05-03', 'FULL_TIME', true, '1'),
                    ('Duy Anh', 'admin3', '0859000003', 'Hà Nội', 'Nam', '1995-08-15', '1003', 'IT3', 'Frontend Dev', '2024-05-03', 'FULL_TIME', true, 1),
                    ('Ngoc Linh', 'user1', '0859000004', 'TP HCM', 'Nữ', '1996-03-21', '1004', 'HR1', 'HR Specialist', '2024-05-03', 'FULL_TIME', true, 2),
                    ('Thanh An', 'user2', '0859000005', 'Đà Nẵng', 'Nam', '1994-11-09', '1005', 'HR2', 'Recruiter', '2024-05-03', 'FULL_TIME', true, 2),
                    ('employee test 1', 'admin1', '0859000006', 'Bắc Ninh', 'Nam', '2000-05-10', '1006', 'IT4', 'BE Dev', '2024-05-03', 'FULL_TIME', true, 2),
                    ('employee test 2', 'admin2', '0859000007', 'Bắc Ninh', 'Nam', '2000-05-11', '1007', 'IT5', 'BE Dev', '2024-05-03', 'FULL_TIME', true, 1);
INSERT INTO contract(salary, employee_id) VALUES (12000000, 1), (20000000, 2);

INSERT INTO user (id, full_name, active, email, password)
VALUES
(1, 'Dang Duong', true, 'admin1', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(2, 'Viet Anh', true, 'admin2', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(3, 'Duy Anh', true, 'admin3', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(4, 'Ngoc Linh', true,'user1', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(5, 'Thanh An', true, 'user2', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(6, 'employee test 1', true, 'soi123', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(7, 'employee test 2', true, 'va123', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK');



INSERT INTO user_roles (user_id, roles_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 2),
(7, 2)
