package com.annuaire.app.repository;

import com.annuaire.app.modele.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SiteRepository {
    private final JdbcTemplate jdbcTemplate;

    public SiteRepository(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //READ - read
    public List<Site> read() {
        String sql = "SELECT * FROM T_SITE_SIT";
        return jdbcTemplate.query(sql, new SiteRowMapper());
    }

    //READ - by Id
    public Site readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        String sql = "SELECT * FROM T_SITE_SIT WHERE sit_id = ?";

        // Utilisation du RowMapper pour convertir chaque ligne en objet Site
        List<Site> siteList = jdbcTemplate.query(sql, new SiteRowMapper(), id);

        if (siteList.isEmpty()) {
            return null;  // Aucun site trouvé, retourner null
        }

        return siteList.get(0);  // Retourner le premier site trouvé
    }


    private static class SiteRowMapper implements RowMapper<Site> {
        @Override
        public Site mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
            Site site = new Site();
            site.setId(rs.getLong("sit_id"));
            site.setName(rs.getString("sit_name"));
            site.setCity(rs.getString("sit_city"));
            site.setSiret(rs.getString("sit_siret"));
            site.setAddress(rs.getString("sit_address"));
            site.setPostcode(rs.getString("sit_postcode"));
            site.setEmail(rs.getString("sit_email"));
            site.setPhone(rs.getString("sit_phone"));

            return site;
        }
    }

    //UPDATE update()

    public int update(Long id, Site site) {
        // Vérification des paramètres
        if (id == null || site == null) {
            throw new IllegalArgumentException("L'id et le site ne doivent pas être null !");
        }

        // Liste pour stocker les fragments de la requête SQL
        //Sqlparts permet de récupérer chaque fragment de la requête
        // selon si la valeur du tableau est tapé ou non (site_name par exemple)
        //params récupère lui les valeurs tapées par l'utilisateur
        // et les intègre à la requête sql avec string.add et params.adds dans la variable "sql".
        // Avantage : les valeurs des colonnes sont ajoutées dans le même ordre que les fragments de requêtes
        // sql correspondants. permet de ne pas casser la requête

        List<String> sqlParts = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Ajout conditionnel des champs à mettre à jour
        if (site.getName() != null) {
            sqlParts.add("sit_name = ?");
            params.add(site.getName());
        }
        if (site.getCity() != null) {
            sqlParts.add("sit_city = ?");
            params.add(site.getCity());
        }
        if (site.getSiret() != null) {
            sqlParts.add("sit_siret = ?");
            params.add(site.getSiret());
        }
        if (site.getAddress() != null) {
            sqlParts.add("sit_address = ?");
            params.add(site.getAddress());
        }
        if (site.getPostcode() != null) {
            sqlParts.add("sit_postcode = ?");
            params.add(site.getPostcode());
        }
        if (site.getEmail() != null) {
            sqlParts.add("sit_email = ?");
            params.add(site.getEmail());
        }
        if (site.getPhone() != null) {
            sqlParts.add("sit_phone = ?");
            params.add(site.getPhone());
        }


        // Si aucun champ à mettre à jour, renvoyer une erreur
        if (sqlParts.isEmpty()) {
            throw new IllegalArgumentException("Aucun champ à mettre à jour !");
        }

        // Construction de la requête SQL en combinant les fragments
        String sql = "UPDATE T_SITE_SIT SET " + String.join(", ", sqlParts) + " WHERE sit_id = ?";
        params.add(id);

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);


        // Exécution de la requête avec les paramètres
        return jdbcTemplate.update(sql, params.toArray());
    }

    // CREATE - create()
    //On met 3 guillemets quand il s'agit d'une chaîne de caractères multiligne ça commence à faire beaucoup de guillemets
    public Site create(Site site) {
        String sql = """
    INSERT INTO T_SITE_SIT (sit_name, sit_city, sit_siret, sit_address, sit_postcode, sit_email, sit_phone) 
    VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        System.out.println("Inserting site with name: " + site.getName() + ", siret: " + site.getSiret());

        jdbcTemplate.update(sql,
                site.getName(),
                site.getCity(),
                site.getSiret(),
                site.getAddress(),
                site.getPostcode(),
                site.getEmail(),
                site.getPhone()
        );

        return site;
    }


    // DELETE delete()

    public int delete(Long id) {
        String sql = "DELETE FROM T_SITE_SIT WHERE SIT_id = ?";
        return jdbcTemplate.update(sql, id);
    }



}
