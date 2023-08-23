INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_MODERATOR');
INSERT INTO role(name) VALUES('ROLE_ADMIN');


INSERT INTO user_details(full_name, username, password, display_name, activation) VALUES('Admin', 'ADMIN', crypt('redberyl', gen_salt('bf')), 'Admin', 'True');