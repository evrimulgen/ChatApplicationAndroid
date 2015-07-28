package my.chatapplication.View;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.Chat;
import my.chatapplication.Adapter.ChatListAdapter;
import my.chatapplication.DataHolder.User;
import my.chatapplication.Service.ChatService;
import my.chatapplication.R;
import my.chatapplication.Service.Utility;

public class ChatActivity extends ListActivity  implements ChatView{

    // TODO: change this to your own Firebase URL
    private static String FIREBASE_URL = "https://sngvsimplechatapp.firebaseio.com/Message";

    private User myUser;
    private User freindUser;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private Intent intent;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userController = new UserController(this , CLASSES.CHAT_ACTIVITY , this );

        myUser = userController.getUserFromSQL();
        freindUser = (User) getIntent().getExtras().getSerializable("user");
        setTitle("Chatting as " + freindUser.getName());

        showToastMessage(getUrl());

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat").child(getUrl());

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.chat_messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.chat_sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private String getUrl() {
        String url = "";

        if(myUser.getEmail().compareTo(freindUser.getEmail()) > 0)
            url = Utility.removeDot(myUser.getEmail()) + "_" + Utility.removeDot(freindUser.getEmail());
        else
            url = Utility.removeDot(freindUser.getEmail()) + "_" + Utility.removeDot(myUser.getEmail());

        return url;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_item, myUser.getName());
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    protected void onResume() {
        intent = new Intent(this, ChatService.class);
        this.stopService(intent);
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    protected void onDestroy() {
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
        intent = new Intent(this, ChatService.class);
        intent.putExtra("mail" , myUser.getEmail());
        startService(intent);
        super.onDestroy();
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.chat_messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, myUser.getName());
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }

    private void showToastMessage(String s) {
        Toast.makeText(this , s , Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
