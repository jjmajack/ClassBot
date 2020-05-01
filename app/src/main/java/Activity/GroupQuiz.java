package Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import Model.ChatMessage;
import Model.Data;
import Model.Group;
import Model.LevelHolder;
import Model.Server;
import Model.Subject;
import Model.User;

public class GroupQuiz extends AppCompatActivity implements View.OnClickListener{

    User person;
    LevelHolder level;
    ImageButton btnSend;
    TextView lblTopic;
    EditText txtAnswer;
    ListView messageBoard;

    ArrayList<ChatMessage> chatMessage = new ArrayList<>();
    Subject subject;
    Group group;
    String ipaddress = "192.168.8.114";
    int port = 12346;
    boolean connected;
    Socket socket;
    int score = 0;
    Gson gson = new Gson();

    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        person = getIntent().getParcelableExtra("person");
        subject = getIntent().getParcelableExtra("subject");

        Global global = (Global)getApplicationContext();
        group = global.getGroup();

        for(Server server : global.getServerList()){
            if (server.getName().equalsIgnoreCase("group")){
                ipaddress = server.getIpAddress();
                port = server.getPort();
                break;
            }
        }
        setTitle(subject.getSubjectName() + " - " + group.getGroupName());
        data.setGroupId(group.getGroupId());

        txtAnswer = (EditText)findViewById(R.id.answer);
        lblTopic = (TextView)findViewById(R.id.lblTopic);

        lblTopic.setText(subject.getSubjectName() + " - " + group.getGroupName() + " : " + score + "xp");

        btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        messageBoard = (ListView)findViewById(R.id.messageBoard);
        messageBoard.setAdapter(new ChatMessageAdapter());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ipaddress, port), 3000);

                    sendData(gson.toJson(data).toString());

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

                                if(data.getUser() != null) {

                                    String display = data.getUser().getUsername().equalsIgnoreCase(person.getUsername())? "You" : data.getUser().getUsername();
                                    if(data.getColor().equalsIgnoreCase("green")) {
                                        chatMessage.add(new ChatMessage(data.getAnswer() + " +" + data.getWeight(), display, R.drawable.correct_icon_13));
                                        score += data.getWeight();
                                        lblTopic.setText(subject.getSubjectName() + " - " + group.getGroupName() + " : " + score + "xp");
                                    } else {
                                        chatMessage.add(new ChatMessage(data.getAnswer(), display, R.drawable.incorrect_icon_21));
                                    }
                                }
                                if(data.getStatusCode() == 100) {
                                    chatMessage.add(new ChatMessage(data.getQuestion(), "Question", 0));
                                } else if (data.getStatusCode() == 303) {
                                    chatMessage.add(new ChatMessage("", data.getQuestion(), 0));
                                }
                                messageBoard.invalidateViews();
                                messageBoard.post(new Runnable(){
                                    public void run() {
                                        messageBoard.setSelection(messageBoard.getCount() - 1);
                                    }});
                            }
                        });
                    }

                }catch (Exception ex){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatMessage.add(new ChatMessage("", "Error: Connection to the server is not available, please try again later", 0));

                            messageBoard.invalidateViews();
                            messageBoard.post(new Runnable(){
                                @Override
                                public void run() {
                                    messageBoard.setSelection(messageBoard.getCount() - 1);
                                }});
                        }
                    });

                }
            }
        }).start();
    }

    public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

        public ChatMessageAdapter() {
            super(getApplicationContext(), R.layout.message, chatMessage);
        }

        public View getView(int pos, View convertView, ViewGroup parent)
        {
            View row = convertView;
            ChatMessageDataHolder chatMessageDataHolder;

            if(row == null){
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                row = inflater.inflate(R.layout.message, parent, false);
                chatMessageDataHolder = new ChatMessageDataHolder(row);
                row.setTag(chatMessageDataHolder);
            } else {
                chatMessageDataHolder = (ChatMessageDataHolder)row.getTag();
            }
            chatMessageDataHolder.populateFrom(chatMessage.get(pos));
            return row;
        }
    }

    public class ChatMessageDataHolder{
        private TextView lblMessage = null;
        private TextView lblUser = null;
        private TextView lblTime = null;
        private ImageView icon = null;

        public ChatMessageDataHolder(View row){
            lblMessage = (TextView)row.findViewById(R.id.messageText);
            lblUser = (TextView)row.findViewById(R.id.messageUser);
            lblTime = (TextView)row.findViewById(R.id.messageTime);
            icon = (ImageView)row.findViewById(R.id.imgMarkIcon);
        }

        public void populateFrom(ChatMessage message){
            lblMessage.setText(message.getMessageText() + "");
            lblUser.setText(message.getMessageUser() + "");
            lblTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                    message.getMessageTime()));
            icon.setImageResource(message.getIcon());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnSend)
        {
            if(!txtAnswer.getText().toString().isEmpty()) {

                data.setUser(person);
                data.setGroupId(group.getGroupId());
                data.setTopicId(subject.getSubjectId());
                data.setAnswer(txtAnswer.getText().toString().trim());

                sendData(gson.toJson(data).toString());
                txtAnswer.setText("");
            }
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        sendData("EXIT");
    }

    public void sendData(String message)    {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
