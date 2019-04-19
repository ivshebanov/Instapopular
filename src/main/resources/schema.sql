-- Table: public."user"

DROP TABLE public."user";

CREATE TABLE public."user"
(
    id            integer                                     NOT NULL,
    email         character(100) COLLATE pg_catalog."default" NOT NULL,
    login         character(100) COLLATE pg_catalog."default" NOT NULL,
    password      character(100) COLLATE pg_catalog."default" NOT NULL,
    inst_name     character(100) COLLATE pg_catalog."default" NOT NULL,
    inst_password character(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to iliashebanov;


-- Table: public.myphoto

DROP TABLE public.myphoto;

CREATE TABLE public.myphoto
(
    id_user integer                                   NOT NULL,
    photo   character(1) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                   NOT NULL,
    CONSTRAINT myphoto_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.myphoto
    OWNER to iliashebanov;


-- Table: public.mylike

DROP TABLE public.mylike;

CREATE TABLE public.mylike
(
    id_user integer                                   NOT NULL,
    "user"  character(1) COLLATE pg_catalog."default" NOT NULL,
    count   integer                                   NOT NULL,
    CONSTRAINT mylike_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.mylike
    OWNER to iliashebanov;


-- Table: public.mygroup

DROP TABLE public.mygroup;

CREATE TABLE public.mygroup
(
    id_user integer                                   NOT NULL,
    "group" character(1) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                   NOT NULL,
    CONSTRAINT mygroup_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.mygroup
    OWNER to iliashebanov;


-- Table: public.hashtag

DROP TABLE public.hashtag;

CREATE TABLE public.hashtag
(
    id_user integer                                   NOT NULL,
    hashteg character(1) COLLATE pg_catalog."default" NOT NULL,
    status  integer                                   NOT NULL,
    CONSTRAINT hashtag_id_user_fkey FOREIGN KEY (id_user)
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.hashtag
    OWNER to iliashebanov;