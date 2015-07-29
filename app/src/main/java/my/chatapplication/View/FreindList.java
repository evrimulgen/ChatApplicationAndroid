package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import my.chatapplication.Adapter.FindFreindAdapter;
import my.chatapplication.Adapter.FreindListAdatper;
import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.LastMessage;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;
import my.chatapplication.Service.Utility;

public class FreindList extends ListActivity implements ChatView{

    private UserController userController;
    private EditText email;
    private View freindListFormView;
    private View progressView;

    private ValueEventListener mConnectedListener;
    private FreindListAdatper  mFreindAdapter;
    private Intent intent;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind_list);
        user = (User) getIntent().getExtras().getSerializable(Utility.MY_USER);
        // showToastMessage(user.toString());

    }

    private void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_freind_list, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        System.out.println("listen service :: initliaze freind list adapter");
        mFreindAdapter   = new FreindListAdatper( R.layout.last_message_item , this , user , this);

        listView.setAdapter(mFreindAdapter);

        mFreindAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mFreindAdapter.getCount() - 1);
            }
        });

        freindListFormView = findViewById(R.id.freindList_form);
        progressView = findViewById(R.id.freindList_progress);

        showProgress(true);

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

            freindListFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            freindListFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    freindListFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            freindListFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
}
