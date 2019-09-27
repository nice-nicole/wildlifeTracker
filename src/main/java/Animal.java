import org.sql2o.Connection;

import java.sql.Timestamp;
import java.util.List;

public class Animal implements DBManagement{

    private int id;
    private String name;
    private String health;
    private String endangered;
    private String age;

    public Animal(String name, String endangered, String health, String age) {
        this.name = name;
        this.endangered = endangered;
        this.health = health;
        this.age= age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHealth() {
        return health;
    }

    public String getEndangered() {
        return endangered;
    }

    public String getAge() {
        return age;
    }

    @Override
    public boolean equals(Object otherAnimal) {
        if(!(otherAnimal instanceof Animal)){
            return false;
        }else {
            Animal newAnimal = (Animal) otherAnimal;
            return  this.getAge().equals(newAnimal.getAge()) &&
                    this.getEndangered().equals(newAnimal.getEndangered()) &&
                    this.getHealth().equals(newAnimal.getHealth()) &&
                    this.getName().equals(newAnimal.getName());
        }
    }
    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, health, endangered, age) VALUES (:name, :health, :endangered, :age)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("health", this.health)
                    .addParameter("endangered", this.endangered)
                    .addParameter("age", this.age)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Animal> all() {
        String sql = "SELECT * FROM animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Animal.class);
        }
    }
    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();
        }
    }
    public static void deletebyId(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
    public static Animal findById(int id){
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM Animal where id=:id";
            Animal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
            return animal;

        }
    }



}
