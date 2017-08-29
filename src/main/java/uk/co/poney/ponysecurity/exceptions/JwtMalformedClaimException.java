package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.MalformedClaimException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtMalformedClaimException extends AbstractException {

    //private final MalformedClaimException exception;

    public JwtMalformedClaimException(MalformedClaimException exception, RequestData requestData) {
        super("Malformed Claim", requestData);
        //this.exception = exception;
    }
}
