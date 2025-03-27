INSERT INTO role (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_ADMIN');

INSERT INTO role (name)
SELECT 'ROLE_USER'
    WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_USER');


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

INSERT INTO user (id, full_name, email, password)
VALUES
(1, 'Dang Duong', 'admin1', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(2, 'Viet Anh', 'admin2', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(3, 'Duy Anh', 'admin3', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(4, 'Ngoc Linh', 'user1', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK'),
(5, 'Thanh An', 'user2', '$2a$12$.4154E2FbWPMqoElrptIR.sjID1w/gyIs3mHa3sTB8vRcom3qFvVK');



INSERT INTO user_roles (user_id, roles_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2);
