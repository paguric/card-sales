package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_condition")
    private CardCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Lob
    private byte[] attachment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}