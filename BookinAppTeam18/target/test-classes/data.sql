INSERT INTO roles('id',
                  'name')
VALUES(1,
       'ADMIN')
INSERT INTO roles('id',
                    'name')
VALUES(2,
    'OWNER')

INSERT INTO roles('id',
                  'name')
VALUES(3,
    'GUEST')

INSERT INTO accounts(`account_id`,
                     `address`,
                     `email`,
                     `first_name`,
                     `last_name`,
                     `last_password_reset_date`,
                     "password",
                     "phone",
                     "user_state",
                     "user_role")
VALUES (1L ,
        'adresa' ,
        'guest@email.com',
        'ime',
        'prezime',
        null,
        '$2a$10$AgpJTNKIkzny46tKJRIbBOxc72ltCH/zYe.D4WCPdhGyZKx4KvCSi',
        '054332332',
        0,
        3);

INSERT INTO accounts(`account_id`,
                     `address`,
                     `email`,
                     `first_name`,
                     `last_name`,
                     `last_password_reset_date`,
                     "password",
                     "phone",
                     "user_state",
                     "user_role")
VALUES (2L ,
        'adresa' ,
        'owner@email.com',
        'owner',
        'prezime',
        null,
        '$2a$10$AgpJTNKIkzny46tKJRIbBOxc72ltCH/zYe.D4WCPdhGyZKx4KvCSi',
        '054332332',
        0,
        2);

INSERT INTO accounts(`account_id`,
                     `address`,
                     `email`,
                     `first_name`,
                     `last_name`,
                     `last_password_reset_date`,
                     "password",
                     "phone",
                     "user_state",
                     "user_role")
VALUES (3L ,
        'adresa' ,
        'admin@email.com',
        'admin',
        'prezime',
        null,
        '$2a$10$AgpJTNKIkzny46tKJRIbBOxc72ltCH/zYe.D4WCPdhGyZKx4KvCSi',
            '054332332',
        0,
        1);
