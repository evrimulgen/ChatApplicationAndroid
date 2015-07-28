package my.chatapplication.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

import my.chatapplication.DataHolder.User;
import my.chatapplication.R;
import my.chatapplication.View.FindFreind;

/**
 * Created by nasser on 28/07/15.
 */
public class FindFreindAdapter extends FindFreindListAdapter<User> {
    String email;
    Query mRef;
    Activity activity;
    int mLayout;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public FindFreindAdapter(Query mRef, Activity activity, int mLayout, String email) {
        super(mRef, User.class , mLayout, activity , email);
        this.email = email;
        this.mRef = mRef;
        this.activity = activity;
        this.mLayout = mLayout;
    }

    public void setEmail(String email) {
        new FindFreindAdapter(mRef, activity, mLayout, email);
    }

    @Override
    protected void populateView(View v, User model) {
        ((TextView)v.findViewById(R.id.findFreind_item_name)).setText(model.getName());
    }
}
