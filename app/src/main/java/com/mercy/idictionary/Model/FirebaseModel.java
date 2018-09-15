package com.mercy.idictionary.Model;

public class FirebaseModel {
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

    public FirebaseModel() {}

    public FirebaseModel(String titleFire, String contentsFire) {
        this.titleFire = titleFire;
        this.contentsFire = contentsFire;
    }
}
