package my.chatapplication.Chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import my.chatapplication.R;

/**
 * Created by nasser on 23/07/15.
 */
public class ChatService extends Service {
    private ChildEventListener mListener;
    private Firebase firebase = new Firebase("https://sngvsimplechatapp.firebaseio.com/Message");
    private Intent intent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showNotification(Chat chat){
        //We get a reference to the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String MyText = "Reminder";
        Notification mNotification = new Notification(R.drawable.firebase_logo, MyText, System.currentTimeMillis() );
        //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

        String MyNotificationTitle = "Medicine!";
        String MyNotificationText  = "Don't forget to take your medicine!";

        Intent MyIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent StartIntent = PendingIntent.getActivity(getApplicationContext(),0,MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

        mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);

        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID , mNotification);
        //We are passing the notification to the NotificationManager with a unique id.

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service Here :: Start");
        mListener = firebase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Service Here :: Child Added" + dataSnapshot.getValue().toString());
                showNotification(new Chat("",""));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("Service Here :: Change" + dataSnapshot.getValue().toString());
                showNotification(new Chat("", ""));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("Service Here :: Remove");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                System.out.println("Service Here :: moved");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }
        });
        return 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
