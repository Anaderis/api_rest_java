package com.annuaire.app.repository;

import com.annuaire.app.modele.Employee;
import com.annuaire.app.modele.Services;
import com.annuaire.app.modele.Site;
import com.annuaire.app.service.ServicesServiceImplement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //READ - read
    public List<Employee> read() {
        String sql = "SELECT e.*, s.* FROM T_EMPLOYEE_EMP e LEFT JOIN T_SITE_SIT s ON e.sit_id = s.sit_id";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }


    //READ - by Id
    public Employee readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        String sql = "SELECT e.*, s.* FROM T_EMPLOYEE_EMP e LEFT JOIN T_SITE_SIT s ON e.sit_id = s.sit_id WHERE e.emp_id = ?";

        // Utilisation du RowMapper pour convertir chaque ligne en objet Employee
        List<Employee> employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);

        if (employeeList.isEmpty()) {
            return null;  // Aucun employee trouvé, retourner null
        }

        return employeeList.get(0);  // Retourner le premier employee trouvé
    }


    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("emp_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setSurname(rs.getString("emp_surname"));
            employee.setEmail(rs.getString("emp_email"));
            employee.setAddress(rs.getString("emp_address"));
            employee.setPostcode(rs.getString("emp_postcode"));
            employee.setCity(rs.getString("emp_city"));
            employee.setEntrydate(rs.getDate("emp_entryDate"));
            employee.setPhone(rs.getString("emp_phone"));
            employee.setAdmin(rs.getBoolean("emp_admin"));
            employee.setMobile(rs.getString("emp_mobile"));
            employee.setLogin(rs.getString("emp_login"));
            employee.setPassword(rs.getString("emp_password"));
            employee.setPhoto(rs.getString("emp_photo"));

            // Récupérer les informations du site
            Long siteId = rs.getLong("sit_id");
            if (!rs.wasNull()) { // Vérifie si la clé étrangère n'est pas null
                Site site = new Site();
                site.setId(siteId);
                site.setName(rs.getString("sit_name")); // Supposons que vous avez joint la table Site
                site.setCity(rs.getString("sit_city"));
                site.setSiret(rs.getString("sit_siret"));
                site.setAddress(rs.getString("sit_address"));
                site.setPostcode(rs.getString("sit_postcode"));
                site.setEmail(rs.getString("sit_email"));
                site.setPhone(rs.getString("sit_phone"));
                employee.setSite(site);
            }

            return employee;
        }
    }


    //UPDATE update()

    public int update(Long id, Employee employee) {
        // Vérification des paramètres
        if (id == null || employee == null) {
            throw new IllegalArgumentException("L'id et l'employee ne doivent pas être null !");
        }

        List<String> sqlParts = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Ajout conditionnel des champs à mettre à jour
        if (employee.getName() != null) {
            sqlParts.add("emp_name = ?");
            params.add(employee.getName());
        }
        if (employee.getSurname() != null) {
            sqlParts.add("emp_surname = ?");
            params.add(employee.getSurname());
        }
        if (employee.getEmail() != null) {
            sqlParts.add("emp_email = ?");
            params.add(employee.getEmail());
        }
        if (employee.getAddress() != null) {
            sqlParts.add("emp_address = ?");
            params.add(employee.getAddress());
        }
        if (employee.getPostcode() != null) {
            sqlParts.add("emp_postcode = ?");
            params.add(employee.getPostcode());
        }
        if (employee.getCity() != null) {
            sqlParts.add("emp_city = ?");
            params.add(employee.getCity());
        }
        if (employee.getEntrydate() != null) {
            sqlParts.add("emp_entryDate = ?");
            params.add(employee.getEntrydate());
        }
        if (employee.getPhone() != null) {
            sqlParts.add("emp_phone = ?");
            params.add(employee.getPhone());
        }
        if (employee.getAdmin() != null) {
            sqlParts.add("emp_admin = ?");
            params.add(employee.getAdmin());
        }
        if (employee.getMobile() != null) {
            sqlParts.add("emp_mobile = ?");
            params.add(employee.getMobile());
        }
        if (employee.getLogin() != null) {
            sqlParts.add("emp_login = ?");
            params.add(employee.getLogin());
        }
        if (employee.getPassword() != null) {
            sqlParts.add("emp_password = ?");
            params.add(employee.getPassword());
        }
        if (employee.getPhoto() != null) {
            sqlParts.add("emp_photo = ?");
            params.add(employee.getPhoto());
        }


        // Si aucun champ à mettre à jour, renvoyer une erreur
        if (sqlParts.isEmpty()) {
            throw new IllegalArgumentException("Aucun champ à mettre à jour !");
        }

        // Construction de la requête SQL en combinant les fragments
        String sql = "UPDATE T_EMPLOYEE_EMP SET " + String.join(", ", sqlParts) + " WHERE emp_id = ?";
        params.add(id);

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);


        // Exécution de la requête avec les paramètres
        return jdbcTemplate.update(sql, params.toArray());
    }

    // CREATE - create()
    //On met 3 guillemets quand il s'agit d'une chaîne de caractères multiligne ça commence à faire beaucoup de guillemets
    public Employee create(Employee employee) {
        String sql = """
    INSERT INTO T_EMPLOYEE_EMP (emp_name, emp_surname, emp_email, emp_address, emp_postcode, emp_city, emp_entrydate, emp_phone, emp_admin, emp_mobile, emp_login, emp_password, emp_photo) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        System.out.println("Inserting employee with name: " + employee.getName() + ", prénom: " + employee.getSurname());

        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getSurname(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPostcode(),
                employee.getCity(),
                employee.getEntrydate(),
                employee.getPhone(),
                employee.getAdmin(),
                employee.getMobile(),
                employee.getLogin(),
                employee.getPassword(),
                employee.getPhoto()

        );

        return employee;
    }


    // DELETE delete()

    public int delete(Long id) {
        String sql = "DELETE FROM T_EMPLOYEE_EMP WHERE emp_id = ?";
        return jdbcTemplate.update(sql, id);
    }



}
