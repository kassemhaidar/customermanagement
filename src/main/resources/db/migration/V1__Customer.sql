CREATE TABLE IF NOT EXISTS public.customer
(
    id bigint PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    mobilenumber VARCHAR(20)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customer
    OWNER to postgres;