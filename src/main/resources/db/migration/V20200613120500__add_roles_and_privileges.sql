create TABLE public.roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

create TABLE public.users_roles (
    id SERIAL PRIMARY KEY,
    user_id int REFERENCES public.users,
    role_id int REFERENCES public.roles
);

create TABLE public.privileges (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    entity VARCHAR NOT NULL,
    privilege VARCHAR NOT NULL
);

create TABLE public.roles_privileges (
    id SERIAL PRIMARY KEY,
    role_id int REFERENCES public.roles,
    privilege_id int REFERENCES public.privileges
);

INSERT INTO public.roles (name)
    VALUES
        ('ROLE_USER'),
        ('ROLE_ADMIN');

INSERT INTO public.privileges (name, entity, privilege)
    VALUES
        ('CREATE_APPOINTMENT', 'APPOINTMENT', 'CREATE'),
        ('READ_ANY_APPOINTMENT', 'APPOINTMENT', 'READ_ANY'),
        ('MODIFY_OWN_APPOINTMENT', 'APPOINTMENT', 'MIDIFY_OWN'),
        ('MODIFY_ANY_APPOINTMENT', 'APPOINTMENT', 'MIDIFY_ANY'),
        ('CREATE_VOTE', 'VOTE', 'CREATE'),
        ('DELETE_OWN_VOTE', 'VOTE', 'DELETE_OWN'),
        ('DELETE_ANY_VOTE', 'VOTE', 'DELETE_ANY'),
        ('MODIFY_OWN_NOTIFICATION', 'NOTIFICATION', 'MIDIFY_OWN'),
        ('MODIFY_ANY_NOTIFICATION', 'NOTIFICATION', 'MIDIFY_ANY');

INSERT INTO public.roles_privileges (role_id, privilege_id)
        SELECT r.id, p.id FROM public.roles r CROSS JOIN public.privileges p
            WHERE r.name = 'ROLE_USER' AND (p.privilege = 'CREATE' OR p.privilege = 'READ_ANY' OR p.privilege = 'MODIFY_OWN' OR p.privilege = 'DELETE_OWN');

INSERT INTO public.roles_privileges (role_id, privilege_id)
        SELECT r.id, p.id FROM public.roles r CROSS JOIN public.privileges p
            WHERE r.name = 'ROLE_ADMIN' AND (p.privilege = 'CREATE' OR p.privilege = 'READ_ANY' OR p.privilege = 'MODIFY_ANY' OR p.privilege = 'DELETE_ANY');

INSERT INTO public.users_roles (user_id, role_id)
        SELECT u.id, r.id FROM public.users u CROSS JOIN public.roles r
            WHERE r.name = 'ROLE_USER';
