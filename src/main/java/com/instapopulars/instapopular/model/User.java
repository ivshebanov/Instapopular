package com.instapopulars.instapopular.model;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    private String email;

    private String username;

    private String password;

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "inst_password")
    private String instPassword;

    @Column(name = "do_not_unsubscribe")
    private Integer doNotUnsubscribe;

    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL)
    private List<MyPhoto> myPhotos;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getDoNotUnsubscribe() {
        return doNotUnsubscribe;
    }

    public void setDoNotUnsubscribe(Integer doNotUnsubscribe) {
        this.doNotUnsubscribe = doNotUnsubscribe;
    }

    public List<MyPhoto> getMyPhotos() {
        return myPhotos;
    }

    public void setMyPhotos(List<MyPhoto> myPhotos) {
        this.myPhotos = myPhotos;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
