ALTER TABLE public.site_users
        ADD COLUMN username VARCHAR NOT NULL DEFAULT '';

UPDATE public.site_users su
        SET username =
        (SELECT u.username FROM public.users u
        WHERE u.id = su.id);

ALTER TABLE public.site_users
        ADD CONSTRAINT unique_site_user_username UNIQUE (username);

ALTER TABLE public.users
        DROP COLUMN username;

create TABLE public.telegram_users (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    login VARCHAR NOT NULL,
    chat_id int NOT NULL
);