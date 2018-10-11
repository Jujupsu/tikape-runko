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
import tikape.runko.domain.Vastaus;
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


        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "Kysymykset");
        }, new ThymeleafTemplateEngine());

        Spark.get("/vastaukset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", kysymysDao.findOne(Integer.parseInt(req.params(":id"))));
            
            map.put("vaihtoehdot", vastausDao.findForKysymys(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "Vastaus");
        }, new ThymeleafTemplateEngine());
        
        

        // Uuden kysymyksen luonti
        Spark.post("/create", (req, res) -> {
            List<Kysymys> kysymykset = kysymysDao.findAll();
            
            
            kysymysDao.save(new Kysymys(kysymykset.size()+1, req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysteksti")));
            res.redirect("/");
            
            return "";
        });
        // Kysymyksen poistaminen
        Spark.post("/delete/:id", (req, res) -> {
//            System.out.println(Integer.parseInt(req.params(":id")));
            kysymysDao.delete(Integer.parseInt(req.params(":id")));
            
            
            
            
            res.redirect("/");
            return "";
        });
        
        //Uuden vastausvaihtoehdon luonti
        Spark.post("/vastaukset/create/:id", (req, res) -> {
            List<Vastaus> vastaukset = vastausDao.findAll();
            
            // Checkboxin arvo pitää olla 0 tai 1.
            int ruutu = 0;
            if(req.queryParams("oikein")!=null){
                ruutu=1;
            }
            
            vastausDao.save(new Vastaus(vastaukset.size()+1,Integer.parseInt(req.params(":id")), req.queryParams("vastausteksti"), ruutu));
            
            res.redirect("/vastaukset/" + Integer.parseInt(req.params(":id")));
            
            return "";
        });
        
        //Vastausvaihtoehdon poistaminen
        Spark.post("/vastaukset/delete/:id", (req, res) -> {
//            System.out.println(Integer.parseInt(req.params(":id")));
            List<Vastaus> vastaukset = vastausDao.findAll();
            System.out.println(Integer.parseInt(req.params(":id")));
            int indeksi = 0;
            for(Vastaus vastaus : vastaukset){
                if(vastaus.getId()==Integer.parseInt(req.params(":id"))){
                    indeksi = vastaus.getKysymys_id();
                    
                    
                }
                
            }
            
            
            
            vastausDao.delete(Integer.parseInt(req.params(":id")));
            

            res.redirect("/vastaukset/" + indeksi);
            return "";
        });
        

    }
}
