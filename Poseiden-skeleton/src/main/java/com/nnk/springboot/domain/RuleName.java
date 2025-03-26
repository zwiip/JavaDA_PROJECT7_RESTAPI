package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Ce champ ne peut pas être vide.")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    String name;

    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    String description;

    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    String json;

    @Length(max = 512, message = "Ne doit pas excéder 512 caractères.")
    String template;

    @Column(name = "sqlStr")
    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    String sql;

    @Length(max = 125, message = "Ne doit pas excéder 125 caractères.")
    String sqlPart;

    public RuleName() {}

    public RuleName(String name, String description, String json, String template, String sql, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sql = sql;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
