package Skyjo_frenic.gui;

/**
 * Add QoL improvements to the GUI elements
 * Might not be useful...
 */
public interface SFCComponent {
    void disappear ();

    /**
     * Using reveal instead of show because show is already a method in the JComponent class
     */
    void reveal();
}
