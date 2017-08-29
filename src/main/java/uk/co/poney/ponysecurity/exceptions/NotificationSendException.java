package uk.co.poney.ponysecurity.exceptions;

import uk.co.poney.ponysecurity.models.RequestData;
import uk.co.poney.ponysecurity.modules.notification.NotificationResponseInterface;

public class NotificationSendException extends AbstractException {

    public NotificationSendException(RequestData requestData, NotificationResponseInterface notificationResponse) {
        super("Notification send exception ; " + notificationResponse.toString(), requestData);
    }

    @Override
    public int getHttpErrorCode() {
        return 500;
    }

}