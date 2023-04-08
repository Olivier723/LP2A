package HalfLife3.basics;

import javax.swing.*;
import java.awt.*;

public class Card extends JButton {
    private int value;
    private Image texture;

    public Card (int value, Image img){
        this.value = value;
        this.texture = img;
    }
}
