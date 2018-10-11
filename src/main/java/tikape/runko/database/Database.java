package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    
    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
    }
//
//    public void init() {
//        List<String> lauseet = sqliteLauseet();
//
//        // "try with resources" sulkee resurssin automaattisesti lopuksi
//        try (Connection conn = getConnection()) {
//            Statement st = conn.createStatement();
//
//            // suoritetaan komennot
//            for (String lause : lauseet) {
//                System.out.println("Running command >> " + lause);
//                st.executeUpdate(lause);
//            }
//
//        } catch (Throwable t) {
//            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
//            System.out.println("Error >> " + t.getMessage());
//        }
//    }
//
//    private List<String> sqliteLauseet() {
//        ArrayList<String> lista = new ArrayList<>();
//
//        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
////        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
////        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
////        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
////        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
//        
//        
////        lista.add("CREATE TABLE Kysymys(id integer PRIMARY KEY, kurssi varchar(100), aihe varchar(100), kysymysteksti varchar(250));");
////        lista.add("CREATE TABLE Vastaus(id integer PRIMARY KEY,kysymys_id integer,vastausteksti varchar(250),oikein integer,FOREIGN KEY (kysymys_id) REFERENCES Kysymys(id));");
//        return lista;
//    }
}
