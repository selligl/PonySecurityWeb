package uk.co.poney.ponysecurity.modules.notification;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class NotificationResponseFirebase implements NotificationResponseInterface {

    private int failure;
    private NotificationResponseFirebase.Results[] results;
    private int success;
    private String multicast_id;
    private String canonical_ids;

    public int getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = Integer.parseInt(failure);
    }

    public NotificationResponseFirebase.Results[] getResults() {
        return results;
    }

    public void setResults(NotificationResponseFirebase.Results... results) {
        this.results = results;
    }

    public int getSuccess() {
        return success;
    }

    public String getMulticastId() {
        return multicast_id;
    }

    public void setMulticastId(String multicast_id) {
        this.multicast_id = multicast_id;
    }

    public String getCanonicalIds() {
        return canonical_ids;
    }

    public void setCanonicalIds(String canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public boolean isSuccess() {
        return this.getSuccess() == 1;
    }

    public void setSuccess(String success) {
        this.success = Integer.parseInt(success);
    }

    @Override
    public String toString() {
        return "ClassPojo [failure = " + failure + ", results = " + new Gson().toJson(results) + ", success = " + success + ", multicast_id = " + multicast_id + ", canonical_ids = " + canonical_ids + "]";
    }

    @Override
    public String getResponse() {
        return this.results.toString();
    }

    public static class Results {
        private String message_id;

        public String getMessageId() {
            return message_id;
        }

        public void setMessageId(String message_id) {
            this.message_id = message_id;
        }

        @Override
        public String toString() {
            return "ClassPojo [message_id = " + message_id + "]";
        }
    }
}
