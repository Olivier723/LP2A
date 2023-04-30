package Skyjo_frenic.gui;

import Skyjo_frenic.basics.Card;

import java.awt.*;

public class CardButton extends SFCButton{
    private Card associatedCard;
    private static final double GOLDEN_RATIO = 1.61803399F;
    private static final Dimension minimumSize = new Dimension(100, (int) (100 * GOLDEN_RATIO));
    private static final Dimension maximumSize = new Dimension(400, (int) (400 * GOLDEN_RATIO));


    public Card getAssociatedCard () {
        return associatedCard;
    }

    public void setAssociatedCard (Card associatedCard) {
        this.associatedCard = associatedCard;
        super.addActionListener(e -> onClick());
    }

    public CardButton (){
        super("", Texture.CARD_BACK);
        Dimension size = SFCFrame.getScreenSize();
        size.width /= 12;
        size.height = (int) (size.width * GOLDEN_RATIO);
        super.setMinimumSize(minimumSize);
        super.setMaximumSize(maximumSize);
        super.setPreferredSize(size);
    }

    private void onClick() {
        if(associatedCard != null) {
            associatedCard.flip();
            super.setBackgroundImage(associatedCard.getCurrentTexture());
        }
    }
}
