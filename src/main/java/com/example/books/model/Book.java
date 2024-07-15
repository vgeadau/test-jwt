package com.example.books.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Book entity which is being persisted.
 * </br>
 * Note1: BigDecimal should be used for Finance related calculus in java, however this
 * is a POC (Proof of Concept) and as a result we simplified a bit the implementation using Double.
 * Note2: As problem is not clearly defined regarding image - we should have 2 fields image name String
 * and image itself which is a bytearray (to be stored in a BLOB or something similar). As this is a POC we choose
 * coverImage as String.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private User author;

    private String coverImage;

    private Double price;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getDescription(), book.getDescription()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getCoverImage(), book.getCoverImage()) && Objects.equals(getPrice(), book.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getAuthor(), getCoverImage(), getPrice());
    }
}