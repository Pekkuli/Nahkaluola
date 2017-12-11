
package wad.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import wad.UutisLuola;
import wad.domain.Uutinen;
import wad.file.FileObject;
import wad.repository.UutisRepository;
import wad.repository.KuvaRepository;

@Controller
public class UutisController {
    
    @Autowired
    private UutisRepository uutisRepo;
    @Autowired
    private KuvaRepository KuvaRepo;
    
    private String[] Kategoria= {"Kotimaan Uutiset",
                                    "Politiikka ",
                                    "Kaupunki",
                                    "Ulkomaan Uutiset",
                                    "Talousuutiset",
                                    "Urheilu",
                                    "Kulttuuri"}; 
    
    public String getKategoria(int kategoriaId){
        return Kategoria[kategoriaId];
    }
    
    @PostConstruct
    public void LisaaTestiUutiset(){
        LocalDate julkaisuDate = LocalDate.now();
        
        Uutinen a1 = new Uutinen("Kerbalit kuuhun","ingressi","sisalto",julkaisuDate);;
        Uutinen a2 = new Uutinen("Ebin","ingressi","sisalto",julkaisuDate);
        Uutinen a3 = new Uutinen("Spudro spädre","ingressi","sisalto",julkaisuDate);
        Uutinen a4 = new Uutinen("FEED NISPU","ingressi","sisalto",julkaisuDate);
        Uutinen a5 = new Uutinen("KOHTA TULEE TURPAAN","ingressi","sisalto",julkaisuDate);
        Uutinen a6 = new Uutinen("KOHTI OTSOONII","ingressi","sisalto",julkaisuDate);
        a1.LisaaKategoria(Kategoria[0]);
        a1.LisaaKategoria(Kategoria[1]);
        a1.LisaaKategoria(Kategoria[2]);
        a1.LisaaKirjoittaja("herbertti");
        a1.LisaaKirjoittaja("albertti");
        a1.LisaaKirjoittaja("vilpertti");
        a2.LisaaKategoria(Kategoria[1]);
        a3.LisaaKategoria(Kategoria[2]);
        a4.LisaaKategoria(Kategoria[3]);
        a5.LisaaKategoria(Kategoria[4]);
        a6.LisaaKategoria(Kategoria[5]);
        uutisRepo.save(a1);
        uutisRepo.save(a2);
        uutisRepo.save(a3);
        uutisRepo.save(a4);
        uutisRepo.save(a5);
        uutisRepo.save(a6);
    }
    
    
    
    @GetMapping("*")
    public String handleDefault(){
        return "redirect:/etusivu";
    }
    
    @GetMapping("/etusivu")
    public String EtuSivu(Model model) {
        List kaikki = uutisRepo.findAll();
        List uusimmat = new ArrayList();
        if(kaikki.size()<= 5){
            uusimmat = kaikki;
        } else {
            uusimmat = kaikki.subList(kaikki.size()-5, kaikki.size());
        }
        model.addAttribute("uutiset", uusimmat);
        return "etusivu";
    }
    
    
    @GetMapping("/kategoria/{kat}")
    public String Kategoria(Model model, @PathVariable() Long kat){
        
        List<Uutinen> kaikki = uutisRepo.findAll();
        List valitut = new ArrayList();
        for(int i=0;i<kaikki.size();i++){
            if(kaikki.get(i).getKategoriat().contains(Kategoria[kat.intValue()])){
                valitut.add(kaikki.get(i));
            }}
        model.addAttribute("uutiset", valitut);
        model.addAttribute("kategoria", Kategoria[kat.intValue()]);
        return "kategoria";
    }
    
    @GetMapping("/uutinen/{id}")
    public String Uutinen(Model model, @PathVariable() Long id){
        model.addAttribute("uutinen", uutisRepo.getOne(id));       
        uutisRepo.getOne(id).view();
        uutisRepo.save(uutisRepo.getOne(id));
        return "uutinen";
    }
    
    @GetMapping("/muokkaus")
    public String uutinen(Model model,@ModelAttribute Uutinen uutinen){
        model.addAttribute("uutiset", uutisRepo.findAll());
        return "muokkaus";
    }
    
    @GetMapping("/muokkaus/{id}")
    public String uutinen(Model model,@PathVariable Long id){
        model.addAttribute("uutinen", uutisRepo.getOne(id));
        return "uutismuokkaus";
    }
    
    @DeleteMapping("/muokkaus/{uutisId}")
    public String deleteUutinen(@PathVariable Long uutisId) {
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        uutisRepo.delete(uutinen);
        return "redirect:/muokkaus";
    }
}
