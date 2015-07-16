package my.chatapplication.Navigation;

/**
 * Created by nasser on 16/07/15.
 */
public class NavBarDomain {
    String img_name;
    String nav_txt;

    public NavBarDomain(String text , String imgSource) {
        nav_txt = text;
        img_name = imgSource;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getNav_txt() {
        return nav_txt;
    }

    public void setNav_txt(String nav_txt) {
        this.nav_txt = nav_txt;
    }
}
