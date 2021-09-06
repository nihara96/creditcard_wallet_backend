package com.example.demo.appUser;


import com.example.demo.card.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1 //increment value
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    private Long id;
    private String username;
    private String email;
    private String password;


    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private Set<Card> cards = new HashSet<>();

}
