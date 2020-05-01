package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2019-12-21.
 */

public class FromJson {
    private String status;
    private User user;
    private List<Subject> subjectList = new ArrayList<>();
    private List<School> schoolList = new ArrayList<>();
    private List<Grade> gradeList = new ArrayList<>();
    private List<Server> serverList = new ArrayList<>();

    public List<School> getSchoolList() {return schoolList;}

    public void setSchoolList(List<School> schoolList) {this.schoolList = schoolList;}

    public List<Grade> getGradeList() {return gradeList;}

    public void setGradeList(List<Grade> gradeList) {this.gradeList = gradeList;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Subject> setTopicList() { return subjectList; }

    public void setSubjectList(List<Subject> subjectList) { this.subjectList = subjectList;}

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }
}
