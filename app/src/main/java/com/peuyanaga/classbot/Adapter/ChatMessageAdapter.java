package com.peuyanaga.classbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.RowView.ChatMessageRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2020-05-10.
 */

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    Context context;
    List<ChatMessage> chatMessageList = new ArrayList<>();
    public ChatMessageAdapter(Context context, List<ChatMessage> chatMessageList) {
        super(context, R.layout.message, chatMessageList);
        this.context = context;
        this.chatMessageList = chatMessageList;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ChatMessageRow chatMessageRow;

        if(row == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.message, parent, false);
            chatMessageRow = new ChatMessageRow(row);
            row.setTag(chatMessageRow);
        } else {
            chatMessageRow = (ChatMessageRow)row.getTag();
        }
        chatMessageRow.populateFrom(chatMessageList.get(pos), context);
        return row;
    }
}
