package uk.co.poney.ponysecurity.exceptions;

import org.jose4j.lang.JoseException;
import uk.co.poney.ponysecurity.models.RequestData;

public class JwtCreationException extends AbstractException {

    private final JoseException e;

    public JwtCreationException(JoseException e, RequestData requestData) {
        super("Jwt creation exception", requestData);
        this.e = e;
    }

    public JoseException getE() {
        return e;
    }
}
