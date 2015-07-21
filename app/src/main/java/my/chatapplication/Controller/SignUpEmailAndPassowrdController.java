package my.chatapplication.Controller;

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

import my.chatapplication.Domain.User;
import my.chatapplication.Model.UMSModule;
import my.chatapplication.R;

public class SignUpEmailAndPassowrdController extends ActionBarActivity {
    private View signUpFormView;
    private View progressView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private EditText repasswordTextView;
    private Button signUpButton;

    private UMSModule UMSModule;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        connectWithXml();
        onClickListner();

        if(messageHandler != null)
            UMSModule = new UMSModule(this , messageHandler);
        else
            showToastMessage("mesage hundler is null");

        context = this;
    }

    public void connectWithXml() {
        emailTextView = (AutoCompleteTextView) findViewById(R.id.signUp_email);
        passwordTextView = (EditText) findViewById(R.id.signUp_password);
        repasswordTextView = (EditText) findViewById(R.id.signUp_rePassword);
        signUpButton = (Button) findViewById(R.id.signUp_button);
        signUpFormView = findViewById(R.id.signUp_form);
        progressView = findViewById(R.id.signUp_progress);
    }

    public void onClickListner(){
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    initSignUp();
                    return true;
                }
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSignUp();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
     * Validate Login form and authenticate.
     */
    public void initSignUp() {

        emailTextView.setError(null);
        passwordTextView.setError(null);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String repasword = repasswordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getString(R.string.field_required));
            focusView = emailTextView;
            cancelLogin = true;
        } else if (!isEmailValid(email)) {
            emailTextView.setError(getString(R.string.invalid_email));
            focusView = emailTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError(getString(R.string.field_required));
            focusView = passwordTextView;
            cancelLogin = true;
        }else if (!isPasswordValid(password)){
            passwordTextView.setError(getString(R.string.field_required));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(repasword)) {
            repasswordTextView.setError(getString(R.string.field_required));
            focusView = repasswordTextView;
            cancelLogin = true;
        } else if (!isPasswordValid(repasword)) {
            repasswordTextView.setError(getString(R.string.invalid_password));
            focusView = repasswordTextView;
            cancelLogin = true;
        }

        if(!cancelLogin && !comparePassword(password , repasword)){
            repasswordTextView.setError(getString(R.string.invalid_repassword));
            focusView = repasswordTextView;
            cancelLogin = true;
        }


        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            showProgress(true);
            UMSModule.signUp(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //add your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 4;
    }

    private boolean comparePassword(String password , String repassword) {
        return password.equals(repassword);
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

            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            if(msg.arg1 == -18){
                View focusView = null;
                emailTextView.setError(getString(R.string.email_alreay_exist));
                focusView = emailTextView;
                focusView.requestFocus();
            }else if (msg.arg1 == 1){
                Intent intent = new Intent(context , SignUpUserInfoController.class);
                intent.putExtra(User.EMAIL, emailTextView.getText().toString());
                intent.putExtra(User.PASSWORD , passwordTextView.getText().toString());
                context.startActivity(intent);
            }
        }
    };


}
