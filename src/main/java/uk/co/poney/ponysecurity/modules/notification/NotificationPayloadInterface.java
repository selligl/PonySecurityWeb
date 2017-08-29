package uk.co.poney.ponysecurity.modules.notification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public interface NotificationPayloadInterface {
    NotificationPayloadInterface build(String to, String notificationTitle, String notificationBody, String URL, String SiteRequested, String user);
}
