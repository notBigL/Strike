package code.commands;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class MakeStrikeCommand extends ConsoleCommand {

    public MakeStrikeCommand() {
        this.requiresPlayer = true;
        this.minExtraTokens = 1;
        this.maxExtraTokens = 1;
        this.simpleCheck = true;
    }

    public void execute(String[] tokens, int depth) {
        String cardName = this.getCardID(tokens);
        AbstractCard c = CardLibrary.getCard(cardName);
        if (c != null) {
            DevConsole.log(c.name);
        } else {
            DevConsole.log("could not find card " + cardName);
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return ConsoleCommand.getCardOptions();
    }

    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* draw [amt]");
    }

    private String getCardID(String[] tokens) {
        String[] cardNameArray = Arrays.copyOfRange(tokens, 1, tokens.length);
        String cardName = String.join(" ", cardNameArray);
        if (BaseMod.underScoreCardIDs.containsKey(cardName)) {
            cardName = BaseMod.underScoreCardIDs.get(cardName);
        }

        return cardName;
    }
}
