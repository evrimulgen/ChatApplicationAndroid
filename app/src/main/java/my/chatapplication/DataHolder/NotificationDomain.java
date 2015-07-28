package my.chatapplication.DataHolder;

/**
 * Created by nasser on 28/07/15.
 */
public class NotificationDomain {
    String name;
    String message;

    public NotificationDomain(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
