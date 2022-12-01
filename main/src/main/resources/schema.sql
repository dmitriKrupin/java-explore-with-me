CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL UNIQUE,
    email VARCHAR(512)                            NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat BIGINT                                  NOT NULL,
    lon BIGINT                                  NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title  VARCHAR(255)                            NOT NULL,
    pinned BOOLEAN                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title              VARCHAR(255)                            NOT NULL,
    annotation         VARCHAR                                 NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    views              BIGINT,
    confirmed_requests BIGINT,
    description        VARCHAR                                 NOT NULL,
    state              BIGINT,
    created_on         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    location_id        BIGINT,
    request_moderation BOOLEAN,
    participant_limit  BIGINT                                  NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    created      TIMESTAMP                               NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS events_list
(
    compilation_id BIGINT NOT NULL UNIQUE,
    events_id      BIGINT NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS events_list_compilation_id_events_id_index
    ON events_list (compilation_id, events_id);