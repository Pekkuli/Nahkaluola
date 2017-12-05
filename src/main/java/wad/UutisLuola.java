
package wad;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class UutisLuola {
    
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

    public static void main(String[] args) {
        SpringApplication.run(UutisLuola.class, args);
    }
    
    public enum Kategoria {
    Kotimaa,
    Politiikka,
    Kaupunki,
    Ulkomaat,
    Talous,
    Urheilu,
    Kulttuuri
}
}