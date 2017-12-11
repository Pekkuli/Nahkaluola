/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Uutinen;
import wad.file.FileObject;
import wad.repository.KuvaRepository;
import wad.repository.UutisRepository;

@Controller
public class MuokkausController {
    
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
    
    @PostMapping("/muokkaus")
    public String AddUutinen(){
        LocalDate julkaisuDate = LocalDate.now();
        Uutinen uutinen = new Uutinen();
        Long DateTime = new Date().getTime();
        uutinen.setOtsikko("Tyhjä["+DateTime+"]");
        uutinen.setIngressi("tyhjä");
        uutinen.setSisalto("tyhjä");
        uutinen.setJulkaisuDate(julkaisuDate);
        uutisRepo.save(uutinen);
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}", params="otsikko")
    public String muokkaaOtsikko(@PathVariable Long uutisId,@RequestParam String otsikko){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        if(!otsikko.equals("")){
            uutinen.setOtsikko(otsikko);
            uutisRepo.save(uutinen);
        }
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}", params="ingressi")
    public String muokkaaIngressi(@PathVariable Long uutisId,@RequestParam String ingressi){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        if(!ingressi.equals("")){
            uutinen.setIngressi(ingressi);
            uutisRepo.save(uutinen);
        }
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}", params="sisalto")
    public String muokkaaSisalto(@PathVariable Long uutisId,@RequestParam String sisalto){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        if(!sisalto.equals("")){
          uutinen.setSisalto(sisalto);
        uutisRepo.save(uutinen); 
        }
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}/lisaakategoria", params={"lisaakategoria"})
    public String lisaaKategoria(@PathVariable Long uutisId,@RequestParam int lisaakategoria){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        if(!uutinen.getKategoriat().contains(Kategoria[lisaakategoria])){
            uutinen.LisaaKategoria(Kategoria[lisaakategoria]);
            uutisRepo.save(uutinen);
        }
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}/poistakategoria", params="poistakategoria")
    public String poistaKategoria(@PathVariable Long uutisId,@RequestParam String poistakategoria){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        uutinen.PoistaKategoria(poistakategoria);
        uutisRepo.save(uutinen);
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}/lisaakirjoittaja", params="lisaakirjoittaja")
    public String lisaaKirjoittaja(@PathVariable Long uutisId,@RequestParam String lisaakirjoittaja){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        
        if(!uutinen.getKirjoittajat().contains(lisaakirjoittaja)&&!lisaakirjoittaja.equals("")){
            uutinen.LisaaKirjoittaja(lisaakirjoittaja);
        }
        uutisRepo.save(uutinen);
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @RequestMapping(value="/muokkaus/{uutisId}/poistakirjoittaja", params="poistakirjoittaja")
    public String poistaKirjoittaja(@PathVariable Long uutisId,@RequestParam String poistakirjoittaja){
        Uutinen uutinen = uutisRepo.getOne(uutisId);
        uutinen.PoistaKirjoittaja(poistakirjoittaja);
        uutisRepo.save(uutinen);
        return "redirect:/muokkaus/"+uutisRepo.findByIdentifierAndOtsikko
                    (uutinen.getIdentifier(),uutinen.getOtsikko()).getId();
    }
    
    @PostMapping("/muokkaus/{uutisId}/lisaakuva")
    public String addKuva(@RequestParam("file") MultipartFile file, @PathVariable Long uutisId) throws IOException {
        try {
            if(file.getContentType().equals("image/png")
                ||file.getContentType().equals("image/jpeg")
                ||file.getContentType().equals("image/gif")){
            FileObject fo = new FileObject();
            fo.setContent(file.getBytes());
            KuvaRepo.save(fo);
            Uutinen uutinen = uutisRepo.getOne(uutisId);
            uutinen.lisaaKuva(KuvaRepo.getOne(fo.getId()).getId());
            uutisRepo.save(uutinen);
        }
        } catch (MaxUploadSizeExceededException e) {
            
        }
        
        return "redirect:/muokkaus/"+uutisId;
    }
    
    @GetMapping(path = "/kuvat/{id}/content", produces = {"image/png", "image/jpeg", "image/gif"})
    @ResponseBody
    public byte[] getKuva(@PathVariable Long id) {
        return KuvaRepo.getOne(id).getContent();
    }
}
