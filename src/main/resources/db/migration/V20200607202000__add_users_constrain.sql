ALTER TABLE "user" RENAME TO users;

ALTER TABLE users ADD CONSTRAINT unique_username UNIQUE (username);