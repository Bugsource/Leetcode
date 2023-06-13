package Company.Flexport;

import java.util.Map;

class Token {
    private String name;

    public Token(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Card {
    private Map<Token, Integer> cost;

    public Card(Map<Token, Integer> cost) {
        this.cost = cost;
    }

    public Map<Token, Integer> getCost() {
        return cost;
    }
}

class Player {
    private Map<Token, Integer> tokens;

    public Player(Map<Token, Integer> tokens) {
        this.tokens = tokens;
    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }

    public boolean canBuyCard(Card card) {
        Map<Token, Integer> cost = card.getCost();
        for (Map.Entry<Token, Integer> entry : cost.entrySet()) {
            Token token = entry.getKey();
            int required = entry.getValue();
            if (!tokens.containsKey(token) || tokens.get(token) < required) {
                return false;
            }
        }
        return true;
    }
}
