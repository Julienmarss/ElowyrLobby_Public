package fr.elowyr.lobby.utils.menus;

public enum Size {
    UNE_LIGNE(9),
    DEUX_LIGNE(18),
    TROIS_LIGNE(27),
    QUATRE_LIGNE(36),
    CINQ_LIGNE(45),
    SIX_LIGNE(54);

    private int size;

    private Size(int size) {
        this.size = size;
    }

    public static Size fit(int slots) {
        if (slots < 10) {
            return UNE_LIGNE;
        } else if (slots < 19) {
            return DEUX_LIGNE;
        } else if (slots < 28) {
            return TROIS_LIGNE;
        } else if (slots < 37) {
            return QUATRE_LIGNE;
        } else {
            return slots < 46 ? CINQ_LIGNE : SIX_LIGNE;
        }
    }

    public int getSize() {
        return this.size;
    }
}

