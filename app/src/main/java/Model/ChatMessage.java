package Model;

import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Admin on 2018/07/30.
 */

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private int icon;

    public ChatMessage(String messageText, String messageUser, int icon) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.icon = icon;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
