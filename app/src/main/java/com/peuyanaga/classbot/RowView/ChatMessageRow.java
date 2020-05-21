package com.peuyanaga.classbot.RowView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.R;

/**
 * Created by DELL on 2020-05-10.
 */

public class ChatMessageRow {
    private TextView lblMessage = null;
    private TextView lblUser = null;
    //private TextView lblTime = null;
    //private TextView lblMarks = null;
    //private ImageView icon = null;

    public ChatMessageRow(View row){
        lblMessage = (TextView)row.findViewById(R.id.messageText);
        lblUser = (TextView)row.findViewById(R.id.messageUser);
        //lblTime = (TextView)row.findViewById(R.id.messageTime);
        //lblMarks = (TextView)row.findViewById(R.id.lblMarks);
       // icon = (ImageView)row.findViewById(R.id.imgMarkIcon);
    }

    public void populateFrom(ChatMessage message, Context context){

        lblUser.setText(message.getMessageUser());

        if(message.isQuestion()){
            lblMessage.setText(message.getMessageText());
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            String answer = message.getMessageText() + "   p";
            ImageSpan is = new ImageSpan(context, message.getIcon());
            Drawable d = is.getDrawable();
            int size = (int)(15 * scale + 0.3f);
            d.setBounds(0, 0, size, size);
            is = new ImageSpan(d);
            int start = answer.length() - 1;
            int end = answer.length();
            SpannableStringBuilder ssb = new SpannableStringBuilder(answer + " " + message.getMarks());
            ssb.setSpan(is, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            lblMessage.setText(ssb, TextView.BufferType.SPANNABLE);
        }

        //lblTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getMessageTime()));
        //icon.setImageResource(message.getIcon());
    }
}