
package wad.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.UutisLuola;
import wad.domain.Uutinen;
import wad.repository.UutisRepository;

@Controller
public class UutisController {
    
    @Autowired
    private UutisRepository uutisRepo;
    
    private String[] Kategoria= {"kotimaa",
                                    "politiikka",
                                    "kaupunki",
                                    "ulkomaa",
                                    "talous",
                                    "urheilu",
                                    "kulttuuri"}; 
    
    public String getKategoria(int kategoriaId){
        return Kategoria[kategoriaId];
    }
    
    @PostConstruct
    public void LisaaTestiUutiset(){
        LocalDate julkaisuDate = LocalDate.now();
        Uutinen a1 = new Uutinen("Kerbalit kuuhun","a","testi1",julkaisuDate, 0);
        Uutinen a2 = new Uutinen("Ebin","b","testi2",julkaisuDate, 1);
        Uutinen a3 = new Uutinen("Spudro spädre","c","testi3",julkaisuDate, 2);
        Uutinen a4 = new Uutinen("FEED NISPU","d","testi4",julkaisuDate, 3);
        Uutinen a5 = new Uutinen("KOHTA TULEE TURPAAN","E","testi5",julkaisuDate, 4);
        uutisRepo.save(a1);
        uutisRepo.save(a2);
        uutisRepo.save(a3);
        uutisRepo.save(a4);
        uutisRepo.save(a5);
    }
    
    
    
    @GetMapping("*")
    public String handleDefault(Model model) {
        model.addAttribute("uutiset", uutisRepo.findAll());
        return "etusivu";
    }
    
    
    @GetMapping("/{Kategoria}")
    public String Kategoria(Model model, @PathVariable() Long Kategoria){
        int kat = Kategoria.intValue();
        
        List<Uutinen> kaikki = uutisRepo.findAll();
        List valitut = new ArrayList();
        for(int i=0;i<kaikki.size();i++){
            if(kaikki.get(i).getKategoriat().contains(kat)){
                valitut.add(kaikki.get(i));
            }
        }
        
        model.addAttribute("uutiset", valitut);
        return getKategoria(kat);
    }
}
