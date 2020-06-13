UPDATE public.privileges
	SET action='MODIFY_OWN'
	WHERE action='MIDIFY_OWN';

UPDATE public.privileges
	SET action='MODIFY_ANY'
	WHERE action='MIDIFY_ANY';

INSERT INTO public.roles_privileges (role_id, privilege_id)
        SELECT r.id, p.id FROM public.roles r CROSS JOIN public.privileges p
            WHERE r.name = 'ROLE_USER' AND p.action = 'MODIFY_OWN';

INSERT INTO public.roles_privileges (role_id, privilege_id)
        SELECT r.id, p.id FROM public.roles r CROSS JOIN public.privileges p
            WHERE r.name = 'ROLE_ADMIN' AND p.action = 'MODIFY_ANY';
