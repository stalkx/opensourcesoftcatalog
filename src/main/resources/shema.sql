CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       login VARCHAR(128) NOT NULL,
                       password VARCHAR NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       role_id SERIAL PRIMARY KEY,
                       role_name VARCHAR NOT NULL
);

CREATE TABLE category (
                          category_id SERIAL PRIMARY KEY,
                          category_name VARCHAR NOT NULL
);

CREATE TABLE program (
                         program_id SERIAL PRIMARY KEY,
                         program_name VARCHAR NOT NULL,
                         program_description TEXT,
                         program_icon_url VARCHAR,
                         program_version VARCHAR,
                         program_system_support VARCHAR,
                         program_developer VARCHAR,
                         program_download_url VARCHAR,
                         program_github_url VARCHAR,
                         category_id INT REFERENCES category(category_id) ON DELETE SET NULL,
                         added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comment (
                         comment_id SERIAL PRIMARY KEY,
                         comment_body VARCHAR,
                         rating INT,
                         program_id INT REFERENCES program(program_id) ON DELETE CASCADE,
                         user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                         added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_role (
                           user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
                           role_id INT REFERENCES roles(role_id) ON DELETE CASCADE,
                           PRIMARY KEY (user_id, role_id)
);