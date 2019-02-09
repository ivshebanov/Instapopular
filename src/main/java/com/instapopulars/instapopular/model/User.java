package com.instapopulars.instapopular.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {
    private String name;                //имя акаунта
    private String linkAccount;         //сслыка на акаунт
    private boolean isOpen;                //аккаунт открыт?
    private boolean areWeSubscribed;       //мы подписаны?
    private boolean areTheySubscribed;     //они подписаны?
    private int amountLikes;            //сколько лайков я поствил
    private Date dateFirstAppeal;       //дата первого обращения
    private Date dateLastAppeal;        //дата последнего обращения
    private int numberSubscribers;      //колличество подписчиков
    private int numberSubscriptions;    //колличество подписок
    private int numberPublications;     //колличество публикаций
    private List<Photo> photos;         //список фото

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

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean getAreWeSubscribed() {
        return areWeSubscribed;
    }

    public void setAreWeSubscribed(boolean areWeSubscribed) {
        this.areWeSubscribed = areWeSubscribed;
    }

    public boolean getAreTheySubscribed() {
        return areTheySubscribed;
    }

    public void setAreTheySubscribed(boolean areTheySubscribed) {
        this.areTheySubscribed = areTheySubscribed;
    }

    public int getAmountLikes() {
        return amountLikes;
    }

    public void setAmountLikes(int amountLikes) {
        this.amountLikes = amountLikes;
    }

    public Date getDateFirstAppeal() {
        return dateFirstAppeal;
    }

    public void setDateFirstAppeal(Date dateFirstAppeal) {
        this.dateFirstAppeal = dateFirstAppeal;
    }

    public Date getDateLastAppeal() {
        return dateLastAppeal;
    }

    public void setDateLastAppeal(Date dateLastAppeal) {
        this.dateLastAppeal = dateLastAppeal;
    }

    public int getNumberSubscribers() {
        return numberSubscribers;
    }

    public void setNumberSubscribers(int numberSubscribers) {
        this.numberSubscribers = numberSubscribers;
    }

    public int getNumberSubscriptions() {
        return numberSubscriptions;
    }

    public void setNumberSubscriptions(int numberSubscriptions) {
        this.numberSubscriptions = numberSubscriptions;
    }

    public int getNumberPublications() {
        return numberPublications;
    }

    public void setNumberPublications(int numberPublications) {
        this.numberPublications = numberPublications;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return amountLikes == user.amountLikes &&
                numberSubscribers == user.numberSubscribers &&
                numberSubscriptions == user.numberSubscriptions &&
                numberPublications == user.numberPublications &&
                Objects.equals(name, user.name) &&
                Objects.equals(linkAccount, user.linkAccount) &&
                isOpen == user.isOpen &&
                areWeSubscribed == user.areWeSubscribed &&
                areTheySubscribed == user.areTheySubscribed &&
                Objects.equals(dateFirstAppeal, user.dateFirstAppeal) &&
                Objects.equals(dateLastAppeal, user.dateLastAppeal) &&
                Objects.equals(photos, user.photos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, linkAccount, isOpen, areWeSubscribed, areTheySubscribed, amountLikes, dateFirstAppeal, dateLastAppeal, numberSubscribers, numberSubscriptions, numberPublications, photos);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", linkAccount='" + linkAccount + '\'' +
                ", isOpen=" + isOpen +
                ", areWeSubscribed=" + areWeSubscribed +
                ", areTheySubscribed=" + areTheySubscribed +
                ", amountLikes=" + amountLikes +
                ", dateFirstAppeal=" + dateFirstAppeal +
                ", dateLastAppeal=" + dateLastAppeal +
                ", numberSubscribers=" + numberSubscribers +
                ", numberSubscriptions=" + numberSubscriptions +
                ", numberPublications=" + numberPublications +
                ", photos=" + photos +
                '}';
    }
}
