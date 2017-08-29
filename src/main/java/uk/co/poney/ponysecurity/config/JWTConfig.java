package uk.co.poney.ponysecurity.config;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.poney.ponysecurity.exceptions.RsaGenerationFailed;

@Configuration
public class JWTConfig {

    private static final Logger logger = LoggerFactory.getLogger("PonySecurity.JWTConfig");
    private RsaJsonWebKey globalRSAKey;

    @Bean
    @Autowired
    protected RsaJsonWebKey rsaJsonWebKey() throws RsaGenerationFailed {
        if (this.globalRSAKey == null) {
            // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
            RsaJsonWebKey rsaJsonWebKey;
            try {
                logger.debug("RSA Key Generation started...");
                rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
                logger.debug("RSA Key Generation finished");
                rsaJsonWebKey.setKeyId("k0");
                this.globalRSAKey = rsaJsonWebKey;
            } catch (JoseException e) {
                throw new RsaGenerationFailed(e);
            }
        }
        return this.globalRSAKey;
    }
}
