package de.nordakademie.iaa.library.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "keywords")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 4134191710688527895L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, unique = true, length = 80)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Keyword keyword)) {
            return false;
        }
        return Objects.equals(id, keyword.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
