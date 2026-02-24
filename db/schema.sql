CREATE DATABASE menu_dishes;

\connect menu_dishes;

CREATE TABLE IF NOT EXISTS dishes (
    id UUID PRIMARY KEY,
    name VARCHAR(150),
    price NUMERIC(5, 2),
    category VARCHAR(100),
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