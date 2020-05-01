package Model;

/**
 * Created by DELL on 2020-01-25.
 */

public class School {
    private int schoolId;
    private String schoolName;
    private String address;

    public School(int schoolId, String schoolName, String address) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.address = address;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString(){
        return schoolName;
    }
}
