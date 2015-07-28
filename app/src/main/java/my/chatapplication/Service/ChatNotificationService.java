package my.chatapplication.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.List;
import java.util.Map;

import my.chatapplication.DataHolder.Chat;
import my.chatapplication.DataHolder.NotificationDomain;
import my.chatapplication.DataHolder.VALIDATION;
import my.chatapplication.R;

/**
 * Created by nasser on 28/07/15.
 */
public class ChatNotificationService {

    private String myMail;

    private Firebase firebase = new Firebase("https://sngvsimplechatapp.firebaseio.com/Notification");

    public ChatNotificationService(String myMail) {
        this.myMail = myMail;
    }

    public void pushMessageNotification(NotificationDomain notification , String freindMail) {
        Firebase pushNotification = firebase.child(Utility.removeDot(freindMail)).child(Utility.removeDot(myMail));
        pushNotification.setValue(notification, new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

            }
        });

    }

    public void removeMessageNotification(String freindMail){
        Firebase pushNotification = firebase.child(Utility.removeDot(myMail)).child(Utility.removeDot(freindMail));
        pushNotification.removeValue();
    }

    public String getMyMail() {
        return myMail;
    }

    public void setMyMail(String myMail) {
        this.myMail = myMail;
    }
}
