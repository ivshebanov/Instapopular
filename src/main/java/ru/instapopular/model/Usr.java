package ru.instapopular.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
public class Usr implements UserDetails, Serializable {

    public Usr() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String usrname;

    @NotBlank
    @JsonBackReference
    private String password;

    @NotBlank
    @Column(name = "inst_name")
    private String instName;

    @NotBlank
    @Column(name = "inst_password")
    @JsonBackReference
    private String instPassword;

    @PositiveOrZero
    @Column(name = "do_not_unsubscribe")
    private Integer doNotUnsubscribe;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Photo> photos;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Guys> guys;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Hashtag> hashtags;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MyGroup> groups;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usr_id"))
    @Enumerated(EnumType.STRING)
    @JsonBackReference
    private Set<Roles> role;

    @Override
    @JsonBackReference
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    @Override
    @JsonBackReference
    public String getUsername() {
        return getUsrname();
    }

    @Override
    @JsonBackReference
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonBackReference
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonBackReference
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonBackReference
    public boolean isEnabled() {
        return isActive();
    }

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

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    @Override
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Guys> getGuys() {
        return guys;
    }

    public void setGuys(List<Guys> guys) {
        this.guys = guys;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<MyGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<MyGroup> groups) {
        this.groups = groups;
    }

    public Set<Roles> getRole() {
        return role;
    }

    public void setRole(Set<Roles> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usr usr = (Usr) o;
        return active == usr.active &&
                Objects.equals(id, usr.id) &&
                Objects.equals(email, usr.email) &&
                Objects.equals(usrname, usr.usrname) &&
                Objects.equals(password, usr.password) &&
                Objects.equals(instName, usr.instName) &&
                Objects.equals(instPassword, usr.instPassword) &&
                Objects.equals(doNotUnsubscribe, usr.doNotUnsubscribe) &&
                Objects.equals(photos, usr.photos) &&
                Objects.equals(guys, usr.guys) &&
                Objects.equals(hashtags, usr.hashtags) &&
                Objects.equals(groups, usr.groups) &&
                Objects.equals(role, usr.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, email, usrname, password, instName, instPassword, doNotUnsubscribe, photos, guys, hashtags, groups, role);
    }

    @Override
    public String toString() {
        return "Usr{" +
                "id=" + id +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", usrname='" + usrname + '\'' +
                ", password='" + password + '\'' +
                ", instName='" + instName + '\'' +
                ", instPassword='" + instPassword + '\'' +
                ", doNotUnsubscribe=" + doNotUnsubscribe +
                ", photos=" + photos +
                ", guys=" + guys +
                ", hashtags=" + hashtags +
                ", groups=" + groups +
                ", role=" + role +
                '}';
    }
}
