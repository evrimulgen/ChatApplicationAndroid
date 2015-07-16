package firebase_login_demo_android.chatapplication.Navigation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import firebase_login_demo_android.chatapplication.R;

/**
 * Created by nasser on 16/07/15.
 */
public class NavigationAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<NavBarDomain> navList;

    public NavigationAdapter(Activity activity , List<NavBarDomain> navList){
        this.activity = activity;
        this.navList = navList;
    }

    @Override
    public int getCount() {
        return navList.size();
    }

    @Override
    public Object getItem(int postition) {
        return navList.get(postition);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        NavBarDomain nav = navList.get(position);

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.navigation_bar_item, null);

        ((TextView)view.findViewById(R.id.nav_text)).setText(nav.getNav_txt());
        ((ImageButton)view.findViewById(R.id.nav_img)).setImageResource(R.drawable.ic_launcher);

        return view;
    }
}
