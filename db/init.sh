#!/bin/bash
set -e
PK='_pk'
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
  SELECT 'CREATE DATABASE $APP_DB_NAME' WHERE NOT exists (SELECT FROM pg_database WHERE datname = '$APP_DB_NAME');
  GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;
  \connect $APP_DB_NAME $APP_DB_USER
  BEGIN;
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

    CREATE TABLE IF NOT EXISTS $APP_DB_TABLE_FLIGHT (
      id varchar NOT NULL,
      data jsonb NULL,
      CONSTRAINT $APP_DB_TABLE_FLIGHT$PK PRIMARY KEY (id)
    );

    insert into $APP_DB_TABLE_FLIGHT values (uuid_generate_v4(), '{"id":"fc704c16fd79","company":"US Airlines","points":25000,"duration":590,"availability":250,"paxes":[{"infant":50},{"children":58},{"adult":120}],"segment":[{"duration":590,"departureTime":"2021-10-10T21:30-03:00","arrivalTime":"2021-10-11T06:20-04:00","origin":"GRU","destination":"JFK"}]}');
    insert into $APP_DB_TABLE_FLIGHT values (uuid_generate_v4(), '{"id":"3fe21e46fd78","company":"Dalta","points":20000,"duration":862,"availability":230,"paxes":[{"infant":96},{"children":111},{"adult":290}],"segment":[{"duration":635,"departureTime":"2021-10-16T20:25-03:00","arrivalTime":"2021-10-17T06:00-04:00","origin":"GRU","destination":"YYZ","connectionDuration":125},{"duration":102,"departureTime":"2021-10-17T08:05-04:00","arrivalTime":"2021-10-17T09:47-04:00","origin":"YYZ","destination":"JFK"}]}');
    insert into $APP_DB_TABLE_FLIGHT values (uuid_generate_v4(), '{"id":"8bf2b3d7be09","company":"Aviana","points":17000,"duration":1050,"availability":300,"paxes":[{"infant":99},{"children":120},{"adult":300}],"segment":[{"duration":515,"departureTime":"2021-10-10T21:25-03:00","arrivalTime":"2021-10-11T05:00-04:00","origin":"GRU","destination":"MIA","connectionDuration":145},{"duration":192,"departureTime":"2021-10-11T07:25-04:00","arrivalTime":"2021-10-11T10:37-04:00","origin":"MIA","destination":"YYZ","connectionDuration":98},{"duration":100,"departureTime":"2021-10-11T12:15-04:00","arrivalTime":"2021-10-11T13:55-04:00","origin":"YYZ","destination":"JFK"}]}');

    CREATE TABLE IF NOT EXISTS $APP_DB_TABLE_PASSENGER (
      id varchar NOT NULL,
      firstName varchar NOT NULL,
      lastName varchar NOT NULL,
      address varchar NOT NULL,
      postalCode varchar NOT NULL,
      country varchar NOT NULL,
      email varchar NOT NULL,
      telephone varchar NOT NULL,
      CONSTRAINT $APP_DB_TABLE_PASSENGER$PK PRIMARY KEY (id)
    );

    insert into $APP_DB_TABLE_PASSENGER values(uuid_generate_v4(), 'test1_first_name', 'test1_last_name', 'address1', '11111', 'Angola', 'email@example.com', '666666666');
    insert into $APP_DB_TABLE_PASSENGER values(uuid_generate_v4(), 'test2_first_name', 'test2_last_name', 'address2', '22222', 'Mongolia', 'email_test@example.com', '111111111');
    insert into $APP_DB_TABLE_PASSENGER values(uuid_generate_v4(), 'test3_first_name', 'test3_last_name', 'address3', '11111', 'Chile', 'emailer@example.com', '000000000');

    CREATE TABLE IF NOT EXISTS $APP_DB_TABLE_BOOKING (
      id varchar NOT NULL,
      data jsonb NULL,
      status int NOT NULL default '0',
      CONSTRAINT $APP_DB_TABLE_BOOKING$PK PRIMARY KEY (id)
    );

  COMMIT;
EOSQL