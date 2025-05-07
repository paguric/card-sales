# Card sales

## PP4 - Spring MVC

### Inizializzare un nuovo progetto
Per inizializzare un progetto in Spring, usare l'[initializer ufficiale](https://start.spring.io/) con le seguenti dependency: *Spring Web*, *Spring Web DevTools*, *Lombok*.

### Risorse statiche
I file HTML / CSS / JavaScript statici vanno spostati in `src/main/resources/static`.

### Template
Le risorse gestite da endpoint implementati nei *controller* dell'applicazione vanno nella cartella `src/main/resources/resources`.
**Nota:** un template può accedere a una risorsa statica tramite path relativo partendo da `"/"`; ad esempio, posso accedere alla cartella dei css con ```href="/css/style.css"```.

### La classe Application.java
L'entry point di un'applicazione Spring dev'essere annotato con `@SpringBootApplication`
```
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### MVC
L'architettura MVC si genera a partire dalla cartella `src/main/java/ch/supsi/web/cardgames` creando i rispettivi package (model, view, controller, service, ...).

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

**Nota:** nel caso dell'enum, nell'html bisogna impostare il value esattamente come i campi dell'enum, ad esempio `<option value="NEAR_MINT">Near Mint</option>`.

#### View
Nella PP4 non viene fatto uso di view. Le pagine vengono create come `ResponseEntity<String>` in `MainController.java`.

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

`MainController.java` si occupa di definire gli endpoint tramite annotazioni (`@GetMapping`, `@PostMapping`, ecc.).
Senza *Thymeleaf*, siamo costretti a restituire l'intero testo "grezzo" della pagina HTML che vogliamo mostrare, opportunamente modificato.

#### Service
Si occupa di gestire le carte, sostituendo momentaneamente l'accesso al database.

## PP5 - Thymeleaf
Con *Thymeleaf* possiamo mappare il nome delle *view* al tipo di ritorno dei metodi-endpoint nei *controller*. Basta che il *controller* faccia il return del nome della view in una stringa (in minuscolo).
In questo modo, *Thymeleaf* compilerà in automatico i campi e restituirà la pagina HTML corrispondente.
**Nota:** la view non è altro che la pagina HTML salvata nelle risorse.

### PathVariable
```
@GetMapping("/api/employees/{id}/{name}")
public ResponseEntity<String> String getEmployeesByIdAndName(@PathVariable String id, @PathVariable String name) {
    return ResponseEntity.ok("ID: " + id + ", name: " + name);
}
```

### RequestParam
```
@PostMapping("/person/new")
public String submit(@RequestParam String name, Model model) {
    model.addAttribute("person", new Person(name));
    return "personView";
}
```
Example: `POST /person/new?name=Marco`

In alternativa, al posto di richiedere tutti i campi di Person, ad esempio, si puo' richiedere l'oggetto stesso.
```
@PostMapping("/person/new")
public String submit(Person person, Model model) {
    model.addAttribute("person", person);
    return "personView";
}
```

E per evitare di dover chiamare `model.addAttribute(...)`, si puo' fare cosi':
```
@PostMapping("/person/new")
public String submit(@ModelAttribute("person") Person person) {
    // code that somehow uses the person object
    return "personView";
}
```

### Redirects
```
@PostMapping("/person/{id}/edit")
public String put(@PathVariable int id, @ModelAttribute("person") Person person){
    // do something
    return "redirect:/person/{id}"
}
```

### Thymeleaf attributes
- `<p th:text="${msg.welcome}">Welcome everyone!</p>`
    - If `msg.getWelcome()` returns the string "Hello" then the result will be: `<p>Hello</p>`.
    - In general, if you want to make an attribute of an HTML element dynamic, simply add th: to the attribute. For example:
        - `<img th:alt="${info}">`
        - `<p th:title="${product.title}" />`
- th:each repeats the tag as many times as there are elements in the array or list returned by the expression:
    - ```<tr th:each="book : ${books}">
        <td th:text="${book.id}">ID</td>
        <td th:text="${book.title}">Title</td>
    </tr>```
- All expressions that start with @{} are called link expressions and are used to create URLs and are very useful because they can add the context in which the application is (th:href, th:src, th:action).
    - Such an expression: `<a th:href="@{/order/list}">...</a>` would be converted to `<a href="/myapplication/order/list">...</a>`
    - Link expression also works perfectly for src (in the img tag for example) or action (in forms) attributes.
    - You can create the link dynamically, for example: `<a th:href="@{'/person/'+${person.id}}">detail</a>`
- Using th:if it is possible to place a condition on the display of a tag.
    - In this case only if the user is an administrator the first div will be displayed and only if variable.something is equal to null will the second div be displayed: `<div th:if="${user.isAdmin()}"> ... <div th:if="${variable.something} == null"> ...`
- The th:object attribute makes it easy to access the fields of an object in a given context.
    - ```<form th:object="${person.address}">
        <input name="line1" th:value="*{line1}">
        <input name="country" th:value="*{country}">
    </form>```
- To add an input to a form: `<input type="text" th:field="*{firstname}" />`
- Thymeleaf offers a set of utility objects that can help us with common and recurring tasks. For example:
    - `<p th:text="${#dates.format(date, 'dd-MM-yyyy HH:mm')}"></p>`
    - `<p th:text="${#numbers.formatDecimal(num,2,3)}"></p>`
    - `<p th:text="${#strings.isEmpty(string)}"></p>`
    - `<p th:text="${#strings.substring(name,3,5)}"></p>`
    - `<p th:text="${#strings.replace(name,'las','ler')}"></p>`
    - `<span th:text="${#lists.size(prod.comments)}">2</span>`
    - `<p th:if="${not #lists.isEmpty(prod.comments)}">comments</p>`

    