package Model;

/**
 * Created by DELL on 2020-04-30.
 */

public class GroupMember {

    int groupMemberId;
    int userId;
    int groupId;
    User user;
    String role;
    String status;

    public GroupMember(){}
    public GroupMember(int groupMemberId, int userId, int groupId, User user, String role, String status) {
        this.groupMemberId = groupMemberId;
        this.userId = userId;
        this.groupId = groupId;
        this.user = user;
        this.role = role;
        this.status = status;
    }

    public int getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(int groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
