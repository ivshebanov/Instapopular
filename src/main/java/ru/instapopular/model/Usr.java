package ru.instapopular.model;

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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
public class Usr implements UserDetails {

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
    private String password;

    @NotBlank
    @Column(name = "inst_name")
    private String instName;

    @NotBlank
    @Column(name = "inst_password")
    private String instPassword;

    @PositiveOrZero
    @Column(name = "do_not_unsubscribe")
    private Integer doNotUnsubscribe;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Photo> photos;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hashtag> hashtags;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MyGroup> groups;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usr_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    @Override
    public String getUsername() {
        return getUsrname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
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
                Objects.equals(likes, usr.likes) &&
                Objects.equals(hashtags, usr.hashtags) &&
                Objects.equals(groups, usr.groups) &&
                Objects.equals(role, usr.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, email, usrname, password, instName, instPassword, doNotUnsubscribe, photos, likes, hashtags, groups, role);
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
                ", likes=" + likes +
                ", hashtags=" + hashtags +
                ", groups=" + groups +
                ", role=" + role +
                '}';
    }
}
