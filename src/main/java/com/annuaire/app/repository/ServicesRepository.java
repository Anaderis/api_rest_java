package com.annuaire.app.repository;

import com.annuaire.app.modele.Services;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicesRepository {
    private final JdbcTemplate jdbcTemplate;

    public ServicesRepository(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //READ - read
    public List<Services> read() {
        String sql = "SELECT * FROM T_SERVICE_SER";
        return jdbcTemplate.query(sql, new ServiceRowMapper());
    }

    //READ - by Id
    public Services readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        String sql = "SELECT * FROM T_SERVICE_SER WHERE ser_id = ?";

        // Utilisation du RowMapper pour convertir chaque ligne en objet Service
        List<Services> servicesList = jdbcTemplate.query(sql, new ServiceRowMapper(), id);

        if (servicesList.isEmpty()) {
            return null;  // Aucun service trouvé, retourner null
        }

        return servicesList.get(0);  // Retourner le premier service trouvé
    }


    private static class ServiceRowMapper implements RowMapper<Services> {
        @Override
        public Services mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            Services services = new Services();
            services.setId(rs.getLong("ser_id"));
            services.setName(rs.getString("ser_name"));
            services.setHeadcount(rs.getInt("ser_headcount"));
            services.setDescription(rs.getString("ser_description"));

            return services;
        }
    }

    //UPDATE update()

    public int update(Long id, Services services) {
        // Vérification des paramètres
        if (id == null || services == null) {
            throw new IllegalArgumentException("L'id et le service ne doivent pas être null !");
        }

        List<String> sqlParts = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Ajout conditionnel des champs à mettre à jour
        if (services.getName() != null) {
            sqlParts.add("ser_name = ?");
            params.add(services.getName());
        }
        if (services.getHeadcount() != 0) {
            sqlParts.add("ser_headcount = ?");
            params.add(services.getHeadcount());
        }
        if (services.getDescription() != null) {
            sqlParts.add("ser_description = ?");
            params.add(services.getDescription());
        }


        // Si aucun champ à mettre à jour, renvoyer une erreur
        if (sqlParts.isEmpty()) {
            throw new IllegalArgumentException("Aucun champ à mettre à jour !");
        }

        // Construction de la requête SQL en combinant les fragments
        String sql = "UPDATE T_SERVICE_SER SET " + String.join(", ", sqlParts) + " WHERE ser_id = ?";
        params.add(id);

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);


        // Exécution de la requête avec les paramètres
        return jdbcTemplate.update(sql, params.toArray());
    }

    // CREATE - create()
    //On met 3 guillemets quand il s'agit d'une chaîne de caractères multiligne ça commence à faire beaucoup de guillemets
    public Services create(Services services) {
        String sql = """
    INSERT INTO T_SERVICE_SER (ser_name, ser_headcount, ser_description) 
    VALUES (?, ?, ?)
    """;

        System.out.println("Inserting service with name: " + services.getName() + ", description: " + services.getDescription());

        jdbcTemplate.update(sql,
                services.getName(),
                services.getHeadcount(),
                services.getDescription()
        );

        return services;
    }


    // DELETE delete()

    public int delete(Long id) {
        String sql = "DELETE FROM T_SERVICE_SER WHERE ser_id = ?";
        return jdbcTemplate.update(sql, id);
    }



}
