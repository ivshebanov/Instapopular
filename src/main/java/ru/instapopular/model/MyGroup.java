package ru.instapopular.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "my_group")
public class MyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    @NotBlank
    @JoinColumn(name = "my_group")
    private String myGroup;

    private boolean active;
}
