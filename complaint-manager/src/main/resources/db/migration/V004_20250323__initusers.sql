CREATE TABLE users (
	id int CONSTRAINT users_id PRIMARY KEY,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	tech varchar(255) NOT NULL
);