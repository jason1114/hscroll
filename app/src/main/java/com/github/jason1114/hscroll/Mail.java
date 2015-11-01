package com.github.jason1114.hscroll;

/**
 * Inbox Demo
 * Created by baidu on 15/11/1.
 */
public class Mail {
    String sender;
    String title;
    String content;

    public Mail(String sender, String title, String content) {
        this.sender = sender;
        this.title = title;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
