package Skyjo_UTBM_Edition.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This is a texture repertory to help with changing the textures of the game without having to change the code everytime.
 */
public enum SUETexture {
    CARD_BACK("resources/textures/Cards/card_back.png", "card_back"),
    MAT_TEXTURE("resources/textures/mat_background.png", "mat_background"),

    // -2 points
    DEBT2(),

    // -1 points
    DEBT1(),

    // 0 points
    VALIDATION(),

    //1 point,
    CI00(),
    CI01(),
    CI02(),
    ID00(),
    MDA1(),
    MDB1(),

    // 2 points
    ID01_CI00(),
    ID02_CI02(),
    ID03_CI01(),
    MG6T(),

    // 3 points
    CM1A(),
    CM1B(),
    DR10(),
    EC10(),
    EC11(),
    EE10(),
    EI11(),
    GE00(),
    GE10(),
    GE11(),

    // 4 points
    HN03(),
    LC00(),
    LC01(),
    LC02(),
    LC08(),
    LE01(),
    LE02(),
    LE05(),
    LE07(),
    LE08(),

    // 5 points
    AM02(),
    AR03(),
    AR05(),
    AV00(),
    CC01(),
    CC02(),
    CC03(),
    CC04(),
    DR01(),
    DR02(),

    // 6 points
    AC20(),
    DT20(),
    EL21(),
    EL22(),
    LE03(),
    LG03(),
    LO21(),
    LP25(),
    LS03(),
    MT1B(),

    // 7 points
    HE10_LE09(),
    IF1A_LG00(),
    IF1B_LG01(),
    IF2A_LG02(),
    IF2B_LG05(),
    IF3A_LI00(),
    IF3B_LI01(),
    IF3E_LJ00(),
    LP2A_LJ01(),
    LP2B_LJ02(),

    // 8 points
    MAA1_DR04(),
    MAA2_EC01(),
    MAC1_EC02(),
    MAC2_EC05(),
    MBE1_EC06(),
    MBE2_EC07(),
    MBE3_EE01(),
    MBE4_EI02(),
    MG10_EI03(),
    MG11_EI04(),

    // 9 points
    LJ08_GE02(),
    LK00_AM02(),
    LK01_AR05(),
    LK08_CC03(),
    LR00_AV00(),
    LR01_CC02(),
    LS00_AV00(),
    LS01_CC04(),
    LS02_DR01(),
    LS04_DR02(),

    // 10 points
    HT07_GE03(),
    HT08_GE06(),
    HT12_GE07(),
    MG01_GE08(),
    MG03_GE09(),
    MG04_HE05(),
    MG05_HE08(),
    MG07_HE09(),
    PH01_HN01(),
    PH02_HT01(),

    // 11 points
    MG12_MG6T_EI05(),
    MT3F_GE01(),
    PM1A_PM1B_EI10(),
    PS1A_PS1B_EI12(),
    PS2A_PS2B_EV00(),
    PS22_EV03(),
    PS28_EV04(),
    SO10_TI10_EV01(),
    TN20_EV05(),
    TNIA_TN1B_EV02(),

    // 12 points
    MT2B_SQ20(),
    MT3E_ST20(),
    MT3F_ST10(),
    PI40_TN20(),
    PS22_TN22(),
    PS25_TZ20(),
    PS26_MT3F(),
    PS27_LE03(),
    PS28_LG03(),
    PS29_LS03();

    private final ImageIcon image;
    private final String name;

    SUETexture (String path, String name) {
        this.image = new ImageIcon(path);
        this.name = name;
    }

    /**
     * Automatically create the name of the card and the path to the texture using only the name provided in the enumerations
     */
    SUETexture () {
        this.name = this.toString().replace("_", "+");
        String path = "resources/textures/Cards/" + name + ".png";
        this.image = new ImageIcon(path);
    }

    public Image getImage () {
        return image.getImage();
    }

    public static SUETexture getTexture (String name) {
        for (SUETexture texture : SUETexture.values()) {
            if (texture.name.equals(name)) {
                return texture;
            }
        }
        return null;
    }
}
