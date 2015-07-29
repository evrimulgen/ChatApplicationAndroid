package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;

import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;
import my.chatapplication.Service.ReceiveService;
import my.chatapplication.Service.Utility;


public class Home extends ActionBarActivity implements ChatView{

    private UserController userController;
    private View homeFormView;
    private View progressView;
    private User user;
    private Button logout;
    private Button chat;
    private Button profile;
    private Button findFreind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this , NavigationBar.class);
//        startActivity(intent);
        userController = new UserController(this , CLASSES.HOME , this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initXml();
        onClickListner();

        user = (User) getIntent().getExtras().getSerializable("user");
        showToastMessage(user.toString());


        if(user.getName().equals("") || user.getTelephone().equals("")){
            userController.getUserByEmail(user.getEmail());
            showProgress(true);
        }else {
            Intent intent = new Intent(this, ReceiveService.class);
            intent.putExtra(Utility.MY_USER, user);
            startService(intent);
        }

        Utility.writeInSharedPref("mail"  , user.getEmail()  , this );
    }

    private void onClickListner() {
        final Context context = this;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userController.logOut();
                Intent intent = new Intent(context , Login.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , FreindList.class);
                intent.putExtra(Utility.MY_USER , user);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , UserProfile.class);
                intent.putExtra("user" , user);
                startActivity(intent);
            }
        });

        findFreind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , FindFreind.class);
                startActivity(intent);
            }
        });


    }

    private void initXml() {
        homeFormView = findViewById(R.id.home_form);
        progressView = findViewById(R.id.home_progress);
        logout = (Button) findViewById(R.id.home_log_out);
        chat = (Button) findViewById(R.id.home_chat);
        profile = (Button) findViewById(R.id.home_profile);
        findFreind = (Button) findViewById(R.id.home_findFriend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1){
            case 1: // get User to Save into DB if not exist
                user.setData(msg.obj);
                userController.saveUser(user);
                Intent intent = new Intent(this, ReceiveService.class);
                intent.putExtra(Utility.MY_USER, user);
                startService(intent);
                break;
            case -1: // not connected
                showToastMessage("not connected");
                break;
        }
        showProgress(false);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this , message , Toast.LENGTH_LONG).show();
    }
}
