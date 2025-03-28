package dev.mfikri.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    private String id;
    private String email;
    private String password;

    @OneToOne(mappedBy = "credential")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
