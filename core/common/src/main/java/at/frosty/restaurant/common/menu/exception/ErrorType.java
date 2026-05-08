package at.frosty.restaurant.common.menu.exception;

public enum ErrorType {
    DISH_NOT_FOUND,
    DISH_DEACTIVATED,
    DISH_ALREADY_EXISTS,
    DISH_ALREADY_ACTIVATED,
    DISH_ALREADY_DEACTIVATED,
    UNAUTHORIZED,
    VALIDATION_ERROR,
    INTERNAL_ERROR;
}
