package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetextra.utils.CarpetExtensionTranslations;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.minecraft.server.command.handler.CommandRegistry;

import java.util.Map;

public class CarpetExtraServer implements CarpetExtension {
    @Override
    public String version() {
        return "carpet-extra";
    }

    public static void init(){
        // load mixin extra
        MixinExtrasBootstrap.init();
    }

    public static void loadExtension() {
        // add to carpet's extension list
        CarpetServer.manageExtension(new CarpetExtraServer());
    }

    @Override
    public void onGameStarted() {
        // let carpet handle the settings
        CarpetServer.settingsManager.parseSettingsClass(CarpetExtraSettings.class);
    }

    @Override
    public void registerCommands(CommandRegistry registry) {
        // register commands here
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return CarpetExtensionTranslations.getTranslationFromResourcePath(lang);
    }
}
