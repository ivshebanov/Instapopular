package com.instapopulars.instapopular.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
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
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
