package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.*;
import ch.supsi.web.cardgames.repository.CardRepository;
import ch.supsi.web.cardgames.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbInitializerService {

    /*
    @Value("${upload.path}")
    private String uploadPath;
     */

    private final CardRepository cardRepository;

    private final UserService userService;

    public DbInitializerService(CardRepository cardRepository, UserRepository userRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html
    //https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/postconstruct-and-predestroy-annotations.html
    @PostConstruct
    public void init() {
        List<User> users = null;
        users = initUsers();
        /*
        try {
            users = initUsers();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
         */
        initCards(users);
    }

    private List<User> initUsers() /* throws BadRequestException */ {
        long count = userService.getAllUsers().size();
        List<User> users = new ArrayList<>();
        if(count == 0) {

            // Initialize the database with some users
            users.add(userService.saveUser(new User("admin", "Admin", "Admin", UserRole.ADMIN, "admin")));
            users.add(userService.saveUser(new User("testuser", "Test", "User", UserRole.USER, "testuser")));
            users.add(userService.saveUser(new User("testuser2", "Test2", "User2", UserRole.USER, "testuser2")));

        }
        return users;
    }

    private void initCards(List<User> users){
        long count = cardRepository.count();
        if(count == 0) {
            /*
            try {
                cardRepository.save(new Card("Red Eyes Dark Dragoon", "Non può essere distrutto dagli effetti delle carte. Nessun giocatore può scegliere come bersaglio questa carta con gli effetti delle carte. Durante la tua Main Phase: puoi distruggere 1 mostro controllato dal tuo avversario e, se lo fai, infliggi al tuo avversario danno pari all'ATK originale di quel mostro. Puoi utilizzare questo effetto un numero di volte per turno fino al numero di Mostri Normali utilizzati come Materiale da Fusione per questa carta. Una volta per turno, quando viene attivata una carta o un effetto (Effetto Rapido): puoi scartare 1 carta; annulla l'attivazione e, se lo fai, distruggi quella carta e, se fai quello, questa carta guadagna 1000 ATK.", new Date(), "Paso", Files.readAllBytes(Paths.get(uploadPath, "red-eyes-dark-dragoon.jpg")), CardCondition.MINT, CardType.YU_GI_OH, users.get(1)));
                cardRepository.save(new Card("Pikachu", "Holographic Pikachu V card featuring the iconic electric Pokémon with enhanced attacks and 170 HP. Shows Pikachu surrounded by lightning bolts with vibrant yellow artwork.", new Date(), "Ash", Files.readAllBytes(Paths.get(uploadPath, "pikachu.png")), CardCondition.MINT, CardType.POKEMON, users.get(1)));
                cardRepository.save(new Card("Charizard", "Rare Charizard card from the Pokémon 151 collection depicting the fire dragon with spread wings and blazing flame tail. Features high HP and the powerful 'Royal Blaze' attack", new Date(), "Ash", Files.readAllBytes(Paths.get(uploadPath, "charizard.png")), CardCondition.NEAR_MINT, CardType.POKEMON, users.get(1)));
                cardRepository.save(new Card("Blue-Eyes White Dragon", "Legendary Yu-Gi-Oh! dragon card with 3000 ATK and 2500 DEF. Features a majestic white dragon with piercing blue eyes, ready to unleash its devastating attack", new Date(), "Kaiba", Files.readAllBytes(Paths.get(uploadPath, "blue-eyes-dragon.jpg")), CardCondition.MINT, CardType.YU_GI_OH, users.get(2)));
                cardRepository.save(new Card("Dark Magician", "Iconic spellcaster from Yu-Gi-Oh! with 2500 ATK and 2100 DEF. Shows the purple-robed magician wielding his staff, known for his powerful dark magic abilities.", new Date(), "Yugi", Files.readAllBytes(Paths.get(uploadPath, "dark-magician.jpeg")), CardCondition.NEAR_MINT, CardType.YU_GI_OH, users.get(2)));
                cardRepository.save(new Card("Swords to Plowshares", "Exile target creature. Its controller gains life equal to its power.", new Date(), "MtG Player", Files.readAllBytes(Paths.get(uploadPath, "sword-plowshares.png")), CardCondition.NEAR_MINT, CardType.MAGIC, users.get(2)));

            } catch (IOException e) {
                throw new RuntimeException("Failed to load initial card images", e);
            }
             */
        }
    }
}
