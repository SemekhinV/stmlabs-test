DROP TABLE IF EXISTS Tickets CASCADE;
DROP TABLE IF EXISTS Routes CASCADE;
DROP TABLE IF EXISTS Carriers CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    login       VARCHAR(255)                            NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    password    VARCHAR(255)                            NOT NULL,
    CONSTRAINT  pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_login UNIQUE (login)
);

CREATE TABLE carriers (
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255)                            NOT NULL,
    phone_number VARCHAR(255)                           NOT NULL,
    CONSTRAINT pk_carriers PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS routes
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    departure_point VARCHAR(255)                        NOT NULL,
    destination_point VARCHAR(255)                      NOT NULL,
    carrier_id  BIGINT REFERENCES carriers (id) ON DELETE CASCADE,
    duration    BIGINT                                  NOT NULL,
    CONSTRAINT pk_routes PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tickets
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    owner_id    BIGINT REFERENCES users(id)   ON DELETE CASCADE,
    route_id    BIGINT REFERENCES routes (id) ON DELETE CASCADE,
    date_time   TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    seat_number BIGINT                                  NOT NULL,
    price       BIGINT                                  NOT NULL,
    status      BOOLEAN                                 NOT NULL,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);