package ru.instapopular.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Scope(value = "prototype")
@Table(name = "my_group")
public class MyGroup implements Serializable {

    public MyGroup() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    @JsonManagedReference
    private Usr usr;

    @NotBlank
    @JoinColumn(name = "my_group")
    private String myGroup;

    @JsonBackReference
    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }

    public String getMyGroup() {
        return myGroup;
    }

    public void setMyGroup(String myGroup) {
        this.myGroup = myGroup;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyGroup group = (MyGroup) o;
        return active == group.active &&
                Objects.equals(id, group.id) &&
                Objects.equals(usr, group.usr) &&
                Objects.equals(myGroup, group.myGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usr, myGroup, active);
    }

    @Override
    public String toString() {
        return "MyGroup{" +
                "id=" + id +
                ", usr=" + usr +
                ", myGroup='" + myGroup + '\'' +
                ", active=" + active +
                '}';
    }
}
