package com.peuyanaga.classbot.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jackson on 11/11/2017.
 */

public class LevelHolder implements Parcelable {

    private int id;
    private int count;
    private String title;
    private String subTitle;
    private int status;

    public LevelHolder(){}

    public LevelHolder(int id, int count, String title, String subTitle, int status) {
        this.id = id;
        this.count = count;
        this.title = title;
        this.subTitle = subTitle;
        this.status = status;
    }

    public LevelHolder(Parcel in){
        this.id = in.readInt();
        this.count = in.readInt();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.status = in.readInt();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String getSubTitle() {

        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(count);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeInt(status);
    }
    public static final Creator<LevelHolder> CREATOR = new Creator<LevelHolder>() {

        @Override
        public LevelHolder createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new LevelHolder(source);
        }

        @Override
        public LevelHolder[] newArray(int size) {
            // TODO Auto-generated method stub
            return new LevelHolder[size];
        }

    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
