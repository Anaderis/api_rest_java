package com.annuaire.app.controller;

import com.annuaire.app.modele.Site;
import com.annuaire.app.service.SiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//RequestMapping permet de récupérer les infos dans l'url type ?name=jeanmarie,
// pour ensuite récupérer ce body dans le PostMapping
@RequestMapping("/site")

public class SiteController {
    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }
    @PostMapping("/create")
    //Post Mapping permet de faire le lien avec une requête HTTP, ici /create
    //RequestBody permet de récupérer le body au niveau de la requête

    public Site create(@RequestBody Site site) {

        return siteService.create(site);
    }

    //Comme il s'agit d'une requête GET, on utilise l'annotation GetMapping
    @GetMapping("/read")
    public List<Site> read(){
        return siteService.read();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Site> readById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Retourne un 400 si l'ID est null
        }

        Site site = siteService.readById(id);
        if (site == null) {
            return ResponseEntity.notFound().build(); // Retourne un 404 si le produit n'est pas trouvé
        }

        return ResponseEntity.ok(site); // Retourne un 200 avec le produit trouvé
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Site site) {
        try {
            // Vérification des champs obligatoires
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id ne doit pas être null.");
            }

            if (site == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les données du site ne doivent pas être nulles.");
            }

            // Appeler la méthode update du repository
            Site rowsUpdated = siteService.update(id, site);

            // Vérifier si une mise à jour a été effectuée
            if (rowsUpdated != null) {
                return ResponseEntity.ok("Site mis à jour avec succès !");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun site trouvé avec l'id " + id);
            }

        } catch (IllegalArgumentException e) {
            // En cas de champs manquants ou invalides
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Gérer les erreurs inattendues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du site: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")

    public String delete(@PathVariable Long id) {
        return siteService.delete(id);
    }




}
