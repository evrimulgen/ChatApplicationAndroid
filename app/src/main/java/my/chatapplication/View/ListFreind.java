package my.chatapplication.View;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.DataHolder.User;
import my.chatapplication.R;

public class ListFreind extends ActionBarActivity implements ChatView{

    private UserController userController;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_freind);
        userController = new UserController(this , CLASSES.LIST_FREIND , this);
        user = (User) getIntent().getExtras().getSerializable("user");
    }

    @Override
    protected void onStart() {
        super.onStart();
        userController.saveUser(user);
        showToastMessage(user.toString());
    }

    private void showToastMessage(String msg) {
        Toast.makeText(this , msg , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_freind, menu);
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

    @Override
    public void handleMessage(Message msg) {
    }
}
