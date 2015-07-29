package my.chatapplication.DataHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nasser on 21/07/15.
 */
public class User implements Serializable{
    public static String EMAIL = "EMAIL";
    public static String PASSWORD = "PASSWORD";
    public static String TELEPHONE = "TELEPHONE";
    public static String NAME = "NAME";


    private String email;
    private String password;
    private String telephone;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String email, String password, String telephone, String name) {
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.name = name;
    }

    public User(String email){
        password = "";
        telephone = "";
        name = "";
        this.email = email;
    }

    public User() {
    }

    public User(String email , String name){
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Map<String, Object> convertToHashMap(){
        Map<String,Object> mp = new HashMap<String,Object>();
        mp.put(NAME ,name );
        mp.put(TELEPHONE , telephone);
        mp.put(PASSWORD , password);
        mp.put(EMAIL , email);
        return mp;
    }

    public void setData(Object data) {
        Map<String,String> user = (Map<String, String>) data;
        setTelephone(user.get("telephone"));
        setPassword(user.get("password"));
        setName(user.get("name"));
    }
}
