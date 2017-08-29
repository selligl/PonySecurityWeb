package uk.co.poney.ponysecurity.modules.notification;

import org.springframework.stereotype.Service;
import uk.co.poney.ponysecurity.exceptions.InternalNotificationSendException;

@Service
public interface NotificationServiceInterface {
    NotificationResponseInterface send() throws InternalNotificationSendException;

    NotificationServiceInterface setPayload(String to, String notificationTitle, String notificationBody, String targetURL, String siteRequested, String user);

    NotificationPayloadInterface getPayload();
}
