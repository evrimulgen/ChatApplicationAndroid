package my.chatapplication.Service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.chatapplication.DataHolder.Chat;
import my.chatapplication.R;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is a generic way of backing an Android ListView with a Firebase location.
 * It handles all of the child events at the given Firebase location. It marshals received data into the given
 * class type. Extend this class and provide an implementation of <code>populateView</code>, which will be given an
 * instance of your list item mLayout and an instance your class that holds your data. Simply populate the view however
 * you like and this class will handle updating the list as the data changes.
 *
 */
public class ChatService extends Service{

    private Query mRef;
    private Class<Chat> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<Chat> mModels;
    private Map<String, List<Chat>> chatMap;
    private ChildEventListener mListener;
    private ChatService context = this;
    boolean firstTime = false;

    private Firebase firebase = new Firebase("https://sngvsimplechatapp.firebaseio.com/Message/chat");

        public void showNotification(Chat chat){
        //We get a reference to the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification mNotification = new Notification(R.drawable.firebase_logo, chat.getAuthor(), System.currentTimeMillis() );
        //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

        String MyNotificationTitle = chat.getAuthor();
        String MyNotificationText  = chat.getMessage();

        Intent MyIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent StartIntent = PendingIntent.getActivity(getApplicationContext(), 0, MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

        mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);

        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID , mNotification);
        //We are passing the notification to the NotificationManager with a unique id.

    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public ChatService() {
        firstTime = false;
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Start Service");

        firebase.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                if (firstTime == false) {
                    firstTime = true;
                    return;
                }
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String author = (String) newPost.get("author");
                String message = (String) newPost.get("message");
                System.out.println("ChatService :: Author: " + newPost.get("author"));
                System.out.println("ChatService :: Title: " + newPost.get("message"));
                showNotification(new Chat(message , author));
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                if (firstTime == false) {
                    firstTime = true;
                    return;
                }
                String message = (String) snapshot.child("message").getValue();
                String author = (String) snapshot.child("author").getValue();
                System.out.println("ChatService :: The updated post title is " + author + " " + message);
                showNotification(new Chat(message,  author));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return 1;
    }
}
