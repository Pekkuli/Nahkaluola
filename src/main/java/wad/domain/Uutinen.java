
package wad.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long>{
    
    @Column
    private String otsikko;
    private String ingressi;
    private String sisalto;
    private double views;
    
    private Date Date;
    private Long identifier;
    private Long kuvaId;
  
    
    @Column
    @ElementCollection
    private List<String> kategoriat;
    @ElementCollection
    private List<String> kirjoittajat;
    
    public Uutinen(String otsikko, String ingressi, String sisalto){
        this.otsikko=otsikko;
        this.ingressi=ingressi;
        this.sisalto=sisalto;
        this.kategoriat = new ArrayList();
        this.kirjoittajat = new ArrayList();
        this.views=0;
        this.Date= Calendar.getInstance().getTime();
        this.identifier= Calendar.getInstance().getTime().getTime();
    }
    
    public void view(){
        this.views+=0.5;
    }
    
    public void LisaaKategoria(String kategoria){
        this.kategoriat.add(kategoria);
    }
    
    public void PoistaKategoria(String kategoria){
        this.kategoriat.remove(kategoria);
    }
    
    public void LisaaKirjoittaja(String kirjoittaja){
        this.kirjoittajat.add(kirjoittaja);
    }
    
    public void PoistaKirjoittaja(String kirjoittaja){
        this.kirjoittajat.remove(kirjoittaja);
    }
    
    public void lisaaKuva(Long kuvaId){
        this.kuvaId=kuvaId;
    }
    
    public void SetDate(Date date){
        this.Date=date;
        this.identifier=date.getTime();
    }
}
