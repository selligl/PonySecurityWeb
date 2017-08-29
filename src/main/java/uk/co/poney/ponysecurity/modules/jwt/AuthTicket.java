package uk.co.poney.ponysecurity.modules.jwt;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope("prototype")
public class AuthTicket implements Serializable {

    private static final long serialVersionUID = -8703914031198523648L;

    private final String application;
    private final String user;
    private final String requestId;
    private final int errorCode;
    private int status;

    public AuthTicket(String requestId, int errorCode, String application, String user) {
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.status = 0;
        this.application = application;
        this.user = user;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getApplication() {
        return application;
    }

    public String getUser() {
        return user;
    }

}
