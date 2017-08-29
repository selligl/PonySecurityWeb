package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.consumer.InvalidJwtException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtInvalidException extends AbstractException {

    private final InvalidJwtException parentException;

    public JwtInvalidException(InvalidJwtException parentException, RequestData requestData) {
        super("JWT Invalid", requestData);
        this.parentException = parentException;
    }

    public InvalidJwtException getParentException() {
        return parentException;
    }
}
