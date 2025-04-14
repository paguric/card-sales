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
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/card")
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = cardService.saveCard(card);
        return ResponseEntity.ok(savedCard);
    }

    @PutMapping("/card/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @RequestBody Card card) {
        return cardService.getCardById(id)
                .map(existingCard -> {
                    card.setId(id);
                    Card updatedCard = cardService.saveCard(card);
                    return ResponseEntity.ok(updatedCard);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        if (cardService.getCardById(id).isPresent()) {
            cardService.deleteCard(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}