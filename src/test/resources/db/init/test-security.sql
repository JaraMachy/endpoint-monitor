--------------------------------
--          SECURITY          --
--------------------------------

INSERT INTO sec_user (id, username, password, email) /* heslo 12345 */
VALUES (2000,
        'Tyrion Lannister',
        '$2a$10$cDJi9uA3U6A6/OEQwBBXxetGXE0fK3LjocsAmdACbMd/3VvZT.cBy',
        'tyrion@got.com');

INSERT INTO sec_user (id, username, password, email)  /* heslo 123456 */
VALUES (2001,
        'Aria Stark',
        '$2a$10$py3js0sKXkAB4iwIu2NbKexRO8Cd79iJSoH/BI4RgRlacwlQKRTmW',
        'aria@got.com');