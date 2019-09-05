package dev.anullihate.envyfactioncore.guis.kits;

import cn.nukkit.form.element.ElementButton;

public class SelectedKitButton extends ElementButton {

    private String kitName;

    public SelectedKitButton(String text, String kitName) {
        super(text);
        this.kitName = kitName;
    }

    public String getKitName() {
        return kitName;
    }
}
