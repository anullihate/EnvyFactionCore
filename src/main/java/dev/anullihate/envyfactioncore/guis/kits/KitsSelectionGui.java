package dev.anullihate.envyfactioncore.guis.kits;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.guis.Gui;
import dev.anullihate.envyfactioncore.objects.kits.Kit;
import dev.anullihate.envyfactioncore.objects.kits.Kits;

public class KitsSelectionGui extends FormWindowSimple implements Gui {

    public KitsSelectionGui(Player player) {
        super("KitsSelectionGui", "");

        Kits kits = EnvyFactionCore.kits;
        kits.getKits().forEach((kitName, kit) -> {
            if (kit.testPermission(player)) {
                addButton(new SelectedKitButton(kitName, kitName));
            }
        });

        if (getButtons().size() == 0) {
            setContent("You haven't unlock any Kits yet!");
        }
    }

    @Override
    public void onPlayerFormResponse(PlayerFormRespondedEvent event) {
        if (!(getResponse().getClickedButton() instanceof SelectedKitButton)) {
            return;
        }
        SelectedKitButton selectedKitButton = (SelectedKitButton) getResponse().getClickedButton();

        String kitName = selectedKitButton.getKitName();

        Kit kit = EnvyFactionCore.kits.getKit(kitName);
        if (event.getPlayer().isOp()) {
            kit.addTo(event.getPlayer());
        } else {
            kit.requestHandler(event.getPlayer());
        }
    }
}
