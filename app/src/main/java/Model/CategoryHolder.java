package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jackson on 11/14/2017.
 */

public class CategoryHolder implements Parcelable{
    private int categoryId;
    private String categoryName;
    private String description;

    public CategoryHolder(int categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    public CategoryHolder(Parcel in){
        this.categoryId = in.readInt();
        this.categoryName = in.readString();
        this.description = in.readString();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryId);
        parcel.writeString(categoryName);
        parcel.writeString(description);
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<CategoryHolder> CREATOR = new Creator<CategoryHolder>() {

        @Override
        public CategoryHolder createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new CategoryHolder(source);
        }

        @Override
        public CategoryHolder[] newArray(int size) {
            // TODO Auto-generated method stub
            return new CategoryHolder[size];
        }

    };
}
