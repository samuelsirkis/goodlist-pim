
CREATE TABLE IF NOT EXISTS public.categories (

  id            uuid DEFAULT uuid_generate_v4 (),
  name          varchar(50)  NOT NULL,

    CONSTRAINT categories_pkey
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.delivery_address (

    id         uuid DEFAULT uuid_generate_v4 (),
    street     varchar(50)  NOT NULL,
    complement varchar(50),
    number     integer      NOT NULL,
    district   varchar(50)  NOT NULL,
    city       varchar(20)  NOT NULL,
    state      varchar(2)   NOT NULL, 
    zip_code   varchar(9)   NOT NULL,

    CONSTRAINT delivery_address_pkey
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.customers (

    id         uuid DEFAULT uuid_generate_v4 (),
    email      varchar(50)  NOT NULL UNIQUE,
    name       varchar(100) NOT NULL,
    phone      varchar(20)  NOT NULL,
    password   varchar(90)  NOT NULL,
    cpf        varchar(15),
    birthdate  timestamp, 
    status     varchar(20),

    street     varchar(50)  NOT NULL,
    complement varchar(50),
    number     integer      NOT NULL,
    district   varchar(50)  NOT NULL,
    city       varchar(20)  NOT NULL,
    state      varchar(2)   NOT NULL, 
    zip_code   varchar(9)   NOT NULL,

    delivery_address_id uuid,
    created_at timestamp    WITHOUT time zone NOT NULL DEFAULT now(),
    updated_at timestamp    WITHOUT time zone,

    CONSTRAINT provider_unique
        UNIQUE (id, email),
    CONSTRAINT customer_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (delivery_address_id) 
        REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS public.users (

    id          uuid DEFAULT uuid_generate_v4 (),
    name        varchar(50)   NOT NULL,
    email       varchar(50)   NOT NULL UNIQUE,
    password    varchar(90)  NOT NULL,
    role        varchar(50),

    created_at  timestamp WITHOUT time zone 
        DEFAULT now() NOT NULL,
    updated_at  timestamp WITHOUT time zone,

    CONSTRAINT  users_pkey
        PRIMARY KEY (id),
     CONSTRAINT users_unique
        UNIQUE (id, email)
);

CREATE TABLE IF NOT EXISTS public.providers (

    id          uuid            DEFAULT uuid_generate_v4 (),
    email       varchar(50)     NOT NULL UNIQUE,
    name        varchar(50)     NOT NULL,
    responsible varchar(50)     NOT NULL,
    phone       varchar(20)     NOT NULL,
    password    varchar(90)     NOT NULL,
    cnpj        varchar(18)     NOT NULL,

    address     varchar(50),
    city        varchar(20),
    state       varchar(20),
    zip_code    varchar(20),
    
    status      varchar(20),

    created_at  timestamp WITHOUT time zone 
        DEFAULT now() NOT NULL,
    updated_at  timestamp WITHOUT time zone,

    CONSTRAINT providers_pkey
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.products (

    id                  uuid DEFAULT uuid_generate_v4 (),
    name                varchar(100)    NOT NULL,
    descripition        varchar(255)    NOT NULL,
    shipping_weight     numeric(8,2),
    price               numeric(8,2)    NOT NULL,
    category_id         uuid,
    last_modified_at    timestamp WITHOUT time zone DEFAULT now(),
    created_by          uuid,

    CONSTRAINT products_pkey
        PRIMARY KEY (id),
    FOREIGN KEY (created_by) 
        REFERENCES providers(id)
);

CREATE TABLE IF NOT EXISTS public.payments (

    id              uuid DEFAULT uuid_generate_v4 (),
    type            varchar(20),
    amount          numeric(8,2),
    card_number     bigint,
    first_name      varchar(20),
    last_name       varchar(20),
    expiration_date varchar(10),
    security_code   integer,
    save_card       boolean,
    brand           varchar(10),
    created_at      timestamp   WITHOUT time zone DEFAULT now(),
    processed_at    timestamp   WITHOUT time zone,
    payment_status  varchar(20),

    CONSTRAINT payments_pky
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.orders (

    id              uuid DEFAULT uuid_generate_v4 (),
    order_date      timestamp WITHOUT time zone DEFAULT now(),
    order_status    varchar(30) NOT NULL,
    decription      varchar(255),
    observation     varchar(255),
    due_date        timestamp WITHOUT time zone,
    payment_date    timestamp WITHOUT time zone,
    price           numeric(8,2),
    customer_id     uuid        NOT NULL,
    provider_id     uuid        NOT NULL,
    payment_id      uuid,

    CONSTRAINT orders_pky
        PRIMARY KEY (id),
    FOREIGN KEY (customer_id) 
        REFERENCES customers(id),
    FOREIGN KEY (provider_id) 
        REFERENCES providers(id),
    FOREIGN KEY (payment_id) 
        REFERENCES payments(id)
);

CREATE TABLE IF NOT EXISTS public.order_details (

    id              uuid DEFAULT uuid_generate_v4 (),
    product_id      uuid        NOT NULL,
    order_id        uuid        NOT NULL,

    quantity        integer     NOT NULL,
    amount          numeric(8,2), 
    
    CONSTRAINT order_detail_pky
        PRIMARY KEY (id),
    FOREIGN KEY (product_id) 
        REFERENCES products(id),
    FOREIGN KEY (order_id) 
        REFERENCES orders(id) 
);