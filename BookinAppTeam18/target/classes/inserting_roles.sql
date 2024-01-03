DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM roles LIMIT 1) THEN
            INSERT INTO roles (name) VALUES ('ADMIN');
            INSERT INTO roles (name) VALUES ('OWNER');
            INSERT INTO roles (name) VALUES ('GUEST');
        END IF;
    END $$;