package com.instapopulars.instapopular.model;

import java.util.Objects;

public class Photo {
    private String name;                //имя акаунта
    private String linkAccount;         //сслыка на акаунт
    private String linkPhoto;           //ссылка на фото
    private boolean isActiveSubscribed;       //мы подписаны?
    private boolean isActiveLike;             //нам нравится?

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkAccount() {
        return linkAccount;
    }

    public void setLinkAccount(String linkAccount) {
        this.linkAccount = linkAccount;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public boolean isActiveSubscribed() {
        return isActiveSubscribed;
    }

    public void setIsActiveSubscribed(boolean isActiveSubscribed) {
        this.isActiveSubscribed = isActiveSubscribed;
    }

    public boolean isActiveLike() {
        return isActiveLike;
    }

    public void setIsActiveLike(boolean isActiveLike) {
        this.isActiveLike = isActiveLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return isActiveSubscribed == photo.isActiveSubscribed &&
                isActiveLike == photo.isActiveLike &&
                Objects.equals(name, photo.name) &&
                Objects.equals(linkAccount, photo.linkAccount) &&
                Objects.equals(linkPhoto, photo.linkPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, linkAccount, linkPhoto, isActiveSubscribed, isActiveLike);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name='" + name + '\'' +
                ", linkAccount='" + linkAccount + '\'' +
                ", linkPhoto='" + linkPhoto + '\'' +
                ", isActiveSubscribed=" + isActiveSubscribed +
                ", isActiveLike=" + isActiveLike +
                '}';
    }
}
