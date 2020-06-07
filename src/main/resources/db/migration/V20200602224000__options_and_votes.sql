alter table public.appointment add PRIMARY KEY (id);

create TABLE public.options (
    id SERIAL PRIMARY KEY,
    comment VARCHAR NOT NULL,
    datetime TIMESTAMP WITH TIME ZONE,
    appointment_id int REFERENCES public.appointment
);

create TABLE public.user (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    option_id int REFERENCES public.options
);