package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.consumer.InvalidJwtException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtExpiredException extends AbstractException {

    public JwtExpiredException(InvalidJwtException e, RequestData requestData) {
        super("JWT Expired. | Claims : " + e.getJwtContext().getJwtClaims(), requestData);
    }
}
