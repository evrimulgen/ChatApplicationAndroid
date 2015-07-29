package my.chatapplication.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Query;

import my.chatapplication.Adapter.BaseAdapter.FindFreindBaseAdapter;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;
import my.chatapplication.Service.Utility;
import my.chatapplication.View.ChatActivity;
import my.chatapplication.View.ChatView;
import my.chatapplication.View.FindFreind;

/**
 * Created by nasser on 28/07/15.
 */
public class FindFreindAdapter extends FindFreindBaseAdapter<User>  implements ChatView{
    String email;
    Query mRef;
    Activity activity;
    int mLayout;
    FindFreind findFreind;

    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public FindFreindAdapter(Query mRef, Activity activity, int mLayout, String phoneNumber , FindFreind findFreind) {
        super(mRef, User.class , mLayout, activity);
        this.email = email;
        this.mRef = mRef;
        this.activity = activity;
        this.mLayout = mLayout;
        this.findFreind = findFreind;
    }

    public void startListner(String phone) {
        initListner(phone , this);
    }

    @Override
    protected void populateView(View v, final User model) {
        ((TextView)v.findViewById(R.id.findFreind_item_name)).setText(model.getName());
        ((Button)v.findViewById(R.id.findFreind_item_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity , ChatActivity.class);
                intent.putExtra(Utility.FREIND_USER, model);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        findFreind.handleMessage(msg);
    }
}
