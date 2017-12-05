
package wad.controller;

import java.time.LocalDate;
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
import wad.UutisLuola.Kategoria;
import wad.domain.Uutinen;
import wad.repository.UutisRepository;

@Controller
public class UutisController {
    
    @Autowired
    private UutisRepository uutisRepo;
    
    private String[] kategoriat= {"kotimaa",
                                    "politiikka",
                                    "kaupunki",
                                    "ulkomaa",
                                    "talous",
                                    "urheilu",
                                    "kulttuuri"}; 
    
    @PostConstruct
    public void LisaaTestiUutiset(){
        LocalDate julkaisuDate = LocalDate.now();
        Uutinen a1 = new Uutinen("Kerbalit kuuhun","a","testi1",julkaisuDate);
        Uutinen a2 = new Uutinen("Ebin","b","testi2",julkaisuDate);
        Uutinen a3 = new Uutinen("Spudro spädre","c","testi3",julkaisuDate);
        Uutinen a4 = new Uutinen("FEED NISPU","d","testi4",julkaisuDate);
        uutisRepo.save(a1);
        uutisRepo.save(a2);
        uutisRepo.save(a3);
        uutisRepo.save(a4);
    }
    
    
    
    @GetMapping("*")
    public String handleDefault(Model model) {
        model.addAttribute("uutiset", uutisRepo.findAll());
        return "etusivu";
    }
    
    
    @GetMapping("/{Kategoria}")
    public String Kategoria(Model model, @PathVariable() Long Kategoria){
        int kat = Kategoria.intValue();
        
        model.addAttribute("uutiset", uutisRepo.findAll());
        return kategoriat[kat];
    }
}
