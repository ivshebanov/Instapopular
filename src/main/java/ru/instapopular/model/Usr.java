package ru.instapopular.model;

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
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active;

    private String email;

    private String usrname;

    private String password;

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "inst_password")
    private String instPassword;

    @Column(name = "do_not_unsubscribe")
    private Integer doNotUnsubscribe;

    @OneToMany(mappedBy = "idUsr", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "usr_role", joinColumns = @JoinColumn(name = "id_usr"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
