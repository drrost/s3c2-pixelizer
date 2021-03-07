DROP DATABASE IF EXISTS pixelizator;
CREATE DATABASE pixelizator;

USE pixelizator;

DROP TABLE IF EXISTS file;
CREATE TABLE file
(
    file_id    BINARY(16) PRIMARY KEY,
    name       TEXT,
    size       INTEGER,
    created_at BIGINT
);

DROP TABLE IF EXISTS file_pixelate;
CREATE TABLE file_pixelate
(
    id INTEGER PRIMARY KEY,
    original_id BINARY(16) NOT NULL,
    pixelate_id BINARY(16) NOT NULL,
    FOREIGN KEY (original_id) REFERENCES file(file_id),
    FOREIGN KEY (pixelate_id) REFERENCES file(file_id)
);