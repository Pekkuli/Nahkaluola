
package wad.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import wad.controller.UutisController;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long>{
    
    
    private String otsikko;
    private String ingressi;
    private String sisalto;
    
    private LocalDate julkaisuDate;
    
//    private byte[] kuva;
    
    @Column
    @ElementCollection
    private List<Integer> kategoriat;
    @Column
    @ElementCollection
    private List<String> kirjoittajat;
    
    public Uutinen(String otsikko, String ingressi, String sisalto,LocalDate julkaisuDate, int kategoriaId){
        this.otsikko=otsikko;
        this.ingressi=ingressi;
        this.sisalto=sisalto;
        this.kategoriat = new ArrayList();
        this.kirjoittajat= new ArrayList();
        this.julkaisuDate=julkaisuDate;
        this.kategoriat.add(kategoriaId);
    }
    
}
