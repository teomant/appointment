CREATE TABLE public.appointment (
    id SERIAL,
    comment VARCHAR NOT NULL,
    latitude REAL,
    longitude REAL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted boolean
)