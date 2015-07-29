package my.chatapplication.DataHolder;

import java.security.Timestamp;

/**
 * Created by nasser on 29/07/15.
 */
public class LastMessage {
    String name;
    String message;
    String email;

    public LastMessage() {
    }

    public LastMessage(String name, String message , String email) {
        this.name = name;
        this.message = message;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "name " + getName() + " email " + email + " message " + message ;
    }

    public boolean equals(LastMessage o) {
        if(name.equals(o.getName()) && email.equals(o.getEmail())){
            return true;
        }
        return false;
    }
}
