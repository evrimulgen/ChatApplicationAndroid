package my.chatapplication.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import my.chatapplication.View.ChatView;
import my.chatapplication.Constant.CLASSES;
import my.chatapplication.Constant.VALIDATION;
import my.chatapplication.Model.UMSModule;
import my.chatapplication.View.Login;
import my.chatapplication.View.SignUpEmailAndPassowrd;

/**
 * Created by nasser on 22/07/15.
 */
public class UserController extends Handler {

    private ChatView classView;
    private UMSModule uModule;
    private Context context;

    public UserController(ChatView classView , CLASSES classes , Context context) {
        this.context = context;
        switch (classes){
            case LOGIN:
                this.classView = ((Login)classView);
                break;
            case SIGNUP_EMAIL_PASSWORD:
                this.classView = ((SignUpEmailAndPassowrd)classView);
                break;
        }
        uModule = new UMSModule(this , context);
    }

    private VALIDATION authentication(String email , String password , String repassword){
        VALIDATION ret = authentication(email , password);
        if(ret != VALIDATION.ACCEPTED)
            return ret;

        if (TextUtils.isEmpty(repassword)) {
            return VALIDATION.REPASSWORD_REQUIRED;
        }

        if (!repassword.equals(password)) {
            return VALIDATION.NOT_MATCH_PASSWORD;
        }

        return ret;
    }

    private VALIDATION authentication(String email , String password) {
        if(TextUtils.isEmpty(password)){
            return VALIDATION.PASSWORD_REQUIRED;
        }

        if(!isPasswordValid(password)) {
            return VALIDATION.PASSWORD_INVALID;
        }

        if (TextUtils.isEmpty(email)) {
            return VALIDATION.EMAIL_REQUIRED;
        } else if (!isEmailValid(email)) {
            return VALIDATION.EMAIL_INVALID;
        }
        return VALIDATION.ACCEPTED;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public void login(String email, String password) {
        VALIDATION status = authentication(email , password);
        Message msg = new Message();
        switch (status){
            case ACCEPTED:
                // showToastMessage("Accepted state in UserControll and start login");
                uModule.login(email , password);
                break;
            default:
                // showToastMessage("false and handle message");
                msg.obj = status;
                classView.handleMessage(msg);
                break;
        }
    }

    public void signUp(String email , String password , String repassword){
        VALIDATION status = authentication(email , password , repassword);
        Message msg = new Message();
        switch (status){
            case ACCEPTED:
                // showToastMessage("Accepted state in UserControll and start login");
                uModule.signUp(email , password);
                break;
            default:
                // showToastMessage("false and handle message");
                msg.obj = status;
                classView.handleMessage(msg);
                break;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        // showToastMessage("msg in UserControll " + msg.obj);
        classView.handleMessage(msg);
    }

    private void showToastMessage(String s) {
        Toast.makeText(context , s , Toast.LENGTH_LONG).show();
    }
}
