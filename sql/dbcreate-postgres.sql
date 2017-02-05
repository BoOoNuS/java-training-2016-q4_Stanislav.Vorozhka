-- Removing tables --
DROP TABLE station_on_route;
DROP TABLE stations;
DROP TABLE station_types;
DROP TABLE train_places;
DROP TABLE place_types;
DROP TABLE trains;
DROP TABLE routes;
DROP TABLE users;
DROP TABLE roles;

-- Creation tables --
CREATE TABLE stations
(
  id   SERIAL                NOT NULL,
  name CHARACTER VARYING(16) NOT NULL,
  CONSTRAINT station_id PRIMARY KEY (id),
  CONSTRAINT station_name_unique UNIQUE (name)
);

CREATE TABLE station_types
(
  id   SERIAL                NOT NULL,
  type CHARACTER VARYING(16) NOT NULL,
  CONSTRAINT stt_type_id PRIMARY KEY (id)
);

CREATE TABLE routes
(
  id          SERIAL NOT NULL,
  travel_time BIGINT NOT NULL,
  CONSTRAINT route_id PRIMARY KEY (id)
);

CREATE TABLE place_types
(
  id   SERIAL                NOT NULL,
  type CHARACTER VARYING(16) NOT NULL,
  CONSTRAINT plc_type_id PRIMARY KEY (id)
);

