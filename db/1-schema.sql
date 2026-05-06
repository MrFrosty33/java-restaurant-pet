CREATE DATABASE menu_dishes;

\connect menu_dishes;

CREATE TABLE IF NOT EXISTS dishes (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price NUMERIC(5, 2) NOT NULL,
    category VARCHAR(100),
    active BOOLEAN,
    CONSTRAINT check_category CHECK (category IN (
            'STARTER',
            'SOUP',
            'SALAD',
            'MAIN',
            'SIDE_DISH',
            'DESSERT',
            'DRINK',
            'SNACK'
        )),
        CONSTRAINT check_price CHECK (price > 0)
    );

CREATE TABLE IF NOT EXISTS dish_allergens (
    dish_id UUID REFERENCES dishes(id),
    allergen CHAR NOT NULL,
    PRIMARY KEY (dish_id, allergen)
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(150) NOT NULL,
    password VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID REFERENCES users(id),
    role VARCHAR(100),
    CONSTRAINT check_role CHECK (role IN (
    'MANAGER', 'OBSERVER'
    ))
)