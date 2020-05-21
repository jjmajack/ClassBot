package com.peuyanaga.classbot.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.peuyanaga.classbot.Adapter.ChatMessageAdapter;
import com.peuyanaga.classbot.R;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.Model.Data;
import com.peuyanaga.classbot.Model.Group;
import com.peuyanaga.classbot.Model.LevelHolder;
import com.peuyanaga.classbot.Model.Server;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.User;

import static com.peuyanaga.classbot.Service.AppService.sendData;
import static com.peuyanaga.classbot.Service.AppService.serverResponse;
import static com.peuyanaga.classbot.Service.AppService.updateMessageBoard;

public class GroupQuiz extends AppCompatActivity implements View.OnClickListener{

    User person;
    LevelHolder level;
    ImageButton btnSend;
    TextView lblTopic, questionView;
    EditText txtAnswer;
    ListView messageBoard;

    List<ChatMessage> chatMessageList = new ArrayList<>();
    Subject subject;
    Group group;
    String ipaddress = "";
    int port = 0;
    boolean connected;
    Socket socket;
    int score = 0;
    Gson gson = new Gson();
    ChatMessageAdapter chatMessageAdapter;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        person = getIntent().getParcelableExtra("person");
        subject = getIntent().getParcelableExtra("subject");

        Global global = (Global)getApplicationContext();
        group = global.getGroup();

        for(Server server : global.getServerList()){
            if (server.getName().equalsIgnoreCase("group_one_word")){
                ipaddress = server.getIpAddress();
                port = server.getPort();
                break;
            }
        }
        setTitle(subject.getSubjectName() + " - " + group.getGroupName());
        data.setGroupId(group.getGroupId());
        data.setSubjectId(subject.getSubjectId());
        data.setGradeSubjectId(subject.getGradeSubjectId());

        txtAnswer = (EditText)findViewById(R.id.answer);
        lblTopic = (TextView)findViewById(R.id.lblTopic);
        questionView = (TextView)findViewById(R.id.question_view);

        lblTopic.setText(subject.getSubjectName() + " - " + group.getGroupName() + " : " + score + "xp");

        btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        messageBoard = (ListView)findViewById(R.id.messageBoard);
        chatMessageAdapter = new ChatMessageAdapter(this, chatMessageList);
        messageBoard.setAdapter(chatMessageAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ipaddress, port), 3000);

                    sendData(gson.toJson(data).toString(), socket);

                    InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    String line;
                    while((line = bufferedReader.readLine()) != null)
                    {
                        final String jsonString = line;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                data = new Data();
                                data = gson.fromJson(jsonString, Data.class);

                                score =  serverResponse(data, person, lblTopic, questionView, subject, score, group, chatMessageList);
                                updateMessageBoard(chatMessageAdapter, messageBoard);
                            }
                        });
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatMessageList.add(new ChatMessage("", "Error: Connection to the server is not available, please try again later", "", 0, true));
                            updateMessageBoard(chatMessageAdapter, messageBoard);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if(view == btnSend)
        {
            if(socket != null) {
                if(!txtAnswer.getText().toString().isEmpty()) {

                    data.setUser(person);
                    data.setGroupId(group.getGroupId());
                    data.setSubjectId(subject.getSubjectId());
                    data.setGradeSubjectId(subject.getGradeSubjectId());
                    data.setAnswer(txtAnswer.getText().toString().trim());

                    sendData(gson.toJson(data).toString(), socket);
                    txtAnswer.setText("");
                }
            }
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(socket != null) {
            sendData("EXIT", socket);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}
