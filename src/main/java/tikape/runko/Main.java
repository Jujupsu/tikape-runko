package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;
//import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:kysymykset.db");
//        database.init();
        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);


        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "Kysymykset");
        }, new ThymeleafTemplateEngine());

        Spark.get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", vastausDao.findOne(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "Vastaus");
        }, new ThymeleafTemplateEngine());
        
        
        
        Spark.post("/create", (req, res) -> {
            List<Kysymys> kysymykset = kysymysDao.findAll();
            
            
            kysymysDao.save(new Kysymys(kysymykset.size()+1, req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysteksti")));
            res.redirect("/");
            return "";
        });
        
        Spark.post("/delete/:id", (req, res) -> {
//            System.out.println(Integer.parseInt(req.params(":id")));
            kysymysDao.delete(Integer.parseInt(req.params(":id")));

            res.redirect("/");
            return "";
        });
        
        

    }
}
