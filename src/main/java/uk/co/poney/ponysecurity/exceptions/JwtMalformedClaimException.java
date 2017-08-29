package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.MalformedClaimException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtMalformedClaimException extends AbstractException {

    private final MalformedClaimException parentException;

    public JwtMalformedClaimException(MalformedClaimException parentException, RequestData requestData) {
        super("Malformed Claim", requestData);
        this.parentException = parentException;
    }

    public MalformedClaimException getParentException() {
        return parentException;
    }
}