CREATE TABLE station_on_route
(
  station_id INTEGER               NOT NULL,
  route_id   INTEGER               NOT NULL,
  type_id    INTEGER               NOT NULL,
  date       BIGINT                NOT NULL,
  time       CHARACTER VARYING(62) NOT NULL,
  CONSTRAINT station_id FOREIGN KEY (station_id)
  REFERENCES stations (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE,

  CONSTRAINT route_id FOREIGN KEY (route_id)
  REFERENCES routes (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE,

  CONSTRAINT plc_type_id FOREIGN KEY (type_id)
  REFERENCES station_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE trains
(
  number   SERIAL  NOT NULL,
  route_id INTEGER,
  CONSTRAINT train_num PRIMARY KEY (number),

  CONSTRAINT route_id FOREIGN KEY (route_id)
  REFERENCES routes (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE train_places
(
  train_num   INTEGER NOT NULL,
  type_id     INTEGER NOT NULL,
  free_places INTEGER NOT NULL,
  cost        INTEGER NOT NULL,
  CONSTRAINT train_num FOREIGN KEY (train_num)
  REFERENCES trains (number) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE,

  CONSTRAINT type_id FOREIGN KEY (type_id)
  REFERENCES place_types (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

-- User tables --
CREATE TABLE roles
(
  id   SERIAL                NOT NULL,
  name CHARACTER VARYING(16) NOT NULL,
  CONSTRAINT roles_id PRIMARY KEY (id),
  CONSTRAINT roles_name_unique UNIQUE (name)
);

CREATE TABLE users
(
  login     CHARACTER VARYING(16) NOT NULL,
  password  CHARACTER VARYING(32) NOT NULL,
  full_name CHARACTER VARYING(36) NOT NULL,
  role_id   INTEGER               NOT NULL DEFAULT 2,
  CONSTRAINT user_login PRIMARY KEY (login),

  CONSTRAINT role_id FOREIGN KEY (role_id)
  REFERENCES roles (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

-- Creation date --
-- place_types filling --
INSERT INTO place_types (type) VALUES ('coupe');
INSERT INTO place_types (type) VALUES ('reserved seat');
INSERT INTO place_types (type) VALUES ('the total');

-- station_types filling --
INSERT INTO station_types (type) VALUES ('initial');
INSERT INTO station_types (type) VALUES ('terminal');
INSERT INTO station_types (type) VALUES ('way');

-- routes filling --
INSERT INTO routes (travel_time) VALUES (12345678);
INSERT INTO routes (travel_time) VALUES (21345678);
INSERT INTO routes (travel_time) VALUES (23145678);

-- trains filling --
INSERT INTO trains (route_id) VALUES (3);
INSERT INTO trains (route_id) VALUES (1);
INSERT INTO trains (route_id) VALUES (2);

-- free trains --
INSERT INTO trains (number) VALUES (4);
INSERT INTO trains (number) VALUES (5);
INSERT INTO trains (number) VALUES (6);

-- train_places filling --
INSERT INTO train_places VALUES (1, 1, 19, 3200);
INSERT INTO train_places VALUES (1, 2, 7, 2100);
INSERT INTO train_places VALUES (1, 3, 43, 1000);

INSERT INTO train_places VALUES (2, 1, 22, 3600);
INSERT INTO train_places VALUES (2, 2, 0, 2200);
INSERT INTO train_places VALUES (2, 3, 17, 800);

INSERT INTO train_places VALUES (3, 2, 27, 1700);
INSERT INTO train_places VALUES (3, 3, 35, 500);

-- train_places for free trains --
INSERT INTO train_places VALUES (4, 1, 50, 2600);
INSERT INTO train_places VALUES (4, 2, 50, 1100);
INSERT INTO train_places VALUES (4, 3, 50, 600);

INSERT INTO train_places VALUES (6, 1, 50, 3000);
INSERT INTO train_places VALUES (6, 2, 50, 2000);
INSERT INTO train_places VALUES (6, 3, 50, 700);

INSERT INTO train_places VALUES (5, 3, 50, 150);


-- stations filling --
INSERT INTO stations (name) VALUES ('Levada');
INSERT INTO stations (name) VALUES ('Osnova');
INSERT INTO stations (name) VALUES ('Zanky');
INSERT INTO stations (name) VALUES ('Slobozhnska');
INSERT INTO stations (name) VALUES ('Balakleya');
INSERT INTO stations (name) VALUES ('Savenci');
INSERT INTO stations (name) VALUES ('Izyum');

-- station_on_route filling --
INSERT INTO station_on_route VALUES (1, 2, 1, 2222222222, '2222222222');
INSERT INTO station_on_route VALUES (2, 2, 3, 2222222222, '33333/33333/33333');
INSERT INTO station_on_route VALUES (3, 2, 3, 2222222222, '44444/44444/44444');
INSERT INTO station_on_route VALUES (4, 2, 3, 2222222222, '55555/55555/55555');
INSERT INTO station_on_route VALUES (5, 2, 2, 2222222222, '2222222226');

INSERT INTO station_on_route VALUES (5, 1, 1, 3333333333, '3333333332');
INSERT INTO station_on_route VALUES (4, 1, 3, 3333333333, '33333/33333/33333');
INSERT INTO station_on_route VALUES (3, 1, 3, 3333333333, '44444/44444/44444');
INSERT INTO station_on_route VALUES (2, 1, 3, 3333333333, '55555/55555/55555');
INSERT INTO station_on_route VALUES (1, 1, 2, 3333333333, '3333333336');

INSERT INTO station_on_route VALUES (1, 3, 1, 4444444444, '4444444441');
INSERT INTO station_on_route VALUES (3, 3, 3, 4444444444, '33332/33333/33333');
INSERT INTO station_on_route VALUES (4, 3, 3, 4444444444, '44443/44444/44444');
INSERT INTO station_on_route VALUES (5, 3, 3, 4444444444, '44444/44444/44444');
INSERT INTO station_on_route VALUES (6, 3, 3, 5555555555, '55555/55555/55555');
INSERT INTO station_on_route VALUES (7, 3, 2, 5555555555, '5555555566');

-- roles filling --
INSERT INTO roles (name) VALUES ('admin');
INSERT INTO roles (name) VALUES ('client');

-- users filling --
INSERT INTO users VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 'Stanislav Vorozhka', 1);
INSERT INTO users VALUES ('админ', 'e61dfbc3c9b44a7e7bcae19b2f35375d', 'Станислав Ворожка', 1);
INSERT INTO users (login, password, full_name) VALUES ('client', '62608e08adc29a8d6dbc9754e659f125', 'Ivan Ivanovich');
