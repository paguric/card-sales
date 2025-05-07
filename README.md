# Card sales

## PP4 - Spring MVC

### Inizializzare un nuovo progetto
Per inizializzare un progetto in Spring, usare l'[initializer ufficiale](https://start.spring.io/) con le seguenti dependency: *Spring Web*, *Spring Web DevTools*, *Lombok*.

### Risorse statiche
I file HTML / CSS / JavaScript statici vanno spostati in ```src/main/resources/static```.

### Template
Le risorse gestite da endpoint implementati nei *controller* dell'applicazione vanno nella cartella ```src/main/resources/resources```.
**Nota:** un template può accedere a una risorsa statica tramite path relativo partendo da ```"/"```; ad esempio, posso accedere alla cartella dei css con ```href="/css/style.css"```.

### La classe Application.java
L'entry point di un'applicazione Spring dev'essere annotato con ```@SpringBootApplication```
```
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### MVC
L'architettura MVC si genera a partire dalla cartella ```src/main/java/ch/supsi/web/cardgames``` creando i rispettivi package (model, view, controller, service, ...).

#### Model
@Getter @Setter @NoArgsConstructor @ToString
public class Card {
    private String name;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String author;
    private CardCondition condition;
}

**Nota:** nel caso dell'enum, nell'html bisogna impostare il value esattamente come i campi dell'enum, ad esempio ```<option value="NEAR_MINT">Near Mint</option>```.

#### View
Nella PP4 non viene fatto uso di view. Le pagine vengono create come ```ResponseEntity<String>``` in ```MainController.java```.

#### Controller
```
@RestController
public class MainController {
    private final MainService mainService = new MainService();
    private final CardService cardService = new CardService();

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(this.mainService.getAndPopulateHTMLHomePage(cardService.getCards()));
    }
    ...
}
```

```MainController.java``` si occupa di definire gli endpoint tramite annotazioni (```@GetMapping```, ```@PostMapping```, ecc.).
Senza *Thymeleaf*, siamo costretti a restituire l'intero testo "grezzo" della pagina HTML che vogliamo mostrare, opportunamente modificato.

#### Service
Si occupa di gestire le carte, sostituendo momentaneamente l'accesso al database.
