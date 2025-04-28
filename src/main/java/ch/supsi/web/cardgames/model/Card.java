package ch.supsi.web.cardgames.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card {
    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String author;
    private CardCondition condition;
    private CardType cardType;
    @Lob
    private byte[] attachment;
}