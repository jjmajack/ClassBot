package com.peuyanaga.classbot.Model;

/**
 * Created by DELL on 2020-05-02.
 */

public class Answer {
    private int answerId;
    private String answer;
    private int correct;
    private int questionId;
    private int counter = 0;

    public Answer() {
    }

    public Answer(int answerId, String answer, int correct, int questionId) {
        this.answerId = answerId;
        this.answer = answer;
        this.correct = correct;
        this.questionId = questionId;
    }
    public int getCounter() { return counter; }

    public void setCounter(int counter) { this.counter = counter; }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
