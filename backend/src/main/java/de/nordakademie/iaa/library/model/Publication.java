package de.nordakademie.iaa.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "publications")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publication implements Serializable {

    private static final long serialVersionUID = 862630786373548660L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Basic(optional = false)
    @Column(name = "authors", nullable = false, length = 255)
    private String authors;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    @Column(name = "publisher", length = 120)
    private String publisher;

    @Column(name = "isbn", length = 30)
    private String isbn;

    @Basic(optional = false)
    @Column(name = "stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties({"publications"})
    private PublicationType type;

    @ManyToMany
    @JoinTable(name = "publication_keywords",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private Set<Keyword> keywords = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public PublicationType getType() {
        return type;
    }

    public void setType(PublicationType type) {
        this.type = type;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publication that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
