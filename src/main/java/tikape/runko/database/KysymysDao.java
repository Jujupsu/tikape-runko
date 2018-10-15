package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static tikape.runko.database.Database.getConnection;
import tikape.runko.domain.Kysymys;



/**
 *
 * @author juusojeskanen
 */
public class KysymysDao implements Dao<Kysymys, Integer>{
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    

    @Override
    public Kysymys findOne(Integer key) throws Exception {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String kysymysteksti = rs.getString("kysymysteksti");
        

        Kysymys o = new Kysymys(id, kurssi, aihe, kysymysteksti);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Kysymys> findAll() throws Exception {

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String kysymysteksti = rs.getString("kysymysteksti");

            kysymykset.add(new Kysymys(id, kurssi, aihe, kysymysteksti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }

    @Override
    public void delete(Integer key) throws Exception {
        // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            //Poistetaan myös kaikki kysymykseen liittyvät vastaukset
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id = ?");
            stmt1.setInt(1, key);
            
            stmt1.executeUpdate();
            
            // sulje yhteys tietokantaan
            stmt1.close();
            // tee kysely
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setInt(1, key);
            
            stmt.executeUpdate();
            
            // sulje yhteys tietokantaan
            stmt.close();
            
            
            conn.close();
            
    }
    
    public void save(Kysymys kysymys) throws Exception {
        if(!kysymys.getKurssi().equals("") && !kysymys.getAihe().equals("") && !kysymys.getKysymysteksti().equals("")){
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys"
                + " (kurssi, aihe, kysymysteksti)"
                + " VALUES (?,?,?)");
        
         
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        
        
        stmt.executeUpdate();

        stmt.close();
        conn.close();
        }
        
    }
}
