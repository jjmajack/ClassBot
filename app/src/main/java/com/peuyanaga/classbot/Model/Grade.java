package com.peuyanaga.classbot.Model;

/**
 * Created by DELL on 2020-01-25.
 */

public class Grade {
    private int gradeId;
    private String grade;

    public Grade(int gradeId, String grade) {
        this.gradeId = gradeId;
        this.grade = grade;
    }


    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString(){
        return grade;
    }

}
