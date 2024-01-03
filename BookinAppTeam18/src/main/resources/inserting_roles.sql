DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM public.roles LIMIT 1) THEN
            INSERT INTO public.roles (name) VALUES ('ADMIN');
            INSERT INTO public.roles (name) VALUES ('OWNER');
            INSERT INTO public.roles (name) VALUES ('GUEST');
        END IF;
    END;
    $$;