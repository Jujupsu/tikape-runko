package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
//import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:kysymykset.db");
        database.init();
        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);


        get("/Kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");
            
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "Kysymykset");
        }, new ThymeleafTemplateEngine());

        get("/Vastaus", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", vastausDao.findAll());

            return new ModelAndView(map, "vastaukset");
        }, new ThymeleafTemplateEngine());

    }
}
