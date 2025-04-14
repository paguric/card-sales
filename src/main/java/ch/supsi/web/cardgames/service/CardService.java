package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.Card;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CardService {
    @Getter
    private final List<Card> cards = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Optional<Card> getCardById(Long id) {
        return cards.stream()
                .filter(card -> card.getId().equals(id))
                .findFirst();
    }

    public Card saveCard(Card card) {
        if (card.getId() == null) {
            card.setId(idGenerator.getAndIncrement());
            cards.add(card);
        } else {
            cards.removeIf(c -> c.getId().equals(card.getId()));
            cards.add(card);
        }
        return card;
    }

    public void deleteCard(Long id) {
        cards.removeIf(card -> card.getId().equals(id));
    }
}