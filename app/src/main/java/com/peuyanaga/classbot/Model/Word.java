package com.peuyanaga.classbot.Model;

/**
 * Created by DELL on 2020-05-16.
 */

public class Word {
    private String word;
    private String hint;
    private String description;
    private String type;
    private int weight;
    private int gradeId;

    public Word(){}

    public Word(String word, String hint, String description, int weight, int gradeId) {
        this.word = word;
        this.hint = hint;
        this.description = description;
        this.weight = weight;
        this.gradeId = gradeId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
