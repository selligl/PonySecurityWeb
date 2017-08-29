package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.consumer.InvalidJwtException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtInvalidException extends AbstractException {

    //private final InvalidJwtException exception;

    public JwtInvalidException(InvalidJwtException exception, RequestData requestData) {
        super("JWT Invalid", requestData);
        //this.exception = exception;
    }
}
