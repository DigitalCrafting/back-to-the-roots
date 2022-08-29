package org.digitalcrafting.eregold.http.core.consts;

public enum DCHttpStatus {
    CONTINUE(100, DCHttpStatus.Series.INFORMATIONAL, "Continue"),
    SWITCHING_PROTOCOLS(101, DCHttpStatus.Series.INFORMATIONAL, "Switching Protocols"),
    PROCESSING(102, DCHttpStatus.Series.INFORMATIONAL, "Processing"),
    CHECKPOINT(103, DCHttpStatus.Series.INFORMATIONAL, "Checkpoint"),
    OK(200, DCHttpStatus.Series.SUCCESSFUL, "OK"),
    CREATED(201, DCHttpStatus.Series.SUCCESSFUL, "Created"),
    ACCEPTED(202, DCHttpStatus.Series.SUCCESSFUL, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, DCHttpStatus.Series.SUCCESSFUL, "Non-Authoritative Information"),
    NO_CONTENT(204, DCHttpStatus.Series.SUCCESSFUL, "No Content"),
    RESET_CONTENT(205, DCHttpStatus.Series.SUCCESSFUL, "Reset Content"),
    PARTIAL_CONTENT(206, DCHttpStatus.Series.SUCCESSFUL, "Partial Content"),
    MULTI_STATUS(207, DCHttpStatus.Series.SUCCESSFUL, "Multi-Status"),
    ALREADY_REPORTED(208, DCHttpStatus.Series.SUCCESSFUL, "Already Reported"),
    IM_USED(226, DCHttpStatus.Series.SUCCESSFUL, "IM Used"),
    MULTIPLE_CHOICES(300, DCHttpStatus.Series.REDIRECTION, "Multiple Choices"),
    MOVED_PERMANENTLY(301, DCHttpStatus.Series.REDIRECTION, "Moved Permanently"),
    FOUND(302, DCHttpStatus.Series.REDIRECTION, "Found"),
    @Deprecated
    MOVED_TEMPORARILY(302, DCHttpStatus.Series.REDIRECTION, "Moved Temporarily"),
    SEE_OTHER(303, DCHttpStatus.Series.REDIRECTION, "See Other"),
    NOT_MODIFIED(304, DCHttpStatus.Series.REDIRECTION, "Not Modified"),
    @Deprecated
    USE_PROXY(305, DCHttpStatus.Series.REDIRECTION, "Use Proxy"),
    TEMPORARY_REDIRECT(307, DCHttpStatus.Series.REDIRECTION, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, DCHttpStatus.Series.REDIRECTION, "Permanent Redirect"),
    BAD_REQUEST(400, DCHttpStatus.Series.CLIENT_ERROR, "Bad Request"),
    UNAUTHORIZED(401, DCHttpStatus.Series.CLIENT_ERROR, "Unauthorized"),
    PAYMENT_REQUIRED(402, DCHttpStatus.Series.CLIENT_ERROR, "Payment Required"),
    FORBIDDEN(403, DCHttpStatus.Series.CLIENT_ERROR, "Forbidden"),
    NOT_FOUND(404, DCHttpStatus.Series.CLIENT_ERROR, "Not Found"),
    METHOD_NOT_ALLOWED(405, DCHttpStatus.Series.CLIENT_ERROR, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, DCHttpStatus.Series.CLIENT_ERROR, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, DCHttpStatus.Series.CLIENT_ERROR, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, DCHttpStatus.Series.CLIENT_ERROR, "Request Timeout"),
    CONFLICT(409, DCHttpStatus.Series.CLIENT_ERROR, "Conflict"),
    GONE(410, DCHttpStatus.Series.CLIENT_ERROR, "Gone"),
    LENGTH_REQUIRED(411, DCHttpStatus.Series.CLIENT_ERROR, "Length Required"),
    PRECONDITION_FAILED(412, DCHttpStatus.Series.CLIENT_ERROR, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, DCHttpStatus.Series.CLIENT_ERROR, "Payload Too Large"),
    @Deprecated
    REQUEST_ENTITY_TOO_LARGE(413, DCHttpStatus.Series.CLIENT_ERROR, "Request Entity Too Large"),
    URI_TOO_LONG(414, DCHttpStatus.Series.CLIENT_ERROR, "URI Too Long"),
    @Deprecated
    REQUEST_URI_TOO_LONG(414, DCHttpStatus.Series.CLIENT_ERROR, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, DCHttpStatus.Series.CLIENT_ERROR, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, DCHttpStatus.Series.CLIENT_ERROR, "Requested range not satisfiable"),
    EXPECTATION_FAILED(417, DCHttpStatus.Series.CLIENT_ERROR, "Expectation Failed"),
    I_AM_A_TEAPOT(418, DCHttpStatus.Series.CLIENT_ERROR, "I'm a teapot"),
    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419, DCHttpStatus.Series.CLIENT_ERROR, "Insufficient Space On Resource"),
    @Deprecated
    METHOD_FAILURE(420, DCHttpStatus.Series.CLIENT_ERROR, "Method Failure"),
    @Deprecated
    DESTINATION_LOCKED(421, DCHttpStatus.Series.CLIENT_ERROR, "Destination Locked"),
    UNPROCESSABLE_ENTITY(422, DCHttpStatus.Series.CLIENT_ERROR, "Unprocessable Entity"),
    LOCKED(423, DCHttpStatus.Series.CLIENT_ERROR, "Locked"),
    FAILED_DEPENDENCY(424, DCHttpStatus.Series.CLIENT_ERROR, "Failed Dependency"),
    TOO_EARLY(425, DCHttpStatus.Series.CLIENT_ERROR, "Too Early"),
    UPGRADE_REQUIRED(426, DCHttpStatus.Series.CLIENT_ERROR, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, DCHttpStatus.Series.CLIENT_ERROR, "Precondition Required"),
    TOO_MANY_REQUESTS(429, DCHttpStatus.Series.CLIENT_ERROR, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, DCHttpStatus.Series.CLIENT_ERROR, "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, DCHttpStatus.Series.CLIENT_ERROR, "Unavailable For Legal Reasons"),
    INTERNAL_SERVER_ERROR(500, DCHttpStatus.Series.SERVER_ERROR, "Internal Server Error"),
    NOT_IMPLEMENTED(501, DCHttpStatus.Series.SERVER_ERROR, "Not Implemented"),
    BAD_GATEWAY(502, DCHttpStatus.Series.SERVER_ERROR, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, DCHttpStatus.Series.SERVER_ERROR, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, DCHttpStatus.Series.SERVER_ERROR, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, DCHttpStatus.Series.SERVER_ERROR, "HTTP Version not supported"),
    VARIANT_ALSO_NEGOTIATES(506, DCHttpStatus.Series.SERVER_ERROR, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, DCHttpStatus.Series.SERVER_ERROR, "Insufficient Storage"),
    LOOP_DETECTED(508, DCHttpStatus.Series.SERVER_ERROR, "Loop Detected"),
    BANDWIDTH_LIMIT_EXCEEDED(509, DCHttpStatus.Series.SERVER_ERROR, "Bandwidth Limit Exceeded"),
    NOT_EXTENDED(510, DCHttpStatus.Series.SERVER_ERROR, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, DCHttpStatus.Series.SERVER_ERROR, "Network Authentication Required");

    private static final DCHttpStatus[] VALUES = values();
    private final int value;
    private final Series series;
    private final String reasonPhrase;

    DCHttpStatus(int value, Series series, String reasonPhrase) {
        this.value = value;
        this.series = series;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public Series series() {
        return this.series;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public boolean is1xxInformational() {
        return this.series() == DCHttpStatus.Series.INFORMATIONAL;
    }

    public boolean is2xxSuccessful() {
        return this.series() == DCHttpStatus.Series.SUCCESSFUL;
    }

    public boolean is3xxRedirection() {
        return this.series() == DCHttpStatus.Series.REDIRECTION;
    }

    public boolean is4xxClientError() {
        return this.series() == DCHttpStatus.Series.CLIENT_ERROR;
    }

    public boolean is5xxServerError() {
        return this.series() == DCHttpStatus.Series.SERVER_ERROR;
    }

    public boolean isError() {
        return this.is4xxClientError() || this.is5xxServerError();
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    public static DCHttpStatus valueOf(int statusCode) {
        DCHttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return status;
        }
    }

    public static DCHttpStatus resolve(int statusCode) {
        DCHttpStatus[] statuses = VALUES;
        int var2 = statuses.length;

        for (int statusIndex = 0; statusIndex < var2; ++statusIndex) {
            DCHttpStatus status = statuses[statusIndex];
            if (status.value == statusCode) {
                return status;
            }
        }

        return null;
    }

    public enum Series {
        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

        public static Series valueOf(int statusCode) {
            Series series = resolve(statusCode);
            if (series == null) {
                throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
            } else {
                return series;
            }
        }

        public static Series resolve(int statusCode) {
            int seriesCode = statusCode / 100;
            Series[] seriesValues = values();
            int var3 = seriesValues.length;

            for (int seriesIndex = 0; seriesIndex < var3; ++seriesIndex) {
                Series series = seriesValues[seriesIndex];
                if (series.value == seriesCode) {
                    return series;
                }
            }

            return null;
        }
    }
}
