package http;

import http.exceptions.UnsupportedHttpMethodException;

import javax.annotation.Nullable;

public class RequestLine {

    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;
    private final QueryString queryString;

    public RequestLine(String method, String path, Protocol protocol, @Nullable QueryString queryString) {
        try {
            this.method = HttpMethod.valueOf(method.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedHttpMethodException("Unsupported Exception!", e);
        }
        this.path = path;
        this.protocol = protocol;
        this.queryString = queryString;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

}
