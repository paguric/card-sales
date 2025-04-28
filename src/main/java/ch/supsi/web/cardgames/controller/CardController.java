package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cards", cardService.getCards());
        return "index";
    }

    @GetMapping("/card/{id}")
    public String getCard(@PathVariable Long id, Model model) {
        return cardService.getCardById(id)
                .map(card -> {
                    model.addAttribute("card", card);
                    return "card-detail";
                })
                .orElse("redirect:/");
    }

    @GetMapping("/card/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        return "card-form";
    }

    @PostMapping("/card/new")
    public String createCard(@ModelAttribute Card card, @RequestParam("image") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            card.setAttachment(file.getBytes());
        }

        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}/edit")
    public String editCardForm(@PathVariable Long id, Model model) {
        return cardService.getCardById(id)
                .map(card -> {
                    model.addAttribute("card", card);
                    return "card-form";
                })
                .orElse("redirect:/");
    }

    @PostMapping("/card/{id}/edit")
    public String updateCard(@PathVariable Long id, @ModelAttribute Card card) {
        card.setId(id);
        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}/delete")
    public String deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
}