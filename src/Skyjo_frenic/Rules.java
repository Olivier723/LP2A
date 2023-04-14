package Skyjo_frenic;


/**
 * Might also be useless but I'll keep it for now
 * TODO: Remove if useless
 */
public enum Rules {
    MAX_CARD_PER_HAND (12),
    MAX_CARDS_IN_DECK (108);

    private final int value;

    Rules (int value) {
        this.value = value;
    }

    public int getValue () {
        return this.value;
    }

}
