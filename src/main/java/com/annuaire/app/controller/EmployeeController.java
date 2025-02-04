package com.annuaire.app.controller;

import com.annuaire.app.modele.Employee;
import com.annuaire.app.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//RequestMapping permet de récupérer les infos dans l'url type ?name=jeanmarie,
// pour ensuite récupérer ce body dans le PostMapping
@RequestMapping("/employee")

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/create")
    //Post Mapping permet de faire le lien avec une requête HTTP, ici /create
    //RequestBody permet de récupérer le body au niveau de la requête

    public Employee create(@RequestBody Employee employee) {

        return employeeService.create(employee);
    }

    //Comme il s'agit d'une requête GET, on utilise l'annotation GetMapping
    @GetMapping("/read")
    public List<Employee> read(){
        return employeeService.read();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Employee> readById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Retourne un 400 si l'ID est null
        }

        Employee employee = employeeService.readById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build(); // Retourne un 404 si le produit n'est pas trouvé
        }

        return ResponseEntity.ok(employee); // Retourne un 200 avec le produit trouvé
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            // Vérification des champs obligatoires
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id ne doit pas être null.");
            }

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Les données du employee ne doivent pas être nulles.");
            }

            // Appeler la méthode update du repository
            Employee rowsUpdated = employeeService.update(id, employee);

            // Vérifier si une mise à jour a été effectuée
            if (rowsUpdated != null) {
                return ResponseEntity.ok("Employee mis à jour avec succès !");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun employee trouvé avec l'id " + id);
            }

        } catch (IllegalArgumentException e) {
            // En cas de champs manquants ou invalides
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Gérer les erreurs inattendues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")

    public String delete(@PathVariable Long id) {
        return employeeService.delete(id);
    }




}
