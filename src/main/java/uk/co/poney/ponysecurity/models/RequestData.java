package uk.co.poney.ponysecurity.models;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestData {

    private final UUID uuid;
    private String expectedAudience;
    private DeferredResult<ResponseEntity<String>> deferredResult;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String jwt;

    public RequestData() {
        this.uuid = UUID.randomUUID();
    }

    public RequestData(String expectedAudience, String jwt,
                       HttpServletRequest request, HttpServletResponse response) {
        this.uuid = UUID.randomUUID();
        this.expectedAudience = expectedAudience;
        this.request = request;
        this.response = response;
        this.jwt = jwt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getExpectedAudience() {
        return expectedAudience;
    }

    public RequestData setExpectedAudience(String expectedAudience) {
        this.expectedAudience = expectedAudience;
        return this;
    }

    public DeferredResult<ResponseEntity<String>> getDeferredResult() {
        return deferredResult;
    }

    public RequestData setDeferredResult(DeferredResult<ResponseEntity<String>> result) {
        this.deferredResult = result;
        return this;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}


