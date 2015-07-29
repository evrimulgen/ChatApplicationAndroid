package my.chatapplication.Controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import my.chatapplication.Adapter.FreindListAdatper;
import my.chatapplication.DataHolder.User;
import my.chatapplication.Model.UMSFireBase;
import my.chatapplication.Model.UMSQLController;
import my.chatapplication.View.ChatActivity;
import my.chatapplication.View.ChatView;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.VALIDATION;
import my.chatapplication.View.FindFreind;
import my.chatapplication.View.Home;
import my.chatapplication.View.Login;
import my.chatapplication.View.SignUpEmailAndPassowrd;
import my.chatapplication.View.SignUpUserInfo;
import my.chatapplication.View.UserProfile;
import my.chatapplication.View.Welcome;

/**
 * Created by nasser on 22/07/15.
 */
public class UserController extends Handler {

    private ChatView classView;
    private UMSFireBase uModule;
    private Context context;
    private UMSQLController dbControler;

    public UserController(ChatView classView , CLASSES classes , Context context) {
        this.context = context;
        switch (classes){
            case LOGIN:
                this.classView = ((Login)classView);
                break;
            case SIGNUP_EMAIL_PASSWORD:
                this.classView = ((SignUpEmailAndPassowrd)classView);
                break;
            case SIGNUP_USERINFO:
                this.classView = ((SignUpUserInfo)classView);
                break;
            case USER_PROFILE:
                this.classView = ((UserProfile)classView);
                break;
            case HOME:
                this.classView = ((Home)classView);
                break;
            case FIND_FREIND:
                this.classView = ((FindFreind)classView);
                break;
            case CHAT_ACTIVITY:
                this.classView = ((ChatActivity)classView);
                break;
            case FREIND_LIST_ADAPTER:
                this.classView = ((FreindListAdatper)classView);
                break;
            case WELCOME:
                this.classView = ((Welcome)classView);
                break;
        }
        uModule = new UMSFireBase(this , context);
        dbControler = new UMSQLController(context);
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
                // // showToastMessage("Accepted state in UserControll and start login");
                uModule.login(email, password);
                break;
            default:
                // // showToastMessage("false and handle message");
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
                // // showToastMessage("Accepted state in UserControll and start login");
                uModule.insertUser(email, password);
                break;
            default:
                // // showToastMessage("false and handle message");
                msg.obj = status;
                classView.handleMessage(msg);
                break;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        // // showToastMessage("msg in UserControll " + msg.obj);
        classView.handleMessage(msg);
    }

    private void  showToastMessage(String s) {
        Toast.makeText(context , s , Toast.LENGTH_LONG).show();
    }

    public void saveInfo(User user) {
        Message msg = new Message();
        if(TextUtils.isEmpty(user.getName())){
            msg.obj = VALIDATION.NAME_REQUIRED;
            classView.handleMessage(msg);
            return ;
        }

        if(TextUtils.isEmpty(user.getTelephone())){
            msg.obj = VALIDATION.TELEPHONE_NUMBER_REQUIRED;
            classView.handleMessage(msg);
            return ;
        }

        if(!isTelephoneValid(user.getTelephone())){
            msg.obj = VALIDATION.TELEPHONE_NUMBER_NVALID;
            classView.handleMessage(msg);
            return ;
        }

        uModule.insertUser(user);
        try{
            dbControler.insertUser(user);
        }catch (Exception e){
            Toast.makeText(context , e.toString() , Toast.LENGTH_LONG).show();
        }
    }

    private boolean isTelephoneValid(String telephone) {
        return true;
    }

    public void getUserByEmail(String email) {
        uModule.getUserByEmail(email);
    }

    public void saveUser(User user) {
        dbControler.eraseUser();
        dbControler.insertUser(user);
    }

    public User getUserFromSQL() {
        return dbControler.getUser();
    }

    public void logOut() {
        dbControler.eraseUser();
    }
}
