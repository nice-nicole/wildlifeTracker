import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args){
        staticFileLocation("/public");

        //get: the first page
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show a form to create a new Animal
        get("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = Animal.all();
            model.put("animals", animals);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new Animal
        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals= Animal.all();
            model.put("animals", animals);
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new Animal
        post("/success", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String endangered = req.queryParams("endangered");
            String health = req.queryParams("health");
            String age = req.queryParams("age");
            Animal newAnimal = new Animal(name,endangered, health, age);
            newAnimal.save();
            System.out.println(newAnimal);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new Sighting
        post("/success2", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String location = req.queryParams("location");
            String rangerName = req.queryParams("rangerName");
            int animalId = Integer.parseInt(req.queryParams("animalId"));
            Sighting newSighting = new Sighting(location,rangerName, animalId);
            newSighting.save();
            return new ModelAndView(model, "success2.hbs");

        }, new HandlebarsTemplateEngine());


        get("/animalsSightings", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = Animal.all(); //refresh list of links for navbar
            List<Sighting> sightings = Sighting.all();
            model.put("animals", animals);
            model.put("sightings", sightings);
            return new ModelAndView(model, "showAll.hbs"); //new layout
        }, new HandlebarsTemplateEngine());



    }
}
