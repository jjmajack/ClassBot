package com.peuyanaga.classbot.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2020-04-30.
 */

public class Group {
    private int counter;
    private int groupId;
    private String groupName;
    private User user;
    private Subject subject;
    private List<GroupMember> members = new ArrayList<>();

    public Group(){}
    public Group(int counter, int groupId, String groupName, User user, List<GroupMember> members) {
        this.counter = counter;
        this.groupId = groupId;
        this.groupName = groupName;
        this.user = user;
        this.members = members;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }
}
