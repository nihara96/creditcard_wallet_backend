package com.example.demo.card;

import com.example.demo.appUser.AppUser;
import com.example.demo.appUser.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cards")
public class CardController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CardRepository cardRepository;

    @GetMapping
    List<Card> getCards(){return cardRepository.findAll(); }

    @PostMapping
    public Card createCard(@RequestBody Card card){
        cardRepository.save(card);
        return card;
    }

    @GetMapping("/{cardId}")
    public Card getCardById(@PathVariable Long cardId)
    {
       return cardRepository.findById(cardId).get();
    }

    @PostMapping("/user/")
    List<Card> getCardsByUsername(@RequestBody AppUser user)
    {
        AppUser appUser = appUserRepository.findByUsername(user.getUsername()).get();

        if(!appUser.getPassword().equals(user.getPassword()) ||
                !appUser.getUsername().equals(user.getUsername())||
                !appUser.getEmail().equals(user.getEmail())
        )
        {
            throw new IllegalStateException("wrong credentials");
        }

        return cardRepository.findByAppUser(appUser);
    }

    @PutMapping("/{cardNumber}/appUser/")
    public Card assignUserToCard(
            @RequestBody AppUser user,
            @PathVariable String cardNumber
    ){
        AppUser appUser = appUserRepository.findByUsername(user.getUsername()).get();

        if(!appUser.getPassword().equals(user.getPassword()) ||
                !appUser.getUsername().equals(user.getUsername())||
                !appUser.getEmail().equals(user.getEmail())
        )
        {
            throw new IllegalStateException("wrong credentials");
        }

        Card card = cardRepository.findByCardNumber(cardNumber);

      card.setAppUser(appUser);
      cardRepository.save(card);
      return card;
    }
}
