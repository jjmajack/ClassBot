package com.peuyanaga.classbot.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.peuyanaga.classbot.Adapter.ChatMessageAdapter;
import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.Model.Data;
import com.peuyanaga.classbot.Model.LevelHolder;
import com.peuyanaga.classbot.Model.Server;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.Topic;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.Model.Word;
import com.peuyanaga.classbot.R;
import com.peuyanaga.classbot.Service.AppService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.peuyanaga.classbot.Service.AppService.sendData;
import static com.peuyanaga.classbot.Service.AppService.serverResponse;
import static com.peuyanaga.classbot.Service.AppService.updateMessageBoard;

public class SpellingBeeQuiz extends AppCompatActivity implements View.OnClickListener{

    User person;
    LevelHolder level;
    ImageButton btnSend;
    Button btnRephrase, btnHint;
    TextView lblTopic, lblTimer, lblScore, lblActiveUsers;
    EditText txtAnswer;
    TextToSpeech textToSpeech;
    ListView messageBoard;
    CountDownTimer countDownTimer;
    List<ChatMessage> chatMessageList = new ArrayList<>();
    Subject subject;
    Topic topic = new Topic();
    String ipaddress = "192.168.8.114";
    ChatMessageAdapter chatMessageAdapter;
    int port = 12345;
    boolean connected, started = false;
    Socket socket;
    int score = 0;
    Gson gson = new Gson();

    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spellingbee_quiz);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        person = getIntent().getParcelableExtra("person");

        Global global = (Global)getApplicationContext();
        topic.setTopicId(1);
        for(Server server : global.getServerList()){
            if (server.getName().equalsIgnoreCase("spelling_challenge")){
                ipaddress = server.getIpAddress();
                port = server.getPort();
                break;
            }
        }
        txtAnswer = (EditText)findViewById(R.id.answer);
        lblTopic = (TextView)findViewById(R.id.lblTopic);
        lblTimer = (TextView)findViewById(R.id.lblTimer);
        lblScore = (TextView)findViewById(R.id.lblScore);
        lblActiveUsers = (TextView)findViewById(R.id.lblActiveUsers);

        lblTopic.setText("Spelling Challenge");

        btnSend = (ImageButton)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnRephrase = (Button)findViewById(R.id.btn_rephrase);
        btnRephrase.setOnClickListener(this);
        btnHint = (Button)findViewById(R.id.btn_hint);
        btnHint.setOnClickListener(this);

        messageBoard = (ListView)findViewById(R.id.messageBoard);
        chatMessageAdapter = new ChatMessageAdapter(this, chatMessageList);
        messageBoard.setAdapter(chatMessageAdapter);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);

                   //textToSpeech.setPitch(1.1f);
                    if(Build.VERSION.SDK_INT >= 21){
                        List<Voice> ls = new ArrayList<>();
                        for(Voice v : textToSpeech.getVoices()){ if(v.getName().contains("en-us")){ ls.add(v); } }

                        if(ls.size() > 7) { textToSpeech.setVoice(ls.get(6)); }
                    }
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
                                data = gson.fromJson(jsonString, Data.class);

                                score = serverResponse(data, person, lblScore, lblActiveUsers, lblTimer, countDownTimer, textToSpeech, score, started, chatMessageList);
                                updateMessageBoard(chatMessageAdapter, messageBoard);
                                started = true;
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
    public void onClick(View view) {
        if(socket != null) {
            if(view == btnSend) {
                if (!AppService.answerCorrect) {
                    if (!txtAnswer.getText().toString().isEmpty()) {

                        data.setUser(person);
                        data.setAnswer(txtAnswer.getText().toString().trim());

                        sendData(gson.toJson(data).toString(), socket);
                        txtAnswer.setText("");
                    }
                } else {
                    txtAnswer.setText("");
                    chatMessageList.add(new ChatMessage("You got the current word right, wait for the next word", "Note", "", 0, true));
                    updateMessageBoard(chatMessageAdapter, messageBoard);
                }
            }
        }
        if(view == btnRephrase)
        {
            Word word = data.getCurrentWord();
            textToSpeech.speak(word != null? word.getWord(): "", TextToSpeech.QUEUE_FLUSH, null);
        }
        if(view == btnHint)
        {
            Word word = data.getCurrentWord();
            if(word != null) {
                String type = word.getType();
                String p = type.isEmpty()? "" : type.substring(0, 1).equalsIgnoreCase("a")? "a" : "an";
                String hint = String.format("%s is %s %s. %s", word.getWord(), p, word.getType(), word.getDescription());
                textToSpeech.speak(hint.isEmpty()? "There's no hint for this word" : hint, TextToSpeech.QUEUE_FLUSH, null);
            } else {
                textToSpeech.speak("There's no hint for this word", TextToSpeech.QUEUE_FLUSH, null);
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
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        finish();
    }
}
