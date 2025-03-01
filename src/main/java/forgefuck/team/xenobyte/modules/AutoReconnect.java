package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.client.multiplayer.ServerData;

public class AutoReconnect extends CheatModule {

    private ServerData lastServer;
    private boolean reconnecting;

    public AutoReconnect() {
        super("AutoReconnect", Category.MISC, PerformMode.TOGGLE);
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (cfgState && utils.mc().func_147104_D() != null) {
            lastServer = utils.mc().func_147104_D();
            reconnecting = true;
        }
    }

    @Override
    public void onTick(boolean inGame) {
        if (reconnecting && !inGame && lastServer != null) {
            utils.mc().displayGuiScreen(new net.minecraft.client.gui.GuiConnecting(utils.mc().currentScreen, utils.mc(), lastServer));
            reconnecting = false;
            lastServer = null; // Reset after reconnecting
        }
    }

    @Override
    public String moduleDesc() {
        return lang.get("Automatically reconnects to the last server when disconnected", "Автоматически переподключается к последнему серверу при отключении");
    }
}
