package com.annuaire.app.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="t_site_sit")
//Entity permet de mapper la table Produit avec la table dans la BDD correspondante

@Getter
@Setter
@NoArgsConstructor

public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sit_id")
    private Long id;
    @Column(name = "sit_name")
    private String name;
    @Column(name = "sit_city")
    private String city;
    @Column(name = "sit_siret")
    private String siret;
    @Column(name = "sit_address")
    private String address;
    @Column(name = "sit_postcode")
    private String postcode;
    @Column(name = "sit_email")
    private String email;
    @Column(name = "sit_phone")
    private String phone;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
