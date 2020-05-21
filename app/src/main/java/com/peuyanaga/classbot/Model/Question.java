package com.peuyanaga.classbot.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2020-05-02.
 */

public class Question {
    private int questionId;
    private String question;
    private int weight;
    private String answer;
    private List<Answer> answerList = new ArrayList<>();

    public Question() {
    }

    public Question(int questionId, String question, int weight, String answer, List<Answer> answerList) {
        this.questionId = questionId;
        this.question = question;
        this.weight = weight;
        this.answer = answer;
        this.answerList = answerList;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
