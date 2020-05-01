package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jackson on 2/18/2018.
 */
public class Topic implements Parcelable{
    private int order;
    private int topicId;
    private int gradeSubjectId;
    private String description;
    private String topic;

   public Topic(){}

    public Topic(int order, int topicId, int gradeSubjectId, String description, String topic) {
        this.order = order;
        this.topicId = topicId;
        this.gradeSubjectId = gradeSubjectId;
        this.description = description;
        this.topic = topic;
    }

    protected Topic(Parcel parcel) {
        this.order = parcel.readInt();
        this.topicId = parcel.readInt();
        this.gradeSubjectId = parcel.readInt();
        this.description = parcel.readString();
        this.topic = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(order);
        parcel.writeInt(topicId);
        parcel.writeInt(gradeSubjectId);
        parcel.writeString(description);
        parcel.writeString(topic);
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getGradeSubjectId() {
        return gradeSubjectId;
    }

    public void setGradeSubjectId(int gradeSubjectId) {
        this.gradeSubjectId = gradeSubjectId;
    }
}
