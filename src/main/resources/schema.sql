
SET TIMEZONE="Asia/Dhaka";
CREATE EXTENSION IF NOT EXISTS pg_trgm;
SELECT * FROM pg_extension;

CREATE TABLE IF NOT EXISTS permissions(
    id  SERIAL PRIMARY KEY,
    name varchar(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW (),
    updated_at TIMESTAMP,
    deleted_at Timestamp
);

CREATE TABLE IF NOT EXISTS roles(
    id  SERIAL PRIMARY KEY,
    name varchar(20) NOT NULL,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW (),
     updated_at TIMESTAMP,
     deleted_at Timestamp
);

create TABLE IF NOT EXISTS  role_permissions(
    id SERIAL PRIMARY KEY NOT NULL,
    role_id SERIAL NOT NULL,
     permission_id SERIAL NOT NULL,
     FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
     FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), username varchar(20) UNIQUE,
    password text,
    email VARCHAR(255) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW (),
    updated_at TIMESTAMP,
    deleted_at Timestamp
);

create TABLE IF NOT EXISTS  user_roles(
    id SERIAL PRIMARY KEY NOT NULL,
    role_id SERIAL NOT NULL,
    user_id UUID NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
     FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'unique_user_role'
    ) THEN
        ALTER TABLE user_roles
        ADD CONSTRAINT unique_user_role UNIQUE (user_id, role_id);
    END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'unique_role_permissions'
    ) THEN
        ALTER TABLE role_permissions
        ADD CONSTRAINT unique_role_permissions UNIQUE (role_id, permission_id);
    END IF;
END $$;



