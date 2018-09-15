package com.mercy.idictionary.Model;

public class MainModel {
    private String title;
    private String contents;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {

        return title;
    }

    public String getContents() {
        return contents;
    }
    public MainModel() {

    }

    public MainModel(String title, String contents) {

        this.title = title;
        this.contents = contents;
    }
}
