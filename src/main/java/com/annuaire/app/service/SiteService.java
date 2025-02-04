package com.annuaire.app.service;

import com.annuaire.app.modele.Site;

import java.util.List;

public interface SiteService {

    // CRUD : CREATE
    Site create(Site site);

    //CRUD : READ
    List<Site> read();

    //CRUD : READ by Id
    Site readById(Long id);

    //CRUD : Update
    Site update(Long id, Site site);

    //CRUD : Delete
    String delete(Long id);
}
