
INSERT INTO roles(role_name) VALUES ( 'ROLE_ADMIN' ), ('ROLE_USER');

INSERT INTO users(login, password) VALUES ( 'admin', '$2a$12$u2yg91rk1uw9LtOYXqmaEeIv2mXmraWITBiGaYu2St6L4jDuKr6Qu');

INSERT INTO user_role(user_id, role_id) values ( 1, 1 );