package org.digitalcrafting.eregold.http.core;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.digitalcrafting.eregold.http.core.consts.DCHttpHeader;
import org.digitalcrafting.eregold.http.core.consts.DCHttpStatus;
import org.digitalcrafting.eregold.http.core.security.JWTUtils;
import org.digitalcrafting.eregold.http.core.session.DCSession;
import org.digitalcrafting.eregold.http.core.session.DCUserContext;

import java.util.List;
import java.util.Optional;

// TODO refactor this
public class DCAuthenticator extends Authenticator {
    @Override
    public Result authenticate(HttpExchange exchange) {
        Headers headers = exchange.getRequestHeaders();

        List<String> cookies = headers.get(DCHttpHeader.COOKIE);

        Optional<String> jsessionIdOpt = cookies.stream()
                .filter(c -> c.startsWith(DCHttpHeader.Values.JSESSIONID))
                .findFirst();

        if (jsessionIdOpt.isEmpty()) {
            return new Failure(DCHttpStatus.FORBIDDEN.value());
        }

        String jsessionid = jsessionIdOpt.get().split("=")[1];

        DCUserContext userContext = DCSession.INSTANCE.sessionMap.get(jsessionid);

        if (userContext == null) {
            return new Failure(DCHttpStatus.FORBIDDEN.value());
        }

        String userToken = userContext.getToken();
        String requestToken = headers
                .getFirst(DCHttpHeader.AUTHORIZATION)
                .substring(DCHttpHeader.Values.BEARER.length());

        if (userToken != null && requestToken != null && JWTUtils.validate(requestToken)) {
            return new Success(new HttpPrincipal(userContext.getUserId(), "eregold-realm"));
        } else {
            return new Failure(DCHttpStatus.FORBIDDEN.value());
        }
    }
}
