package my.chatapplication.Navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import my.chatapplication.Controller.UserController;
import my.chatapplication.DataHolder.User;
import my.chatapplication.View.ChatActivity;
import my.chatapplication.View.Login;
import my.chatapplication.View.SignUpEmailAndPassowrd;
import my.chatapplication.View.UserProfile;
import my.chatapplication.R;


/**
 * Created by nasser on 16/07/15.
 */
public class NavigationBar extends MaterialNavigationDrawer {
    private UserController userController;
    private View homeFormView;
    private View progressView;
    private User user;
    private Button logout;
    private Button chat;
    private Button profile;
    private Button findFreind;

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

            homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            homeFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            homeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

        @Override
        public void init(Bundle savedInstanceState) {

            // create and set the header
            View view = LayoutInflater.from(this).inflate(R.layout.custom_drawer,null);
            setDrawerHeaderCustom(view);

            this.addSection(newSection("Home", new FragmentIndex()));
            this.addSection(newSection("Chat", new Intent(this, ChatActivity.class)));
            this.addSection(newSection("Login", new Intent(this, Login.class)));
            this.addSection(newSection("SignUp", new Intent(this, SignUpEmailAndPassowrd.class)));
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("email" , "ahmed@ahmed.com");
            this.addSection(newSection("Profile", intent));

            //this.addBottomSection(newSection("Bottom Section",R.drawable.ic_settings_black_24dp,new Intent(this,Settings.class)));

        }

}
