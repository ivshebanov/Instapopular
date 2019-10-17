-- Table: public.usr
CREATE TABLE public.usr
(
    id                 bigint                                              NOT NULL,
    active             boolean                                             NOT NULL,
    email              character varying(255) COLLATE pg_catalog."default" NOT NULL,
    usrname            character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password           character varying(255) COLLATE pg_catalog."default" NOT NULL,
    inst_name          character varying(255) COLLATE pg_catalog."default",
    inst_password      character varying(255) COLLATE pg_catalog."default",
    do_not_unsubscribe integer,
    CONSTRAINT usr_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.usr
    OWNER to ivshebanov;


-- Table: public.my_photo
CREATE TABLE public.my_photo
(
    id     bigint                                              NOT NULL,
    id_usr bigint                                              NOT NULL,
    photo  character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status integer                                             NOT NULL,
    CONSTRAINT my_photo_pkey PRIMARY KEY (id),
    CONSTRAINT my_photo_id_usr_fkey FOREIGN KEY (id_usr)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_photo
    OWNER to ivshebanov;


-- Table: public.my_like
CREATE TABLE public.my_like
(
    id     bigint                                              NOT NULL,
    id_usr bigint                                              NOT NULL,
    usr    character varying(255) COLLATE pg_catalog."default" NOT NULL,
    count  integer                                             NOT NULL,
    CONSTRAINT my_like_pkey PRIMARY KEY (id),
    CONSTRAINT my_like_id_usr_fkey FOREIGN KEY (id_usr)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_like
    OWNER to ivshebanov;


-- Table: public.my_group
CREATE TABLE public.my_group
(
    id      bigint                                              NOT NULL,
    id_usr  bigint                                              NOT NULL,
    "group" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                             NOT NULL,
    CONSTRAINT my_group_pkey PRIMARY KEY (id),
    CONSTRAINT my_group_id_usr_fkey FOREIGN KEY (id_usr)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_group
    OWNER to ivshebanov;


-- Table: public.my_hashtag
CREATE TABLE public.my_hashtag
(
    id      bigint                                              NOT NULL,
    id_usr  bigint                                              NOT NULL,
    hashtag character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                             NOT NULL,
    CONSTRAINT my_hashtag_pkey PRIMARY KEY (id),
    CONSTRAINT my_hashtag_id_usr_fkey FOREIGN KEY (id_usr)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.my_hashtag
    OWNER to ivshebanov;


-- Table: public.usr_role
CREATE TABLE public.usr_role
(
    id_usr bigint                                              NOT NULL,
    roles  character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT usr_role_id_usr_fkey FOREIGN KEY (id_usr)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.usr_role
    OWNER to ivshebanov;