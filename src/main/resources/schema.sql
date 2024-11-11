
CREATE TABLE IF NOT EXISTS permissions(
    id  SERIAL PRIMARY KEY,
    name varchar(20) NOT NULL,
    description varchar(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at Timestamp
);

CREATE TABLE IF NOT EXISTS roles(
    id  SERIAL PRIMARY KEY,
    name varchar(20) NOT NULL,
    description varchar(255) NOT NULL,
     created_at TIMESTAMP,
     updated_at TIMESTAMP,
     deleted_at Timestamp
);

create TABLE IF NOT EXISTS  role_permissions(
    role_id SERIAL NOT NULL,
     permission_id SERIAL NOT NULL,
     PRIMARY KEY (role_id, permission_id),
     FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
     FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username varchar(20) UNIQUE,
    password text,
    email VARCHAR(255) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at Timestamp
);

create TABLE IF NOT EXISTS  user_roles(
    role_id SERIAL NOT NULL,
    user_id UUID NOT NULL,
     PRIMARY KEY (user_id, role_id),
     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
     FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
)




