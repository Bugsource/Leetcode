package Company.Flexport;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TokenCardGame {
    HashSet<Card2> availableCards;
    List<Player2> players;

    public TokenCardGame() {
        this.availableCards = new HashSet<>();
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player2 player) {
        this.players.add(player);
    }

    public void addCard(Card2 card) {
        this.availableCards.add(card);
    }

    public boolean canPurchase(Player2 player, Card2 card) {
        HashMap<Token2, Integer> costMap = card.costMap;
        Integer yellowTokenCount = player.tokenMap.getOrDefault(Token2.YELLOW, 0);
        for (Map.Entry<Token2, Integer> tokenCost : costMap.entrySet()) {
            Token2 token = tokenCost.getKey();
            Integer cost = tokenCost.getValue();
            Integer availableTokens = player.tokenMap.getOrDefault(token, 0);
            Integer availableCardTokens = player.cardTokenMap.getOrDefault(token, 0);
            if (availableTokens + availableCardTokens < cost) {
                int tokensNeeded = cost - (availableTokens + availableCardTokens);
                if (yellowTokenCount < tokensNeeded) return false;
                yellowTokenCount -= tokensNeeded;
            }
        }
        return true;
    }

    public boolean purchase(Player2 player, Card2 card) {
        if (!canPurchase(player, card)) return false;
        HashMap<Token2, Integer> tokensToSpend = new HashMap<>();
        for (Map.Entry<Token2, Integer> tokenCost : card.costMap.entrySet()) {
            Token2 token = tokenCost.getKey();
            Integer cost = tokenCost.getValue();
            Integer availableTokens = player.tokenMap.getOrDefault(token, 0);
            Integer availableCardTokens = player.cardTokenMap.getOrDefault(token, 0);
            if (availableTokens + availableCardTokens < cost) {
                int yellowTokensNeeded = cost - (availableTokens + availableCardTokens);
                tokensToSpend.put(Token2.YELLOW, yellowTokensNeeded);
            } else {
                int tokensNeeded = cost - availableCardTokens;
                if (tokensNeeded > 0) {
                    tokensToSpend.put(token, tokensNeeded);
                }
            }
        }
        player.useTokens(tokensToSpend);
        player.addCard(card);
        availableCards.remove(card);
        return true;
    }
}

class Player2 {
    List<Card2> cardList;
    HashMap<Token2, Integer> tokenMap;
    HashMap<Token2, Integer> cardTokenMap;
    String name;

    public Player2(String name) {
        this.name = name;
        this.cardList = new ArrayList<>();
        this.tokenMap = new HashMap<>();
        this.cardTokenMap = new HashMap<>();
    }

    public void addCard(Card2 card) {
        cardList.add(card);
        cardTokenMap.put(card.value, cardTokenMap.getOrDefault(card.value, 0) + 1);
    }

    public void addTokens(HashMap<Token2, Integer> tokens) {
        for (Map.Entry<Token2, Integer> entry : tokens.entrySet()) {
            tokenMap.put(entry.getKey(), tokenMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    public void useTokens(HashMap<Token2, Integer> tokens) {
        for (Map.Entry<Token2, Integer> entry : tokens.entrySet()) {
            Token2 token = entry.getKey();
            Integer count = entry.getValue();
            if (!tokenMap.containsKey(token) || tokenMap.get(token) < count) {
//                 Log error, fatal
                continue;
            }
            tokenMap.put(token, tokenMap.get(token) - count);
        }
    }
}

class Card2 {
    HashMap<Token2, Integer> costMap;
    Token2 value;

    public Card2(HashMap<Token2, Integer> costMap, Token2 value) {
        this.costMap = costMap;
        this.value = value;
    }
}

enum Token2 {
    RED,
    BLUE,
    GREEN,
    BLACK,
    WHITE,
    YELLOW
}