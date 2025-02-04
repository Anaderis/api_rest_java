package com.annuaire.app.service;

import com.annuaire.app.modele.Site;
import com.annuaire.app.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//On précise qu'il s'agit du code métier : Service
@Service
public class SiteServiceImplement implements SiteService {

    private final SiteRepository siteRepository;


    //Utilisation de l'objet repository dans mon constructeur
    @Autowired
    public SiteServiceImplement(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }


    public Site create(Site site) {
        //Fonction pour sauvegarder le nouveau produit : "save" est une méthode de Spring Data JPA
        // qui permet de persister un objet dans la base de données.
        return siteRepository.create(site);
    }


    public List<Site> read() {
        //On récupère une liste dans la DAO
        return siteRepository.read();
    }

    public Site readById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return siteRepository.readById(id);
    }

    @Transactional
    public Site update(Long id, Site site) {
        int updateSite = siteRepository.update(id, site);
        //on vérifie que le client est bien trouvé et on gère les erreurs en précisant l'id
        if (updateSite == 0) {
            throw new RuntimeException("Aucun site trouvé avec l'ID : " + id);
        }
        return site;

    }


    public String delete(Long id) {
        siteRepository.delete(id);
        return "Site supprimé";
    }
}
