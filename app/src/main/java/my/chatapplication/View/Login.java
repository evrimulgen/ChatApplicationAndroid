package my.chatapplication.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import my.chatapplication.Chat.ChatActivity;
import my.chatapplication.Constant.CLASSES;
import my.chatapplication.Constant.VALIDATION;
import my.chatapplication.Controller.UserController;
import my.chatapplication.R;
//http://javapapers.com/android/beautiful-android-login-screen-design-tutorial/
public class Login extends ActionBarActivity implements ChatView{

    private View loginFormView;
    private View progressView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private Button loginButton;

    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userController = new UserController(this , CLASSES.LOGIN , this);
        connectWithXml();
        onClickListner();
    }

    public void onClickListner(){
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                showProgress(true);
//                 showToastMessage("start loggin with email " + email + "and password " + password);
                userController.login(email, password);

            }
        });

    }

    public void connectWithXml() {
        emailTextView = (AutoCompleteTextView) findViewById(R.id.login_email);
        passwordTextView = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void showToastMessage(String msg){
        Toast.makeText(this , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleMessage(Message msg) {
        // showToastMessage("get to login activity with status is " + msg.obj);
        VALIDATION status = (VALIDATION) msg.obj;
        emailTextView.setError(null);
        passwordTextView.setError(null);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;

        View focusView = null;

        showProgress(false);

        switch (status){
            case PASSWORD_REQUIRED:
                passwordTextView.setError(getString(R.string.password_required));
                focusView = passwordTextView;
                break;
            case PASSWORD_INVALID:
                passwordTextView.setError(getString(R.string.password_is_incorrect));
                focusView = passwordTextView;
                break;
            case EMAIL_REQUIRED:
                emailTextView.setError(getString(R.string.email_required));
                focusView = emailTextView;
                break;
            case EMAIL_INVALID:
                emailTextView.setError(getString(R.string.email_invalid));
                focusView = emailTextView;
                break;
            case ACCEPTED:
                Intent intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                return ;
        }
        focusView.requestFocus();
    }

}
