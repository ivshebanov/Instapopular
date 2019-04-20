package com.instapopulars.instapopular.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;

    private String login;

    private String password;

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "inst_password")
    private String instPassword;

    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL)
    private List<MyPhoto> myPhotos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstPassword() {
        return instPassword;
    }

    public void setInstPassword(String instPassword) {
        this.instPassword = instPassword;
    }

    public List<MyPhoto> getMyPhotos() {
        return myPhotos;
    }

    public void setMyPhotos(List<MyPhoto> myPhotos) {
        this.myPhotos = myPhotos;
    }
}
