package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2020-04-30.
 */

public class Group {
    private int groupId;
    private String groupName;
    private User user;
    private List<GroupMember> members = new ArrayList<>();

    public Group(){}
    public Group(int groupId, String groupName, User user, List<GroupMember> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.user = user;
        this.members = members;
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

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }
}
