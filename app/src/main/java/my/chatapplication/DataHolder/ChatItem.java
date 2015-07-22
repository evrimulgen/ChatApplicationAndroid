package my.chatapplication.DataHolder;

/**
 * Created by nasser on 16/07/15.
 */
public class ChatItem {
    String author;
    String msg;
    int img;

    public ChatItem(String msg, String author, int image) {
        this.author = author;
        this.msg = msg;
        this.img = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
