package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.chatapplication.Chat.ChatActivity;
import my.chatapplication.DataHolder.User;
import my.chatapplication.Model.UMSModule;
import my.chatapplication.R;

public class SignUpUserInfo extends ActionBarActivity {
    private User user;
    private EditText username;
    private EditText telephone;
    private Button complete;

    private View userDomainView;
    private View progressView;

    private UMSModule UmsModule;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_domain);
        context = this;
//        if(messageHandler != null)
//            UmsModule = new UMSModule(this , messageHandler);
//        else
//            showToastMessage("mesage hundler is null");

        connectWithXml();
        clickListnerInit();
    }

    private void clickListnerInit() {
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUserInfo();
            }
        });
    }

    private void initUserInfo() {
//        fillUserObject();
//        telephone.setError(null);
//        username.setError(null);
//
//        String name = username.getText().toString();
//        String telep = telephone.getText().toString();
//
//        boolean cancelLogin = false;
//        View focusView = null;
//
//        if (TextUtils.isEmpty(telep)) {
//            telephone.setError(getString(R.string.field_required));
//            focusView = telephone;
//            cancelLogin = true;
//        } else if (!isTelephoneValid(telep)) {
//            telephone.setError(getString(R.string.invalid_telephone_number));
//            focusView = telephone;
//            cancelLogin = true;
//        }
//        if (TextUtils.isEmpty(name)) {
//            username.setError(getString(R.string.field_required));
//            focusView = username;
//            cancelLogin = true;
//        }
//
//        if (cancelLogin) {
//            focusView.requestFocus();
//        } else {
//            showProgress(true);
//            UmsModule.saveUser(user);
//        }
    }

    private boolean isTelephoneValid(String telep) {
        return true;
    }

    private void fillUserObject() {
        String email = getIntent().getExtras().getString(User.EMAIL);
        String password = getIntent().getExtras().getString(User.PASSWORD);
        String telep = telephone.getText().toString();
        String name = username.getText().toString();

        user = new User(email , password , telep , name);
    }

    private void connectWithXml() {
        username = (EditText) findViewById(R.id.userDomain_username);
        telephone = (EditText) findViewById(R.id.userDomain_telephone);
        complete = (Button) findViewById(R.id.userDomain_complete);

        userDomainView = findViewById(R.id.userDomain_form);
        progressView = findViewById(R.id.userDomain_progress);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_domain_menu, menu);
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

            userDomainView.setVisibility(show ? View.GONE : View.VISIBLE);
            userDomainView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    userDomainView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            userDomainView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public void showToastMessage(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    private Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg){
            showProgress(false);
            if(msg.arg1 == 1){
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }else {
                showToastMessage("Server unavailable");
                showToastMessage(((String)msg.obj));
            }
        }
    };


}
