package my.chatapplication.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
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
import com.firebase.client.ValueEventListener;

import java.util.List;
import java.util.Map;

import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.Chat;
import my.chatapplication.DataHolder.NotificationDomain;
import my.chatapplication.DataHolder.User;
import my.chatapplication.DataHolder.VALIDATION;
import my.chatapplication.R;
import my.chatapplication.View.ChatActivity;
import my.chatapplication.View.ChatView;

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
public class ReceiveService extends Service{

    private final String NO_MAIL = "No Mail";
    private Firebase myFirebaseRef = new Firebase("https://sngvsimplechatapp.firebaseio.com") ;
    private Query mRef;
    private List<Chat> mModels;
    private ChildEventListener mListener;
    private boolean firstTime = false;
    private ChildEventListener firebaselisnter;
    private UserController userController;
    private User myUser , freindUser;

    private Firebase firebase = new Firebase("https://sngvsimplechatapp.firebaseio.com/Notification");

    public void showNotification(NotificationDomain chat){
        //We get a reference to the NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification mNotification = new Notification(R.drawable.firebase_logo, chat.getName(), System.currentTimeMillis() );
        //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

        String MyNotificationTitle = chat.getName();
        String MyNotificationText  = chat.getMessage();

        Intent MyIntent = new Intent(this , ChatActivity.class);
        MyIntent.putExtra(Utility.FREIND_USER , new User(chat.getEmail() , chat.getName()));
        PendingIntent StartIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                MyIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

        mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);

        int NOTIFICATION_ID = 1;
        notificationManager.notify(NOTIFICATION_ID , mNotification);
        //We are passing the notification to the NotificationManager with a unique id.

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public ReceiveService() {
        firstTime = false;
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        User newMyUser = (User) intent.getExtras().getSerializable(Utility.MY_USER);
        freindUser = (User) intent.getExtras().getSerializable(Utility.FREIND_USER);

        if(newMyUser == null)
            return 0;

        myUser = newMyUser;

        if(myUser == null)
            return 0;

        if(freindUser != null)
            System.out.println("service data :: start my mail is " +  Utility.removeDot(myUser.getEmail()) +
                                                    "and my freind mail is " + freindUser.getEmail() );
        else
            System.out.println("service data :: start my mail is " +  Utility.removeDot(myUser.getEmail()) +
                    "and my freind mail is null" );

        firebase = firebase.child(Utility.removeDot(myUser.getEmail()));

        firebaselisnter = firebase.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                System.out.println("service data :: " + snapshot.getKey());
                System.out.println("service data :: " + snapshot.getValue().toString());

                if(freindUser  != null && snapshot.getKey().equals(Utility.removeDot(freindUser.getEmail())))
                        return ;

                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String author = (String) newPost.get("name");
                String message = (String) newPost.get("message");
                String email = (String) newPost.get("email");

                showNotification(new NotificationDomain(author,message, email));
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
               System.out.println("service data :: " + snapshot.getKey());
                System.out.println("service data :: " + snapshot.getValue().toString());

                if(freindUser  != null && snapshot.getKey().equals(Utility.removeDot(freindUser.getEmail())))
                    return ;

                String message = (String) snapshot.child("message").getValue();
                String author = (String) snapshot.child("name").getValue();
                String email = (String) snapshot.child("email").getValue();
                showNotification(new NotificationDomain(author ,message, email));
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

    @Override
    public void onDestroy() {
        firebase.removeEventListener(firebaselisnter);
//        super.onDestroy();
    }

}
