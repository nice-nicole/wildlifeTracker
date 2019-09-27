import org.sql2o.Connection;

import java.util.List;

public class Sighting {
    private int id;
    private String location;
    private String rangerName;
    private int animalId;

    public Sighting(String location, String rangerName, int animalId) {
        this.location = location;
        this.rangerName = rangerName;
        this.animalId = animalId;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getRangerName() {
        return rangerName;
    }

    public int getAnimalId() {
        return animalId;
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (location, rangerName, animalId) VALUES (:location, :rangerName, :animalId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("location", this.location)
                    .addParameter("rangerName", this.rangerName)
                    .addParameter("animalId", this.animalId)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }
//    public static List<Sighting> allAnimals() {
//        String sql = "SELECT location, rangerName FROM sightings where animalId=: ";
//        try(Connection con = DB.sql2o.open()) {
//            return con.createQuery(sql).executeAndFetch(Sighting.class);
//        }
//    }

    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM monsters WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();
        }
    }
}
