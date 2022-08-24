package org.digitalcrafting.eregold.http.core.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum DCSession {
    INSTANCE;

    public final Map<String, DCUserContext> sessionMap = new ConcurrentHashMap<>();
}
