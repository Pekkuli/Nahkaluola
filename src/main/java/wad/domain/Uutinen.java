
package wad.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import wad.controller.UutisController;
import wad.file.FileObject;

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
    private LocalDate julkaisuDate;
    private int identifier;
    private Long kuvaId;
  
    
    @Column
    @ElementCollection
    private List<String> kategoriat;
    @ElementCollection
    private List<String> kirjoittajat;
    
    public Uutinen(String otsikko, String ingressi, String sisalto,LocalDate julkaisuDate){
        this.otsikko=otsikko;
        this.ingressi=ingressi;
        this.sisalto=sisalto;
        this.kategoriat = new ArrayList();
        this.kirjoittajat = new ArrayList();
        this.views=0;
        this.julkaisuDate=julkaisuDate;
        this.identifier= (int) (new Date().getTime()) /1000000000;
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
    
    
    
}
