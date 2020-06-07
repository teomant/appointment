CREATE TABLE public.user (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

ALTER TABLE public.vote ADD COLUMN user_id int REFERENCES public.user;
ALTER TABLE public.appointment ADD COLUMN user_id int REFERENCES public.user;