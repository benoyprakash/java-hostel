package com.hostel.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    @Deprecated
    public static final String SYSTEM_ACCOUNT = "system";

    @Deprecated
    public static final String ANONYMOUS_USER = "anonymoususer";


    public static final String USER_ROLE_ADMIN = "ROLE_ADMIN";
    public static final String USER_ROLE_USER = "ROLE_USER";
    public static final String USER_ROLE_MANAGER = "ROLE_MANAGER";
    public static final String USER_ROLE_STAFF = "ROLE_STAFF";
    public static final String USER_ROLE_CUSTOMER = "ROLE_CUSTOMER";

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_DELETED = "DELETED";

    public static final String PAYMENT_ENTRY_COMMENT = "Auto generated payment entry";


    private Constants() {
    }
}
