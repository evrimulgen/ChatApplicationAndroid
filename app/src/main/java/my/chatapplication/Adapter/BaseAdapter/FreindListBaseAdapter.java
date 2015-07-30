package my.chatapplication.Adapter.BaseAdapter;

import android.app.Activity;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.chatapplication.DataHolder.LastMessage;
import my.chatapplication.DataHolder.User;
import my.chatapplication.Service.Utility;
import my.chatapplication.View.FreindList;

/**
 * Created by nasser on 29/07/15.
 */
public abstract class FreindListBaseAdapter  extends BaseAdapter {


    private Firebase firebase = new Firebase("https://sngvsimplechatapp.firebaseio.com/Freind");

        private Query mRef;
        private int mLayout;
        private LayoutInflater mInflater;
        private List<LastMessage> mModels;
        private ChildEventListener mListener;
        private Activity activity;
        User myUser;

        /**
         * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
         *                    instance of the corresponding view with the data from an instance of mModelClass.
         * @param activity    The activity containing the ListView

         */
        public FreindListBaseAdapter( int mLayout, final Activity activity, User myUser , FreindList freindList) {
            cleanup();
            this.mLayout = mLayout;
            mInflater = activity.getLayoutInflater();
            mModels = new ArrayList<LastMessage>();
            this.activity = activity;
            this.myUser = myUser;
            initListner(freindList);
        }

        public void initListner(final FreindList freindList){
            cleanup();
            Query newmRef = firebase.child(Utility.removeDot(myUser.getEmail())).orderByChild("messageTime");
            System.out.println("listen service :: start " + Utility.removeDot(myUser.getEmail()));

            // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
            mListener = newmRef.addChildEventListener(new ChildEventListener() {
                    // Retrieve new posts as they are added to the database

                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                        freindList.handleMessage(new Message());
                        System.out.println("listen service :: " + snapshot.getValue());
                        Map<String , Object > mp = (Map<String, Object>) snapshot.getValue();
                        String name = (String) mp.get("name");
                        String message = (String) mp.get("message");
                        String email = (String) mp.get("email");
                        Timestamp messageTime = (Timestamp) mp.get("messageTime");
                        mModels.add(new LastMessage(name , message , email, messageTime)) ;

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {
                        freindList.handleMessage(new Message());

                        System.out.println("listen service :: " + dataSnapshot.getValue());
                        // One of the mModels changed. Replace it in our list and name mapping

                        Map<String , Object > mp = (Map<String, Object>) dataSnapshot.getValue();
                        String name = (String) mp.get("name");
                        String message = (String) mp.get("message");
                        String email = (String) mp.get("email");
                        Timestamp messageTime = (Timestamp) mp.get("messageTime");
                        LastMessage lastMessage = new LastMessage(name , message , email,messageTime);

                        for(int i = 0 ; i < mModels.size() ; i++){
                            if( mModels.get(i).equals(lastMessage)) {
                                mModels.get(i).setMessage(lastMessage.getMessage());
                                while (i != 0) {
                                    i = mModels.indexOf(lastMessage);
                                    Collections.swap(mModels , i , i-1);
                                }
                            }
                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        freindList.handleMessage(new Message());
                        Map<String , Object > mp = (Map<String, Object>) dataSnapshot.getValue();
                        String name = (String) mp.get("name");
                        String message = (String) mp.get("message");
                        String email = (String) mp.get("email");
                        Timestamp messageTime = (Timestamp) mp.get("messageTime");
                        LastMessage lastMessage = new LastMessage(name , message , email, messageTime);

                        for(int i = 0 ; i < mModels.size() ; i++){
                            if( mModels.get(i).equals(lastMessage)) {
                                mModels.get(i).setMessage(lastMessage.getMessage());
                                while (i != 0) {
                                    i = mModels.indexOf(lastMessage);
                                    Collections.swap(mModels , i , i-1);
                                }
                            }
                        }

                        for(LastMessage msg : mModels){
                            if(msg.equals(lastMessage)){
                                mModels.remove(msg);
                            }
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        freindList.handleMessage(new Message());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        freindList.handleMessage(new Message());
                    }


                });
        }



        public void showToastMessage(String s , Activity activity){
            Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        }

        public void cleanup() {

            // We're being destroyed, let go of our mListener and forget about all of the mModels
            if(mListener != null)
                mRef.removeEventListener(mListener);

            if(mModels != null)
                mModels.clear();


        }

        @Override
        public int getCount() {
            return mModels.size();
        }

        @Override
        public Object getItem(int i) {
            return mModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mInflater.inflate(mLayout, viewGroup, false);
            }

            LastMessage model = mModels.get(i);
            // Call out to subclass to marshall this model into the provided view
            populateView(view, model);
            return view;
        }

        /**
         * Each time the data at the given Firebase location changes, this method will be called for each item that needs
         * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
         * <p/>
         * Your implementation should populate the view using the data contained in the model.
         *
         * @param v     The view to populate
         * @param model The object containing the data used to populate the view
         */
        protected abstract void populateView(View v, LastMessage model);

}
