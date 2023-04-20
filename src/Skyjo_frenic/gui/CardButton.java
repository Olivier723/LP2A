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

    public CardButton (Card associatedCard){
        super(associatedCard.toString());
        this.associatedCard = associatedCard;
        this.setBackgroundImage(associatedCard.getTexture());
        this.addActionListener(e -> associatedCard.onClick());
    }
}
