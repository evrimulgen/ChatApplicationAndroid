package my.chatapplication.Model;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import my.chatapplication.Controller.LoginController;
import my.chatapplication.Domain.User;
import my.chatapplication.R;

/**
 * Created by nasser on 20/07/15.
 */
public class UMSModule {

    private Context context;
    private Firebase myFirebaseRef;
    private Handler loginHandler;

    public UMSModule(Context context, Handler handler){
        this.context = context;
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase(context.getString(R.string.fireBaseUrl));
        loginHandler = handler;
    }

    public void signUp(String email, String password){
        myFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = 1;
                message.obj = result;
                loginHandler.sendMessage(message);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = firebaseError.getCode();
                message.obj = firebaseError.getMessage();
                loginHandler.sendMessage(message);
            }
        });
    }

    public void login(String email, String password){

        myFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = 1;
                message.obj = authData;
                loginHandler.sendMessage(message);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = firebaseError.getCode();
                message.obj = firebaseError.getMessage();
                loginHandler.sendMessage(message);
            }
        });
    }

    public void saveUser(User user){
        Firebase usersave = myFirebaseRef.child("users").child(removeDot(user.getEmail()));
        usersave.setValue(user);

        usersave.setValue(user, new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Message message = loginHandler.obtainMessage();
                try {
                    message.arg1 = firebaseError.getCode();
                    message.obj = firebaseError.getMessage();
                    loginHandler.sendMessage(message);
                } catch (Exception e) {
                    message.obj = 1;
                    message.obj = firebase.getKey();
                    loginHandler.sendMessage(message);
                }

            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(context , message , Toast.LENGTH_LONG).show();
    }

    public void updateUser(User user){
        Firebase userUpdate = myFirebaseRef.child("users").child(user.getEmail());
        Map<String, Object> userdata = user.convertToHashMap();
        userUpdate.updateChildren(userdata);
    }

    public void getUser(String email){

        Firebase _user = myFirebaseRef.child("users").child(removeDot(email));

        _user.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = 1;
                message.obj = dataSnapshot.getValue();
                loginHandler.sendMessage(message);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Message message = loginHandler.obtainMessage();
                message.arg1 = -1;
                message.obj = firebaseError.getMessage();
                loginHandler.sendMessage(message);
            }
        });
    }

    public String removeDot(String msg){
        String newMsg = msg.replace('.','@');
        System.out.println(newMsg);
        return newMsg;
    }
}
