package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.Card;
import ch.supsi.web.cardgames.repository.CardRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CardService {
    @Autowired
    private CardRepository repository;

    public Card getCardById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Card saveCard(Card card) {
        return repository.save(card);
    }

    public void deleteCard(Long id) {
        repository.deleteById(id);
    }

    public List<Card> getCards() {
        return repository.findAll();
    }
}