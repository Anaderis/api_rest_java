package com.annuaire.app.controller;

import com.annuaire.app.modele.Services;
import com.annuaire.app.service.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//RequestMapping permet de récupérer les infos dans l'url type ?name=jeanmarie,
// pour ensuite récupérer ce body dans le PostMapping
@RequestMapping("/services")

public class ServicesController {
    private final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }
    @PostMapping("/create")
    //Post Mapping permet de faire le lien avec une requête HTTP, ici /create
    //RequestBody permet de récupérer le body au niveau de la requête

    public Services create(@RequestBody Services services) {

        return servicesService.create(services);
    }

    //Comme il s'agit d'une requête GET, on utilise l'annotation GetMapping
    @GetMapping("/read")
    public List<Services> read(){
        return servicesService.read();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Services> readById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Retourne un 400 si l'ID est null
        }

        Services services = servicesService.readById(id);
        if (services == null) {
            return ResponseEntity.notFound().build(); // Retourne un 404 si le produit n'est pas trouvé
        }

        return ResponseEntity.ok(services); // Retourne un 200 avec le produit trouvé
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Services services) {
        try {
            // Vérification des champs obligatoires
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id ne doit pas être null.");
            }

            if (services == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les données du service ne doivent pas être nulles.");
            }

            // Appeler la méthode update du repository
            Services rowsUpdated = servicesService.update(id, services);

            // Vérifier si une mise à jour a été effectuée
            if (rowsUpdated != null) {
                return ResponseEntity.ok("Service mis à jour avec succès !");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun service trouvé avec l'id " + id);
            }

        } catch (IllegalArgumentException e) {
            // En cas de champs manquants ou invalides
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Gérer les erreurs inattendues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du service: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")

    public String delete(@PathVariable Long id) {
        return servicesService.delete(id);
    }




}
