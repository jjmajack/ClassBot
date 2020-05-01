package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jackson on 11/19/2017.
 */

public class Ranking implements Parcelable {

    private int resultId;
    private int position;
    private int userId;
    private User user;
    private int levelId;
    private int topicId;
    private int points;
    private int subjectId;
    private int groupId;
    private int respects;

    public Ranking(){}

    public Ranking(Parcel parcel){
        this.resultId = parcel.readInt();
        this.userId = parcel.readInt();
        this.levelId = parcel.readInt();
        this.topicId = parcel.readInt();
        this.points = parcel.readInt();
    }
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(resultId);
        parcel.writeInt(userId);
        parcel.writeInt(levelId);
        parcel.writeInt(topicId);
        parcel.writeInt(points);
    }

    public static final Creator<Ranking> CREATOR = new Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel parcel) {
            return new Ranking(parcel);
        }

        @Override
        public Ranking[] newArray(int i) {
            return new Ranking[i];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getRespects() {
        return respects;
    }

    public void setRespects(int respects) {
        this.respects = respects;
    }
}
