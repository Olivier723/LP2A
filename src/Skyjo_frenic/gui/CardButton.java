package Skyjo_frenic.gui;

import Skyjo_frenic.basics.Card;

public class CardButton extends SFCButton{

    private Card associatedCard;

    public Card getAssociatedCard () {
        return associatedCard;
    }

    public void setAssociatedCard (Card associatedCard) {
        this.associatedCard = associatedCard;
    }

    public CardButton (Card card){
        super(card.toString(), card.getTexture().getImage());
        this.associatedCard = card;
        this.addActionListener(e -> this.associatedCard.onClick());
    }
}
