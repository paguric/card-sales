package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiCardController {

    @Autowired
    private final CardService cardService;

    @Autowired
    public ApiCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(cardService.getCards());
    }

    @GetMapping("/card/{id}")
    public Card getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @PostMapping("/card")
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = cardService.saveCard(card);
        return ResponseEntity.ok(savedCard);
    }
}