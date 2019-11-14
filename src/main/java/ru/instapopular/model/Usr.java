package ru.instapopular.model;

import lombok.Data;

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
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "usr")
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    @Email
    private String email;

    @NotBlank
    @Size(min=2, max=50)
    private String usrname;

    @NotBlank
    @Size(min=2, max=50)
    private String password;

    @Column(name = "inst_name")
    private String instName;

    @NotBlank
    @Size(min=2, max=50)
    @Column(name = "inst_password")
    private String instPassword;

    @Column(name = "do_not_unsubscribe")
    private Integer doNotUnsubscribe;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
    private List<Hashtag> hashtags;

    @OneToMany(mappedBy = "usr", cascade = CascadeType.ALL)
    private List<MyGroup> groups;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usr_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> role;
}
