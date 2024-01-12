package code;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.interfaces.*;
import code.commands.MakeStrikeCommand;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModFile implements
        PostInitializeSubscriber {

    public static final String modID = "strike";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public ModFile() {
        BaseMod.subscribe(this);
    }


    public static void initialize() {
        ModFile thismod = new ModFile();
    }

    @Override
    public void receivePostInitialize() {
        ConsoleCommand.addCommand("makeStrike", MakeStrikeCommand.class);
    }
}
