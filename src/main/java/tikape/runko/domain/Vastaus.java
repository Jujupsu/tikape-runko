/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author juusojeskanen
 */
public class Vastaus {
    private Integer id;
    private Integer kysymys_id;
    private String vastausteksti;
    private Integer oikein;
    
    

    public Vastaus(Integer id, Integer kysymys_id, String vastausteksti, Integer oikein) {
        this.kysymys_id = kysymys_id;
        this.id = id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getKysymys_id() {
        return kysymys_id;
    }

    public void setKysymys_id(Integer kysymys_id) {
        this.kysymys_id = kysymys_id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public void setVastausteksti(String vastausteksti) {
        this.vastausteksti = vastausteksti;
    }
    
    public String getOikein() {
        if(this.oikein ==1){
            return " OIKEIN";
        }else{
            return " VÄÄRIN";
        }
        
    }

    public void setOikein(Integer oikein) {
        this.oikein = oikein;
    }
}
