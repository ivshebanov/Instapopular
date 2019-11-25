package ru.instapopular.model;

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

@Data
@Entity
@Scope(value = "prototype")
@Table(name = "my_group")
public class MyGroup {

    public MyGroup() {
    }

    public MyGroup(Usr usr, @NotBlank String myGroup) {
        this.usr = usr;
        this.myGroup = myGroup;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private Usr usr;

    @NotBlank
    @JoinColumn(name = "my_group")
    private String myGroup;

    private boolean active;
}
