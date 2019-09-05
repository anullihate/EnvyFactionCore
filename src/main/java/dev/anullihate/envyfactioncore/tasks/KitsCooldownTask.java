package dev.anullihate.envyfactioncore.tasks;

import cn.nukkit.scheduler.PluginTask;
import dev.anullihate.envyfactioncore.EnvyFactionCore;
import dev.anullihate.envyfactioncore.objects.kits.Kit;

public class KitsCooldownTask extends PluginTask<EnvyFactionCore> {

    private int min = 0;
    private int everyMin = 5;

    public KitsCooldownTask(EnvyFactionCore core) {
        super(core);
    }

    @Override
    public void onRun(int i) {
        for (Kit kit : EnvyFactionCore.kits.getKits().values()) {
            kit.processCooldown();
        }

        if (++this.min % everyMin == 0) {
            for (Kit kit : EnvyFactionCore.kits.getKits().values()) {
                kit.save();
            }
        }
    }
}
