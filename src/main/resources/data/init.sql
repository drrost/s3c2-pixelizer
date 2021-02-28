DROP DATABASE IF EXISTS songs;

DROP DATABASE IF EXISTS pixelizator;
CREATE DATABASE pixelizator;

USE pixelizator;

CREATE TABLE file
(
    file_id BINARY(16) PRIMARY KEY,
    name    TEXT,
    size    INTEGER
);