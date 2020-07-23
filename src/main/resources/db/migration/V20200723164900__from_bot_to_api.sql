alter table telegram_users rename to client_users;

alter table public.client_users rename COLUMN chat_id to client_user_id;
alter table public.client_users drop COLUMN login;

create TABLE public.clients (
    id SERIAL PRIMARY KEY REFERENCES users(id),
    password VARCHAR NOT NULL
);

alter table public.client_users add COLUMN client_id int REFERENCES public.clients;

alter table public.users
        add COLUMN username VARCHAR NOT NULL DEFAULT '';

update public.users u
        set username =
        (select su.username from public.site_users su
        where u.id = su.id)
        where id in (select id from public.site_users);

update public.users u
        set username =
        (select cu.name from public.client_users cu
        where u.id = cu.id)
        where id in (select id from public.client_users);

alter table public.site_users
        drop COLUMN username;

alter table public.client_users
        drop COLUMN name;

insert into public.roles (name)
    VALUES
        ('ROLE_CLIENT');

insert into public.roles_privileges (role_id, privilege_id)
        select r.id, p.id from public.roles r CROSS JOIN public.privileges p
            where r.name = 'ROLE_CLIENT' and (p.action = 'CREATE' or p.action = 'READ_ANY' or p.action = 'MODIFY_OWN' or p.action = 'DELETE_OWN');
