create TABLE public.notification (
    id SERIAL PRIMARY KEY,
    comment VARCHAR NOT NULL,
    user_id int REFERENCES public.users,
    appointment_id int REFERENCES public.appointment,
    delivered BOOLEAN
);