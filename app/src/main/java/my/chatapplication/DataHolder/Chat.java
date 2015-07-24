package my.chatapplication.DataHolder;

import android.media.Image;

/**
 * @author greg
 * @since 6/21/13
 */
public class Chat {

    private String message;
    private String author;

    // Required default constructor for Firebase object mapping
    public  Chat() {
    }

    public Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
