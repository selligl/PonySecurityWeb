package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.jwt.consumer.InvalidJwtException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtInvalidAudienceException extends AbstractException {

    public JwtInvalidAudienceException(InvalidJwtException e, RequestData requestData) {
        super("Invalid Audience | Claims : " + e.getJwtContext().getJwtClaims(), requestData);
    }
}
