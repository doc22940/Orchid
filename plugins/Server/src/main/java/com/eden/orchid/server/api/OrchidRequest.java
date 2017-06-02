package com.eden.orchid.server.api;

import com.eden.orchid.utilities.OrchidUtils;
import fi.iki.elonen.NanoHTTPD;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class OrchidRequest {

    OrchidRoute route;
    NanoHTTPD.IHTTPSession session;

    Map<String, String> pathParams;

    public OrchidRequest(NanoHTTPD.IHTTPSession session, OrchidRoute route) {
        this.session = session;
        this.route = route;

        pathParams = ServerUtils.parsePathParams(
                OrchidUtils.normalizePath(route.getPath()),
                OrchidUtils.normalizePath(session.getUri())
        );

    }

    public String path(String key) {
        return pathParams.get(key);
    }

    public String query(String key) {
        return session.getParms().get(key);
    }
    public List<String> queryList(String key) {
        return session.getParameters().get(key);
    }

    public String input(String key) {
        if(pathParams.containsKey(key)) {
            return pathParams.get(key);
        }
        else if(session.getParms().containsKey(key)) {
            return session.getParms().get(key);
        }
        return null;
    }

    public JSONObject body() {
        return new JSONObject();
    }
}
