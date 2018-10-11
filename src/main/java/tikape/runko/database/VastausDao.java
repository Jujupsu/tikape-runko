package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public Vastaus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {

        
        Connection connection = database.getConnection();
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
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void save(Vastaus vastaus) throws SQLException {
        if(!vastaus.getVastausteksti().isEmpty()){
        Connection conn = database.getConnection();
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
    
    public List<Vastaus> findForKysymys(Integer kysymysId) throws SQLException {
        String query = "SELECT * FROM Vastaus WHERE kysymys_id = ?;";

        List<Vastaus> vastaukset = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
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
