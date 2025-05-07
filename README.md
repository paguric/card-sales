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

#### View

#### Controller

#### Service
