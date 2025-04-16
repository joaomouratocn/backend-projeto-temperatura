CREATE TABLE UNITS(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(50) NOT NULL
);