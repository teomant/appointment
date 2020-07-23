ALTER TABLE public.users
        ADD CONSTRAINT unique_user_username UNIQUE (username);