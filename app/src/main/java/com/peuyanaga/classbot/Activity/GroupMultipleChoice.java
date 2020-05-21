package com.peuyanaga.classbot.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.peuyanaga.classbot.Adapter.AnswerAdapter;
import com.peuyanaga.classbot.Adapter.ChatMessageAdapter;
import com.peuyanaga.classbot.Model.Answer;
import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.Model.Data;
import com.peuyanaga.classbot.Model.Group;
import com.peuyanaga.classbot.Model.Question;
import com.peuyanaga.classbot.Model.Server;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.Topic;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.peuyanaga.classbot.Service.AppService.sendData;
import static com.peuyanaga.classbot.Service.AppService.serverResponse;
import static com.peuyanaga.classbot.Service.AppService.updateMessageBoard;

public class GroupMultipleChoice extends AppCompatActivity{

    String ipaddress = "";
    int port = 0;
    int score = 0;
    boolean connected;

    TextView lblTopic;
    TextView questionView;
    ListView messageBoard;
    ListView answerTray;

    ChatMessageAdapter chatMessageAdapter;
    AnswerAdapter answerAdapter;

    List<ChatMessage> chatMessageList = new ArrayList<>();
    List<Answer> answerList = new ArrayList<>();

    Data data = new Data();
    Gson gson = new Gson();
    User person = new User();
    Subject subject = new Subject();
    Group group = new Group();

    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice_activity_quiz);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        person = getIntent().getParcelableExtra("person");
        subject = getIntent().getParcelableExtra("subject");

        Global global = (Global)getApplicationContext();
        group = global.getGroup();

        for(Server server : global.getServerList()){
            if (server.getName().equalsIgnoreCase("group_multiple_choice")){
                ipaddress = server.getIpAddress();
                port = server.getPort();
                break;
            }
        }
        setTitle(subject.getSubjectName() + " - " + group.getGroupName());
        data.setGroupId(group.getGroupId());
        data.setSubjectId(subject.getSubjectId());
        data.setGradeSubjectId(subject.getGradeSubjectId());

        lblTopic = (TextView)findViewById(R.id.lblTopic);
        questionView = (TextView)findViewById(R.id.question_view);

        lblTopic.setText(subject.getSubjectName() + " - " + group.getGroupName() + " : " + score + "xp");

        messageBoard = (ListView)findViewById(R.id.messageBoard);
        chatMessageAdapter = new ChatMessageAdapter(this, chatMessageList);
        messageBoard.setAdapter(chatMessageAdapter);

        answerTray = (ListView)findViewById(R.id.list_answers);

        answerTray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Answer answer = answerList.get(i);
                data.setUser(person);
                data.setGroupId(group.getGroupId());
                data.setSubjectId(subject.getSubjectId());
                data.setGradeSubjectId(subject.getGradeSubjectId());
                data.setAnswer(answer.getAnswer() + "");
                if(socket != null) {
                    sendData(gson.toJson(data).toString(), socket);
                }
            }
        });

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
                                Question currentQuestion = data.getCurrentQuestion();
                                answerList = (currentQuestion != null)? data.getCurrentQuestion().getAnswerList() : new ArrayList<Answer>();

                                answerAdapter = new AnswerAdapter(getApplicationContext(), answerList);
                                answerTray.setAdapter(answerAdapter);

                                score = serverResponse(data, person, lblTopic, questionView, subject, score, group, chatMessageList);
                                updateMessageBoard(chatMessageAdapter, messageBoard);
                            }
                        });
                    }

                }catch (Exception ex){
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
