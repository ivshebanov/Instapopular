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
import java.util.Objects;

@Data
@Entity
@Scope(value = "prototype")
@Table(name = "hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    private Usr usr;

    @NotBlank
    private String hashtag;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashtag hashtag1 = (Hashtag) o;
        return active == hashtag1.active &&
                Objects.equals(id, hashtag1.id) &&
                Objects.equals(usr, hashtag1.usr) &&
                Objects.equals(hashtag, hashtag1.hashtag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usr, hashtag, active);
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", usr=" + usr +
                ", hashtag='" + hashtag + '\'' +
                ", active=" + active +
                '}';
    }
}
