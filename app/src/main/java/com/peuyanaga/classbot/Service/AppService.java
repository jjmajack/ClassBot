package com.peuyanaga.classbot.Service;

import android.content.Context;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.peuyanaga.classbot.Adapter.ChatMessageAdapter;
import com.peuyanaga.classbot.Model.ChatMessage;
import com.peuyanaga.classbot.Model.Data;
import com.peuyanaga.classbot.Model.Group;
import com.peuyanaga.classbot.Model.Subject;
import com.peuyanaga.classbot.Model.Topic;
import com.peuyanaga.classbot.Model.User;
import com.peuyanaga.classbot.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by DELL on 2020-05-03.
 */

public class AppService {

    public static boolean answerCorrect = false;
    public AppService() {
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.6f;
        wm.updateViewLayout(container, p);
    }

    public static String toUpperCaseWords(String text) {

        String words[] = text.split("\\s");
        String capitilizeWord = "";
        for (String w : words) {
            char[] ws = w.toCharArray();
            String firstChar = (ws.length > 0) ? w.substring(0, 1) : "";
            String restOfChars = (ws.length > 1) ? w.substring(1) : "";
            capitilizeWord += String.format("%s%s ", firstChar.toUpperCase(), restOfChars.toLowerCase());
        }
        return capitilizeWord.trim();
    }

    public static void sendData(String message, Socket socket) {
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

    public static void updateMessageBoard(ChatMessageAdapter chatMessageAdapter, final ListView messageBoard) {
        messageBoard.invalidateViews();
        messageBoard.post(new Runnable() {
            @Override
            public void run() {
                messageBoard.setSelection(messageBoard.getCount() - 1);
            }
        });
        chatMessageAdapter.notifyDataSetChanged();
    }

    public static int serverResponse(Data data, User person, TextView lblTopic, TextView questionView, Subject subject, int score, Group group, List<ChatMessage> chatMessageList) {
        if (data.getUser() != null) {
            String display = data.getUser().getUsername().equalsIgnoreCase(person.getUsername()) ? "You" : data.getUser().getUsername();
            if (data.getColor().equalsIgnoreCase("green")) {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, String.format(" +%d pts", data.getWeight()), R.drawable.correct_icon_13, false));
                if(data.getUser().getUserId() == person.getUserId()) score += data.getWeight();
                lblTopic.setText(subject.getSubjectName() + " - " + group.getGroupName() + " : " + score + "pts");
            } else {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, "", R.drawable.incorrect_icon_21, false));
            }
        }
        if (data.getStatusCode() == 100) {
            chatMessageList.add(new ChatMessage(data.getQuestion(), "Question", "", 0, true));
            questionView.setText(data.getQuestion());
        } else if (data.getStatusCode() == 303) {
            chatMessageList.add(new ChatMessage("", data.getQuestion(), "", 0, true));
        }
        return score;
    }

    public static int serverResponse(Data data, User person, TextView lblTopic, TextView questionView, Subject subject, int score, Topic topic, List<ChatMessage> chatMessageList) {
        if (data.getUser() != null) {
            String display = data.getUser().getUsername().equalsIgnoreCase(person.getUsername()) ? "You" : data.getUser().getUsername();
            if (data.getColor().equalsIgnoreCase("green")) {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, String.format(" +%d pts", data.getWeight()) , R.drawable.correct_icon_13, false));
                if(data.getUser().getUserId() == person.getUserId()) score += data.getWeight();
                lblTopic.setText(subject.getSubjectName() + " - " + topic.getTopic() + " : " + score + "pts");
            } else {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, "", R.drawable.incorrect_icon_21, false));
            }
        }
        if (data.getStatusCode() == 100) {
            chatMessageList.add(new ChatMessage(data.getQuestion(), "Question", "", 0, true));
            questionView.setText(data.getQuestion());
        } else if (data.getStatusCode() == 303) {
            chatMessageList.add(new ChatMessage("", data.getQuestion(), "", 0, true));
        }
        return score;
    }

    public static int serverResponse(Data data, User person, TextView lblScore, TextView lblActiveUsers, final TextView lblTimer, CountDownTimer countDownTimer, TextToSpeech textToSpeech, int score, boolean started, List<ChatMessage> chatMessageList) {

        lblActiveUsers.setText(data.getActiveUsers() + "");
        if (data.getUser() != null) {
            String display = data.getUser().getUsername().equalsIgnoreCase(person.getUsername()) ? "You" : data.getUser().getUsername();
            if (data.getColor().equalsIgnoreCase("green")) {
                chatMessageList.add(new ChatMessage("", display, String.format(" +%d pts", data.getWeight()) , R.drawable.correct_icon_13, false));
                if(data.getUser().getUserId() == person.getUserId()){
                    score += data.getWeight();
                    answerCorrect = true;
                }
                lblScore.setText(score + "");
            } else {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, "", R.drawable.incorrect_icon_21, false));
            }
        }
        if (data.getStatusCode() == 100) {
            if(!started)chatMessageList.add(new ChatMessage("Press rephrase to listen to the word again or press Hint for definition of the word", "Note", "", 0, true));
            else chatMessageList.add(new ChatMessage(data.getAnswer(), "Answer", "", 0, true));
            textToSpeech.speak("Spell " + data.getCurrentWord().getWord(), TextToSpeech.QUEUE_FLUSH, null);
            answerCorrect = false;

            countDownTimer = new CountDownTimer(data.getTimer() * 1000, 1000){

                @Override
                public void onTick(long l) {
                    lblTimer.setText(String.format("%ds", l/1000));
                }

                @Override
                public void onFinish() {

                }
            };
            countDownTimer.start();
        } else if (data.getStatusCode() == 303) {
            chatMessageList.add(new ChatMessage("", data.getQuestion(), "", 0, true));
        }
        return score;
    }

    public static boolean serverResponse(Data data, User person, TextView lblTopic, TextView questionView, Subject subject, int score, Topic topic, List<ChatMessage> chatMessageList, boolean answered) {
        if (data.getUser() != null) {
            String display = data.getUser().getUsername().equalsIgnoreCase(person.getUsername()) ? "You" : data.getUser().getUsername();
            if (data.getColor().equalsIgnoreCase("green")) {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, String.format(" +%d pts", data.getWeight()), R.drawable.correct_icon_13));
                if(data.getUser().getUserId() == person.getUserId()) score += data.getWeight();
                lblTopic.setText(subject.getSubjectName() + " - " + topic.getTopic() + " : " + score + "pts");
            } else {
                chatMessageList.add(new ChatMessage(data.getAnswer(), display, "", R.drawable.incorrect_icon_21));
            }
        }
        if (data.getStatusCode() == 100) {
            chatMessageList.add(new ChatMessage(data.getQuestion(), "Question", "", 0));
            questionView.setText(data.getQuestion());
            answered = false;
        } else if (data.getStatusCode() == 303) {
            chatMessageList.add(new ChatMessage("", data.getQuestion(), "", 0));
        }
        return answered;
    }
}
