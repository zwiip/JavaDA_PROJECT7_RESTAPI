package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Ne peut pas être vide.")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    private String username;

    @NotBlank(message = "Ne peut pas être vide.")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    private String password;

    @NotBlank(message = "Ne peut pas être vide.")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    private String fullname;

    @NotBlank(message = "Ne peut pas être vide.")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    private String role;

    public User() {}

    public User(String username, String password, String fullname, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
