package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import my.chatapplication.Adapter.FindFreindAdapter;
import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.R;

public class FindFreind extends ListActivity implements ChatView{

    private UserController userController;
    private EditText email;
    private View homeFormView;
    private View progressView;

    // TODO: change this to your own Firebase URL
    private static String FIREBASE_URL = "https://sngvsimplechatapp.firebaseio.com";

    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private FindFreindAdapter mFindFreindAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_freind);
        userController = new UserController(this , CLASSES.FIND_FREIND , this);
        mFirebaseRef = new Firebase(FIREBASE_URL).child("users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initXml();
        onClickListner();

        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mFindFreindAdapter = new FindFreindAdapter(mFirebaseRef , this, R.layout.find_freind_item , "" , this);
        listView.setAdapter(mFindFreindAdapter);
        mFindFreindAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mFindFreindAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(FindFreind.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FindFreind.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    private void onClickListner() {
        final Context context;
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                // showToastMessage(textView.getText().toString());
                showProgress(true);
                mFindFreindAdapter.startListner(textView.getText().toString());
                return false;
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }

    private void initXml() {
        email = (EditText) findViewById(R.id.findFreind_phoneNumber);
        homeFormView = findViewById(R.id.findFreind_form);
        progressView = findViewById(R.id.findFreind_progress);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_freind, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleMessage(Message msg) {
        showProgress(false);
    }


    /**
     * Shows the progress UI and hides the login form.
     * @param show is boolean to show progress or no
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            homeFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
