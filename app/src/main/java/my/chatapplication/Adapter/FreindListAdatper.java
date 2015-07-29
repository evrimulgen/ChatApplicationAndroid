package my.chatapplication.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

import java.util.Map;

import my.chatapplication.Adapter.BaseAdapter.FreindListBaseAdapter;
import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.LastMessage;
import my.chatapplication.DataHolder.NotificationDomain;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;
import my.chatapplication.Service.Utility;
import my.chatapplication.View.ChatActivity;
import my.chatapplication.View.ChatView;
import my.chatapplication.View.FreindList;

/**
 * Created by nasser on 29/07/15.
 */
public class FreindListAdatper extends FreindListBaseAdapter  implements ChatView {

    private User myUser;
    private Activity activity;
    private UserController userController;
    private FreindList freindList;

    /**
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     * @param myUser
     */
    public FreindListAdatper(  int mLayout ,  Activity activity , User myUser , FreindList freindList) {
        super(mLayout, activity, myUser , freindList);
        this.myUser = myUser;
        this.activity = activity;
        System.out.println("listen service :: done initliaze base adapter and user controller");
        userController = new UserController(this , CLASSES.FREIND_LIST_ADAPTER , activity.getApplicationContext());
        this.freindList = freindList;
    }

    @Override
    protected void populateView(View v, final LastMessage model) {
        // showToastMessage(model.toString() , activity);
        ((TextView)v.findViewById(R.id.lastMessage_name)).setText(model.getName());
        ((TextView)v.findViewById(R.id.lastMessage_message)).setText(model.getMessage());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userController.getUserByEmail(model.getEmail());
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        Intent intent = new Intent(activity , ChatActivity.class);
        Map<String ,Object> mp = (Map<String, Object>) msg.obj;
        String email = (String) mp.get("email");
        User user = new User(email);
        user.setData(msg.obj);
        intent.putExtra(Utility.FREIND_USER, user);
        activity.startActivity(intent);
    }
}
