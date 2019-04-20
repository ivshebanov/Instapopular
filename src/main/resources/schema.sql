-- Table: public."user"

--DROP TABLE public."user";

CREATE TABLE public."user"
(
    id            integer                                     NOT NULL,
    email         character(255) COLLATE pg_catalog."default" NOT NULL,
    login         character(255) COLLATE pg_catalog."default" NOT NULL,
    password      character(255) COLLATE pg_catalog."default" NOT NULL,
    inst_name     character(255) COLLATE pg_catalog."default" NOT NULL,
    inst_password character(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to iliashebanov;


-- Table: public.my_photo

-- DROP TABLE public.my_photo;

CREATE TABLE public.my_photo
(
    id      integer                                     NOT NULL,
    id_user integer                                     NOT NULL,
    photo   character(255) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                     NOT NULL,
    CONSTRAINT my_photo_pkey PRIMARY KEY (id),
    CONSTRAINT my_photo_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_photo
    OWNER to iliashebanov;


-- Table: public.my_like

-- DROP TABLE public.my_like;

CREATE TABLE public.my_like
(
    id integer NOT NULL,
    id_user integer NOT NULL,
    "user" character(255) COLLATE pg_catalog."default" NOT NULL,
    count integer NOT NULL,
    CONSTRAINT my_like_pkey PRIMARY KEY (id),
    CONSTRAINT my_like_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_like
    OWNER to iliashebanov;


-- Table: public.my_group

-- DROP TABLE public.my_group;

CREATE TABLE public.my_group
(
    id      integer                                     NOT NULL,
    id_user integer                                     NOT NULL,
    "group" character(255) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                     NOT NULL,
    CONSTRAINT my_group_pkey PRIMARY KEY (id),
    CONSTRAINT my_group_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_group
    OWNER to iliashebanov;


-- Table: public.my_hashtag

-- DROP TABLE public.my_hashtag;

CREATE TABLE public.my_hashtag
(
    id integer NOT NULL,
    id_user integer NOT NULL,
    hashtag character(255) COLLATE pg_catalog."default" NOT NULL,
    status integer NOT NULL,
    CONSTRAINT my_hashtag_pkey PRIMARY KEY (id),
    CONSTRAINT my_hashtag_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_hashtag
    OWNER to iliashebanov;