package code.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MakeStrikeCommand extends ConsoleCommand {

    public MakeStrikeCommand() {
        this.requiresPlayer = true;
        this.minExtraTokens = 1;
        this.maxExtraTokens = 1;
    }

    public void execute(String[] tokens, int depth) {
        if (tokens.length != 2) {
            cmdDrawHelp();
        } else {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, ConvertHelper.tryParseInt(tokens[1], 0)));
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        if (tokens[depth].matches("\\d+")) {
            complete = true;
        }
        return ConsoleCommand.smallNumbers();
    }

    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* draw [amt]");
    }
}
