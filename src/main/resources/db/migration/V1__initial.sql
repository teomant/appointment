CREATE TABLE public.option (
    id SERIAL,
    comment VARCHAR NOT NULL,
    latitude REAL,
    longitude REAL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted boolean
)