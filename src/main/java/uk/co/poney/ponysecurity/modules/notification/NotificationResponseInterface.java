package uk.co.poney.ponysecurity.modules.notification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public interface NotificationResponseInterface {
    boolean isSuccess();

    String getResponse();
}
