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
import java.util.Objects;

@Data
@Entity
@Scope(value = "prototype")
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    private boolean active;

    @NotBlank
    private String guys;

    @PositiveOrZero
    @JoinColumn(name = "count_like")
    private Integer countLike;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return active == like.active &&
                Objects.equals(id, like.id) &&
                Objects.equals(usr, like.usr) &&
                Objects.equals(guys, like.guys) &&
                Objects.equals(countLike, like.countLike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usr, active, guys, countLike);
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", usr=" + usr +
                ", active=" + active +
                ", guys='" + guys + '\'' +
                ", countLike=" + countLike +
                '}';
    }
}
