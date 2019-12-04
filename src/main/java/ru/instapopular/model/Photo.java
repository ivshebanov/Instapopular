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
import java.util.Objects;

@Data
@Entity
@Scope(value = "prototype")
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private Usr usr;

    private boolean active;

    @NotBlank
    private String photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo1 = (Photo) o;
        return active == photo1.active &&
                Objects.equals(id, photo1.id) &&
                Objects.equals(usr, photo1.usr) &&
                Objects.equals(photo, photo1.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usr, active, photo);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", usr=" + usr +
                ", active=" + active +
                ", photo='" + photo + '\'' +
                '}';
    }
}
