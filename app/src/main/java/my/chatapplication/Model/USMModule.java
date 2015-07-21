package my.chatapplication.Model;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.security.MessageDigest;
import java.util.Map;

import my.chatapplication.Controller.LoginController;
import my.chatapplication.R;

/**
 * Created by nasser on 20/07/15.
 */
public class USMModule {

    private Context context;
    private Firebase myFirebaseRef;
    private Handler loginHandler;

    public USMModule(Context context, Handler handler){
        this.context = context;
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase(context.getString(R.string.fireBaseUrl));
        loginHandler = handler;
    }

    public boolean createUser(String email , String password){
        final boolean[] valid = new boolean[1];

        myFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                Message message = loginHandler.obtainMessage();
                message.obj = true;
                loginHandler.sendMessage(message);
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                Message message = loginHandler.obtainMessage();
                message.obj = false;
                loginHandler.sendMessage(message);
            }
        });
        return true;
    }

    public boolean checkeEmailAndPassword(String email , String password){

        myFirebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Message message = loginHandler.obtainMessage();
                message.obj = true;
                loginHandler.sendMessage(message);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Message message = loginHandler.obtainMessage();
                message.obj = false;
                loginHandler.sendMessage(message);
            }
        });

        return true;
    }

}
