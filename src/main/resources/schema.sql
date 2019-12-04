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
    TABLESPACE pg_default;

ALTER TABLE public.usr
    OWNER to ivshebanov;


-- Table: public.photo
CREATE TABLE public.photo
(
    id     bigint                                              NOT NULL,
    usr_id bigint                                              NOT NULL,
    active boolean                                             NOT NULL,
    photo  character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT photo_pkey PRIMARY KEY (id),
    CONSTRAINT photo_usr_id_fkey FOREIGN KEY (usr_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE public.photo
    OWNER to ivshebanov;


-- Table: public.guys
CREATE TABLE public.guys
(
    id         bigint                                              NOT NULL,
    usr_id     bigint                                              NOT NULL,
    active     boolean                                             NOT NULL,
    guy_name   character varying(255) COLLATE pg_catalog."default" NOT NULL,
    count_like integer                                             NOT NULL,
    CONSTRAINT guys_pkey PRIMARY KEY (id),
    CONSTRAINT guys_usr_id_fkey FOREIGN KEY (usr_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE public.guys
    OWNER to ivshebanov;


-- Table: public.my_group
CREATE TABLE public.my_group
(
    id       bigint                                              NOT NULL,
    usr_id   bigint                                              NOT NULL,
    my_group character varying(255) COLLATE pg_catalog."default" NOT NULL,
    active   boolean                                             NOT NULL,
    CONSTRAINT my_group_pkey PRIMARY KEY (id),
    CONSTRAINT my_group_usr_id_fkey FOREIGN KEY (usr_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE public.my_group
    OWNER to ivshebanov;


-- Table: public.hashtag
CREATE TABLE public.hashtag
(
    id      bigint                                              NOT NULL,
    usr_id  bigint                                              NOT NULL,
    hashtag character varying(255) COLLATE pg_catalog."default" NOT NULL,
    active  boolean                                             NOT NULL,
    CONSTRAINT hashtag_pkey PRIMARY KEY (id),
    CONSTRAINT hashtag_usr_id_fkey FOREIGN KEY (usr_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE public.hashtag
    OWNER to ivshebanov;


-- Table: public.roles
CREATE TABLE public.roles
(
    usr_id bigint                                              NOT NULL,
    role   character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_usr_id_fkey FOREIGN KEY (usr_id)
        REFERENCES public.usr (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    TABLESPACE pg_default;

ALTER TABLE public.roles
    OWNER to ivshebanov;