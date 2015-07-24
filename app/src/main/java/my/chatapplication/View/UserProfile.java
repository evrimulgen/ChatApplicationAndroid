package my.chatapplication.View;

import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.chatapplication.DataHolder.CLASSES;
import my.chatapplication.Controller.UserController;
import my.chatapplication.R;

public class UserProfile extends ActionBarActivity implements ChatView{
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userController = new UserController(this , CLASSES.USER_PROFILE , this);
        userController.getUser(getIntent().getExtras().getString("email"));
    }

    private void showToastMessage(String s) {
        Toast.makeText(this , s , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
        showToastMessage(msg.obj.toString());
    }
}
