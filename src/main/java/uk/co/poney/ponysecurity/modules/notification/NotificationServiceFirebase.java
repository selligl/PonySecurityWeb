package uk.co.poney.ponysecurity.modules.notification;

import com.google.gson.Gson;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.co.poney.ponysecurity.exceptions.InternalNotificationSendException;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class NotificationServiceFirebase implements NotificationServiceInterface {

    @Autowired
    private NotificationPayloadFirebase payload;

    @Value("${google.authorization}")
    private String authorization;

    @Override
    public NotificationServiceInterface setPayload(String to, String notificationTitle, String notificationBody, String targetURL, String siteRequested, String user) {
        this.payload.build(
                to,
                notificationTitle,
                notificationBody,
                targetURL,
                siteRequested,
                user
        );
        return this;
    }

    @Override
    public NotificationPayloadInterface getPayload() {
        return this.payload;
    }


    public NotificationResponseFirebase send() throws InternalNotificationSendException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new InternalNotificationSendException(e);
        }

        assert sslContext != null;
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "https://android.googleapis.com/gcm/send";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=".concat(authorization));

        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(this.payload), headers);
        return restTemplate.postForObject(url, entity, NotificationResponseFirebase.class);
    }

}
