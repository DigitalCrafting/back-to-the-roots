package org.digitalcrafting.eregold.http.core;

public final class DCHttpHeader {
    public static final String COOKIE = "Cookie";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String AUTHORIZATION = "Authorization";

    public final class Values {
        public static final String JSESSIONID = "JSESSIONID";
        public static final String BEARER = "Bearer "; // Space at the end is required, as it is part of header value

        private Values() {
        }
    }

    private DCHttpHeader() {
    }
}
