package com.annuaire.app.service;

import com.annuaire.app.modele.Services;
import com.annuaire.app.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//On précise qu'il s'agit du code métier : Service
@Service
public class ServicesServiceImplement implements ServicesService {

    private final ServicesRepository servicesRepository;


    //Utilisation de l'objet repository dans mon constructeur
    @Autowired
    public ServicesServiceImplement(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }


    public Services create(Services services) {
        //Fonction pour sauvegarder le nouveau produit : "save" est une méthode de Spring Data JPA
        // qui permet de persister un objet dans la base de données.
        return servicesRepository.create(services);
    }


    public List<Services> read() {
        //On récupère une liste dans la DAO
        return servicesRepository.read();
    }

    public Services readById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return servicesRepository.readById(id);
    }

    @Transactional
    public Services update(Long id, Services services) {
        int updateServices = servicesRepository.update(id, services);
        //on vérifie que le client est bien trouvé et on gère les erreurs en précisant l'id
        if (updateServices == 0) {
            throw new RuntimeException("Aucun service trouvé avec l'ID : " + id);
        }
        return services;

    }


    public String delete(Long id) {
        servicesRepository.delete(id);
        return "Service supprimé";
    }
}
