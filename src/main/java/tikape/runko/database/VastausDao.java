package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static tikape.runko.database.Database.getConnection;
import tikape.runko.domain.Vastaus;

/**
 *
 * @author juusojeskanen
 */
public class VastausDao implements Dao<Vastaus, Integer>{
    
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    
    @Override
    public Vastaus findOne(Integer key) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vastaus> findAll() throws Exception {

        
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer kysymys_id = rs.getInt("kysymys_id");
            String vastausteksti = rs.getString("vastausteksti");
            Integer oikein = rs.getInt("oikein");
            

            vastaukset.add(new Vastaus(id, kysymys_id, vastausteksti, oikein));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws Exception {
        // avaa yhteys tietokantaan
            Connection conn = getConnection();
            
            
            // tee kysely
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
            stmt.setInt(1, key);
            
            stmt.executeUpdate();
            
            // sulje yhteys tietokantaan
            stmt.close();
            conn.close();
    }
    
    public void save(Vastaus vastaus) throws Exception {
        if(!vastaus.getVastausteksti().isEmpty()){
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus"
                + " (kysymys_id, vastausteksti, oikein)"
                + " VALUES (?, ?, ?)");
        
        int palautus = 0;
        if(vastaus.getOikein().equals(" OIKEIN")){
            palautus = 1;
        }
        
        stmt.setInt(1, vastaus.getKysymys_id());
        stmt.setString(2, vastaus.getVastausteksti());
        stmt.setInt(3, palautus);
        
        
        stmt.executeUpdate();

        stmt.close();
        conn.close();
        }
    }
    
    public List<Vastaus> findForKysymys(Integer kysymysId) throws Exception {
        String query = "SELECT * FROM Vastaus WHERE kysymys_id = ?;";

        List<Vastaus> vastaukset = new ArrayList<>();

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setInt(1, kysymysId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                vastaukset.add(new Vastaus(result.getInt("id"), result.getInt("kysymys_id"), result.getString("vastausteksti"), result.getInt("oikein")));
            }
        }

        return vastaukset;
    }
}
