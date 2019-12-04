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
@Table(name = "guys")
public class Guys {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    private boolean active;

    @NotBlank
    @JoinColumn(name = "guy_name")
    private String guyName;

    @PositiveOrZero
    @JoinColumn(name = "count_like")
    private Integer countLike;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guys guys = (Guys) o;
        return active == guys.active &&
                Objects.equals(id, guys.id) &&
                Objects.equals(usr, guys.usr) &&
                Objects.equals(this.guyName, guys.guyName) &&
                Objects.equals(countLike, guys.countLike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usr, active, guyName, countLike);
    }

    @Override
    public String toString() {
        return "Guys{" +
                "id=" + id +
                ", usr=" + usr +
                ", active=" + active +
                ", guyName='" + guyName + '\'' +
                ", countLike=" + countLike +
                '}';
    }
}
