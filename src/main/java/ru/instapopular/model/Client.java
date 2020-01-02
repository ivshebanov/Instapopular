package ru.instapopular.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Scope(value = "prototype")
@Table(name = "potential_client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    private boolean active;

    @NotBlank
    @JoinColumn(name = "client_name")
    private String clientName;

    @PositiveOrZero
    @JoinColumn(name = "frequency")
    private Integer frequency;
}
