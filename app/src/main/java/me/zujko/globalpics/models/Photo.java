package me.zujko.globalpics.models;

public class Photo {
    String id;

    String owner;

    String secret;

    String server;

    Integer farm;

    String title;

    Integer ispublic;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public Integer getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIspublic() {
        return ispublic;
    }
}
