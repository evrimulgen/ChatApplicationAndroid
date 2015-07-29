package my.chatapplication.Service;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import my.chatapplication.DataHolder.LastMessage;
import my.chatapplication.DataHolder.NotificationDomain;

/**
 * Created by nasser on 28/07/15.
 */
public class ChatNotificationService {

    private String myMail;

    private Firebase firebaseNotification = new Firebase("https://sngvsimplechatapp.firebaseio.com/Notification");
    private Firebase firebaseFreind = new Firebase("https://sngvsimplechatapp.firebaseio.com/Freind");

    public ChatNotificationService(String myMail) {
        this.myMail = myMail;
    }

    public void pushMessageNotification(NotificationDomain notification , String freindMail) {
        Firebase pushNotification = firebaseNotification.child(Utility.removeDot(freindMail)).child(Utility.removeDot(myMail));

        pushNotification.setValue(notification, new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

            }
        });

    }

    public void removeMessageNotification(String freindMail){
        Firebase pushNotification = firebaseNotification.child(Utility.removeDot(myMail)).child(Utility.removeDot(freindMail));
        pushNotification.removeValue();
    }

    public String getMyMail() {
        return myMail;
    }

    public void setMyMail(String myMail) {
        this.myMail = myMail;
    }

    public void addFreind(LastMessage lastMessage) {
        Firebase pushNotification = firebaseFreind.child(Utility.removeDot(myMail)).child(Utility.removeDot(lastMessage.getEmail()));

        pushNotification.setValue(lastMessage, new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

            }
        });
    }
}
