package com.peuyanaga.classbot.Model;

import java.util.Date;

/**
 * Created by Admin on 2018/07/30.
 */

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private String marks;
    private boolean question;
    private long messageTime;
    private int icon;

    public ChatMessage(String messageText, String messageUser, String marks, int icon) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.marks = marks;
        this.icon = icon;
        this.question = true;
        // Initialize to current time
        messageTime = new Date().getTime();
    }
    public ChatMessage(String messageText, String messageUser, String marks, int icon, boolean question){
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.question = question;
        this.marks = marks;
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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
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
