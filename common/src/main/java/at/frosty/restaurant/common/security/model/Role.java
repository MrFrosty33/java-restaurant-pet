package at.frosty.restaurant.common.security.model;

public enum Role {
    MANAGER, OBSERVER, WAITER, SERVICE;

    // when update don't forget about DB constraint
}
