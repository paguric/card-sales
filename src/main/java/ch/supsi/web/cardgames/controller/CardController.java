package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.model.CardCondition;
import ch.supsi.web.cardgames.model.CardType;
import ch.supsi.web.cardgames.model.UserRole;
import ch.supsi.web.cardgames.service.CardService;
import ch.supsi.web.cardgames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cards", cardService.getCards());
        return "index";
    }

    @GetMapping("/card/{id}")
    public String getCard(@PathVariable Long id, Model model) {
        Card card = cardService.getCardById(id);
        model.addAttribute("card", card);
        // Also add logged user & admin role enum to the model. It will be used to determine whether to display edit and delete buttons
        ch.supsi.web.cardgames.model.User loggedUser = userService.getCurrentlyLoggedUser();
        model.addAttribute("user", loggedUser);
        model.addAttribute("adminRole", UserRole.ADMIN);
        return "card-detail";
    }

    @GetMapping("/card/{id}/image")
    public ResponseEntity<Resource> getCardImage(@PathVariable Long id) {
        Card card = cardService.getCardById(id);

        byte[] image = card.getAttachment();

        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(image);

        MediaType contentType = MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }

    @GetMapping("/card/new")
    public String newCardForm(Model model) {
        model.addAttribute("card", new Card());
        // Also add enum types to model
        model.addAttribute("cardTypes", CardType.values());
        model.addAttribute("cardConditions", CardCondition.values());
        return "card-form";
    }

    @PostMapping("/card/new")
    public String createCard(@ModelAttribute Card card, @RequestParam("image") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            card.setAttachment(file.getBytes());
        }

        ch.supsi.web.cardgames.model.User loggedUser = userService.getCurrentlyLoggedUser();
        card.setUser(loggedUser);

        card.setAuthor(loggedUser.getUsername());

        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}/edit")
    public String editCardForm(@PathVariable Long id, Model model) {
        // Check whether the current user is the owner of the card, or is admin
        // TODO
        Card card = cardService.getCardById(id);
        model.addAttribute("card", card);
        return "card-form";
    }

    @PostMapping("/card/{id}/edit")
    public String updateCard(@PathVariable Long id, @ModelAttribute Card card) {
        card.setId(id);
        cardService.saveCard(card);
        return "redirect:/";
    }

    @GetMapping("/card/{id}/delete")
    public String deleteCard(@PathVariable Long id) {
        // Check whether the current user is the owner of the card, or is admin
        // TODO
        cardService.deleteCard(id);
        return "redirect:/";
    }

}