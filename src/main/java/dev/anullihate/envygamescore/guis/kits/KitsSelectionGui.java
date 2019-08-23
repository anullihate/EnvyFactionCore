package dev.anullihate.envygamescore.guis.kits;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import dev.anullihate.envygamescore.EnvyGamesCore;
import dev.anullihate.envygamescore.guis.Gui;
import dev.anullihate.envygamescore.objects.kits.Kit;
import dev.anullihate.envygamescore.objects.kits.Kits;

import java.util.Map;

public class KitsSelectionGui extends FormWindowSimple implements Gui {

    public KitsSelectionGui(Player player) {
        super("KitsSelectionGui", "");

        Kits kits = EnvyGamesCore.kits;
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

        Kit kit = EnvyGamesCore.kits.getKit(kitName);
        kit.requestHandler(event.getPlayer());
    }
}
