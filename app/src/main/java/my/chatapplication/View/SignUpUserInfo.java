package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.VALIDATION;
import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;

public class SignUpUserInfo extends ActionBarActivity implements ChatView{
    private User user;
    private EditText nameTextEdit;
    private EditText telephoneTextEdit;
    private Button complete;

    private View userDomainView;
    private View progressView;

    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userController = new UserController(this , CLASSES.SIGNUP_USERINFO , this);

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
        String name = nameTextEdit.getText().toString();
        String telephone = telephoneTextEdit.getText().toString();
        showProgress(true);
        user = fillUserObject();
        userController.saveInfo(user);
    }

    private boolean isTelephoneValid(String telep) {
        return true;
    }

    private User fillUserObject() {
        String email = getIntent().getExtras().getString(User.EMAIL);
        String password = getIntent().getExtras().getString(User.PASSWORD);
        String telep = telephoneTextEdit.getText().toString();
        String name = nameTextEdit.getText().toString();
        
        return new User(email , password , telep , name);
    }

    private void connectWithXml() {
        nameTextEdit = (EditText) findViewById(R.id.userDomain_username);
        telephoneTextEdit = (EditText) findViewById(R.id.userDomain_telephone);
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

    public void showToastMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleMessage(Message msg) {
        showProgress(false);

        View focusView = null;

        switch (((VALIDATION)msg.obj)){
            case NAME_REQUIRED:
                nameTextEdit.setError(getString(R.string.name_is_required));
                focusView = nameTextEdit;
                break;
            case ACCEPTED:
                Intent intent = new Intent(this , Home.class);
                // showToastMessage(user.toString());
                intent.putExtra("user" , user);
                startActivity(intent);
                return ;
            case TELEPHONE_NUMBER_REQUIRED:
                telephoneTextEdit.setError(getString(R.string.telephone_is_required));
                focusView = telephoneTextEdit;
                break;
            case TELEPHONE_NUMBER_NVALID:
                telephoneTextEdit.setError(getString(R.string.telephone_is_required));
                focusView = telephoneTextEdit;
                break;
        }

        focusView.requestFocus();
    }

}
