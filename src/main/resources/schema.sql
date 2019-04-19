CREATE TABLE public."user"
(
    id integer NOT NULL,
    email character(100) NOT NULL,
    login character(100) NOT NULL,
    password character(100) NOT NULL,
    inst_name character(100) NOT NULL,
    intst_password character(100) NOT NULL,
    PRIMARY KEY (id)
);
