package com.mercy.idictionary.models;

import java.io.Serializable;

public class Word implements Serializable {

    private String titleFire;
    private String contentsFire;

    public String getTitleFire() {
        return titleFire;
    }

    public void setTitleFire(String titleFire) {
        this.titleFire = titleFire;
    }

    public String getContentsFire() {
        return contentsFire;
    }

    public void setContentsFire(String contentsFire) {
        this.contentsFire = contentsFire;
    }

    public Word() {
    }

    public Word(String titleFire, String contentsFire) {
        this.titleFire = titleFire;
        this.contentsFire = contentsFire;
    }

    // end of file
}
