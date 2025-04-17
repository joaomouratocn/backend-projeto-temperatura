CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role INT NOT NULL DEFAULT 1,
    unit UUID NOT NULL,

    FOREIGN KEY (unit) REFERENCES units(id)
);