package com.example.books.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Once created the user, password should not be changed from adding new books.
 */
@XmlRootElement(name = "user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private Long id;
    private String username;
    private String pseudonym;

    public User() {
        // No-argument constructor for JAXB
    }

    // Getters and Setters
    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPseudonym(), user.getPseudonym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPseudonym());
    }
}