package uk.co.poney.ponysecurity.exceptions;

import uk.co.poney.ponysecurity.models.RequestData;

public abstract class AbstractException extends Exception {

    protected RequestData requestData;

    AbstractException(String message, RequestData requestData) {
        super(message);
        this.requestData = requestData;
    }

    public int getHttpErrorCode() {
        return 500;
    }

    public RequestData getRequestData() {
        return requestData;
    }
}
