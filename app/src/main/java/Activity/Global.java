package Activity;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import Model.Grade;
import Model.Group;
import Model.School;
import Model.Server;
import Model.Subject;
import Model.User;

/**
 * Created by DELL on 2019-12-23.
 */

public class Global extends Application {
    private List<Subject> subjectList = new ArrayList<>();
    private List<School> schoolList = new ArrayList<>();
    private List<Grade> gradeList = new ArrayList<>();
    private List<Server> serverList = new ArrayList<>();
    private User user;
    private Subject subject;
    private String rootURL = "http://192.168.8.114:8081/trivia365/api";
    private Group group;

    public Global() {}

    public User getUser() {return user;}

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Subject> getSubjectList() {return subjectList;}

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Subject getSubject(){ return this.subject; }

    public void setSubject(Subject subject){ this.subject = subject; }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public String getRootURL() {
        return rootURL;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
