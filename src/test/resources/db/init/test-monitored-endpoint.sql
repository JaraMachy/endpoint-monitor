--------------------------------
--      MONITORED ENDPOINT    --
--------------------------------

INSERT INTO monitored_endpoint (id, name, url, date_of_creation, date_of_last_check, monitored_interval, owner_id)
VALUES (1000,
        'Tipsport',
        'https://www.tipsport.cz/',
        CURRENT_TIMESTAMP,
        null,
        20,
        2000);
INSERT INTO monitored_endpoint (id, name, url, date_of_creation, date_of_last_check, monitored_interval, owner_id)
VALUES (1001,
        'Seznam',
        'https://www.seznam.cz/',
        CURRENT_TIMESTAMP,
        null,
        30,
        2001);
INSERT INTO monitored_endpoint (id, name, url, date_of_creation, date_of_last_check, monitored_interval, owner_id)
VALUES (1002,
        'Refactoring guru',
        'https://refactoring.guru/refactoring',
        CURRENT_TIMESTAMP,
        null,
        31,
        2000);
INSERT INTO monitored_endpoint (id, name, url, date_of_creation, date_of_last_check, monitored_interval, owner_id)
VALUES (1003,
        'Youtube',
        'https://www.youtube.com/',
        CURRENT_TIMESTAMP,
        null,
        32,
        2000);
INSERT INTO monitored_endpoint (id, name, url, date_of_creation, date_of_last_check, monitored_interval, owner_id)
VALUES (1004,
        'Novinky',
        'https://www.novinky.cz/',
        CURRENT_TIMESTAMP,
        null,
        33,
        2001);