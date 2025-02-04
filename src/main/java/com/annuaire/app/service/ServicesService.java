package com.annuaire.app.service;

import com.annuaire.app.modele.Services;

import java.util.List;

public interface ServicesService {

    // CRUD : CREATE
    Services create(Services services);

    //CRUD : READ
    List<Services> read();

    //CRUD : READ by Id
    Services readById(Long id);

    //CRUD : Update
    Services update(Long id, Services services);

    //CRUD : Delete
    String delete(Long id);
}
