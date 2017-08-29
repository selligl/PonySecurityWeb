package uk.co.poney.ponysecurity.modules.notification;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class NotificationPayloadFirebase implements NotificationPayloadInterface {
    private String to;

    private Data data;

    private Notification notification;

    public NotificationPayloadFirebase build(String to, String notificationTitle, String notificationBody, String url, String siteRequested, String user) {
        this.to = to;
        this.notification = new NotificationPayloadFirebase.Notification(notificationBody, notificationTitle);
        this.data = new NotificationPayloadFirebase.Data(url, siteRequested, user);
        return this;
    }


    @Override
    public String toString() {
        return "ClassPojo [to = " + to + ", data = " + data + ", notification = " + notification + "]";
    }

    private class Notification {
        private String body;

        private String title;

        private String click_action;

        Notification(String body, String title) {
            this.click_action = "ACTIVITY_CODE";
            this.body = body;
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickAction() {
            return click_action;
        }

        public void setClickAction(String click_action) {
            this.click_action = click_action;
        }

        @Override
        public String toString() {
            return "ClassPojo [body = " + body + ", title = " + title + ", click_action = " + click_action + "]";
        }
    }

    private class Data {
        private String URL;

        private String siteRequested;

        private String user;

        Data(String URL, String siteRequested, String user) {
            this.URL = URL;
            this.siteRequested = siteRequested;
            this.user = user;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String url) {
            this.URL = url;
        }

        public String getSiteRequested() {
            return siteRequested;
        }

        public void setSiteRequested(String siteRequested) {
            this.siteRequested = siteRequested;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "ClassPojo [URL = " + URL + ", siteRequested = " + siteRequested + ", user = " + user + "]";
        }
    }
}
