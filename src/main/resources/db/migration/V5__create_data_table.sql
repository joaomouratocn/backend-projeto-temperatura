CREATE TABLE data(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    unit_id UUID NOT NULL,
    ref_min NUMERIC NOT NULL,
    ref_cur NUMERIC NOT NULL,
    ref_max NUMERIC NOT NULL,
    env_min NUMERIC NOT NULL,
    env_cur NUMERIC NOT NULL,
    env_max NUMERIC NOT NULL,
    date_time NUMERIC NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_unit FOREIGN KEY (unit_id) REFERENCES units(id)
);