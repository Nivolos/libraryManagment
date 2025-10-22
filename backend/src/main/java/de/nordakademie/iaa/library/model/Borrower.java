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
@Table(name = "borrowers")
public class Borrower implements Serializable {

    private static final long serialVersionUID = 2937068276858375843L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "matriculation", nullable = false, unique = true, length = 20)
    private String matriculation;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Borrower borrower)) {
            return false;
        }
        return Objects.equals(id, borrower.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
