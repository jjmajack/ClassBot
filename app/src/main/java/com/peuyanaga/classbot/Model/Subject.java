package com.peuyanaga.classbot.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2/18/2018.
 */
public class Subject implements Parcelable{

    private int order;
    private int subjectId;
    private int gradeSubjectId;
    private String description;
    private String subjectName;
    private List<Topic> topicList = new ArrayList<>();

    public Subject(){}
    public Subject(int order, int subjectId, int gradeSubjectId, String description, String subjectName){
        this.order = order;
        this.subjectId = subjectId;
        this.gradeSubjectId = gradeSubjectId;
        this.description = description;
        this.subjectName = subjectName;
    }

    protected Subject(Parcel parcel) {
        this.order = parcel.readInt();
        this.subjectId = parcel.readInt();
        this.gradeSubjectId = parcel.readInt();
        this.description = parcel.readString();
        this.subjectName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(order);
        parcel.writeInt(subjectId);
        parcel.writeInt(gradeSubjectId);
        parcel.writeString(description);
        parcel.writeString(subjectName);
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setGradeSubjectId(int gradeSubjectId){ this.gradeSubjectId = gradeSubjectId; }

    public int getGradeSubjectId(){ return gradeSubjectId; }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public String toString(){
        return subjectName;
    }
}
