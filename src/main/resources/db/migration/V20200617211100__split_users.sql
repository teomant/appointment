create TABLE public.site_users (
    id SERIAL PRIMARY KEY,
    password VARCHAR NOT NULL
);

INSERT INTO public.site_users (id, password)
        SELECT id, password FROM public.users;

ALTER TABLE public.users
        DROP COLUMN password;
