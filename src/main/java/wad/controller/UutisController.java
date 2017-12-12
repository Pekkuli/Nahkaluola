
package wad.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wad.CustomComparator;
import wad.domain.Uutinen;
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

//    Testi uutisia jos haluaa kokeilla sovellusta
//    @PostConstruct
//    public void LisaaTestiUutiset(){
//        Date julkaisuDate = Calendar.getInstance().getTime();
//        
//        Uutinen a1 = new Uutinen("Kerbalit kuuhun","ingressi","sisalto");;
//        Uutinen a2 = new Uutinen("Ebin","ingressi","sisalto");
//        Uutinen a3 = new Uutinen("Spudro spädre","ingressi","sisalto");
//        Uutinen a4 = new Uutinen("FEED NISPU","ingressi","sisalto");
//        Uutinen a5 = new Uutinen("KOHTA TULEE TURPAAN","ingressi","sisalto");
//        Uutinen a6 = new Uutinen("KOHTI OTSOONII","ingressi","sisalto");
//        a1.LisaaKategoria(Kategoria[0]);
//        a1.LisaaKategoria(Kategoria[1]);
//        a1.LisaaKategoria(Kategoria[2]);
//        a1.LisaaKirjoittaja("herbertti");
//        a1.LisaaKirjoittaja("albertti");
//        a1.LisaaKirjoittaja("vilpertti");
//        a2.LisaaKategoria(Kategoria[1]);
//        a3.LisaaKategoria(Kategoria[2]);
//        a4.LisaaKategoria(Kategoria[3]);
//        a5.LisaaKategoria(Kategoria[4]);
//        a6.LisaaKategoria(Kategoria[5]);
//        a1.SetDate(julkaisuDate);
//        a2.SetDate(julkaisuDate);
//        a3.SetDate(julkaisuDate);
//        a4.SetDate(julkaisuDate);
//        a5.SetDate(julkaisuDate);
//        a6.SetDate(julkaisuDate);
//        uutisRepo.save(a1);
//        uutisRepo.save(a2);
//        uutisRepo.save(a3);
//        uutisRepo.save(a4);
//        uutisRepo.save(a5);
//        uutisRepo.save(a6);
//    }
    
    
//    Satunnaisen osoitteent etusivulle
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
        Collections.sort(uusimmat, new CustomComparator());
        model.addAttribute("uutiset", uusimmat);
        return "etusivu";
    }
    
    
    @GetMapping("/kategoria/{kat}")
    public String HaeKategoria(Model model, @PathVariable() Long kat){
        
        List<Uutinen> kaikki = uutisRepo.findAll();
        List valitut = new ArrayList();
        for(int i=0;i<kaikki.size();i++){
            if(kaikki.get(i).getKategoriat().contains(Kategoria[kat.intValue()])){
                valitut.add(kaikki.get(i));
            }}
        Collections.sort(valitut, new CustomComparator());
        model.addAttribute("uutiset", valitut);
        model.addAttribute("kategoria", Kategoria[kat.intValue()]);
        return "kategoria";
    }
    
    @GetMapping("/uutinen/{id}")
    public String HaeUutinen(Model model, @PathVariable() Long id){
        model.addAttribute("uutinen", uutisRepo.getOne(id));       
        uutisRepo.getOne(id).view();
        uutisRepo.save(uutisRepo.getOne(id));
        return "uutinen";
    }
}
