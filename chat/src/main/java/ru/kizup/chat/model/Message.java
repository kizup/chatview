package ru.kizup.chat.model;

/**
 * Created by kizup on 23.09.17.
 */

public class Message {

    public static final int SELF_MESSAGE = 0;
    public static final int ANOTHER_MESSAGE = 1;

    private long id;
    private String message;
    private String time;
    private User user;
    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
