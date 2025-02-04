package com.annuaire.app.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_service_ser")
//Entity permet de mapper la table Produit avec la table dans la BDD correspondante

@Getter
@Setter
@NoArgsConstructor

public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ser_id")
    private Long id;
    //@Column(name = "ser_name")
    private String name;
    //@Column(name = "ser_headcount")
    private int  headcount;
    //@Column(name = "ser_description")
    private String description;

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

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
