package com.example.spendnee.model;

public class GroupMember {
    private int idGroupMember;
    private int idGroup;
    private String username;

    public GroupMember(int idGroupMember, int idGroup, String username) {
        this.idGroupMember = idGroupMember;
        this.idGroup = idGroup;
        this.username = username;

    }

    public int getIdGroupMember() {
        return idGroupMember;
    }

    public void setIdGroupMember(int idGroupMember) {
        this.idGroupMember = idGroupMember;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
