package uk.co.poney.ponysecurity.exceptions;

import uk.co.poney.ponysecurity.models.RequestData;

public class SecondFactorResponseTimeoutException extends AbstractException {

    public SecondFactorResponseTimeoutException(RequestData requestData) {
        super("2nd factor request timeout", requestData);
    }

    public int getHttpErrorCode() {
        return 403;
    }
}
