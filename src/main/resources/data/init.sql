DROP DATABASE IF EXISTS songs;
CREATE DATABASE songs;

USE songs;

CREATE TABLE artist
(
    artist_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name      TEXT
);

CREATE TABLE song
(
    song_id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    title     TEXT,
    year      INTEGER,
    album     TEXT,
    artist_id INTEGER,
    FOREIGN KEY (artist_id) REFERENCES artist (artist_id)
);