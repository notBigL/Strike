package code.commands;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class MakeStrikeCommand extends ConsoleCommand {

    public MakeStrikeCommand() {
        this.requiresPlayer = true;
        this.minExtraTokens = 1;
        this.maxExtraTokens = 2;
    }

    public void execute(String[] tokens, int depth) {
        String cardName = this.getCardID(tokens);
        AbstractCard c = CardLibrary.getCard(cardName);
        if (c != null) {
            if (tokens[depth + 1].equals("true") || tokens[depth + 1].isEmpty()) {
                skimThroughAllPiles(true, c);
            } else if (tokens[depth + 1].equals("false")) {
                skimThroughAllPiles(false, c);
            }
        } else {
            DevConsole.log("could not find card " + cardName);
        }
    }

    private void skimThroughAllPiles(boolean addTag, AbstractCard c) {
        skimThroughGivenPile(addTag, AbstractDungeon.player.hand, c);
        skimThroughGivenPile(addTag, AbstractDungeon.player.drawPile, c);
        skimThroughGivenPile(addTag, AbstractDungeon.player.discardPile, c);
        skimThroughGivenPile(addTag, AbstractDungeon.player.exhaustPile, c);
    }

    private void skimThroughGivenPile(boolean addTag, CardGroup player, AbstractCard c) {
        for (AbstractCard card : new ArrayList<>(player.group)) {
            if (c.cardID.equals(card.cardID) && addTag && !card.hasTag(AbstractCard.CardTags.STRIKE)) {
                CardTags.addTags(card, AbstractCard.CardTags.STRIKE);
                consoleLogForSuccessfulCommand(true, card.cardID);
            } else if (c.cardID.equals(card.cardID) && !addTag && card.hasTag(AbstractCard.CardTags.STRIKE)) {
                CardTags.removeTags(card, AbstractCard.CardTags.STRIKE);
                consoleLogForSuccessfulCommand(false, card.cardID);
            }
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = ConsoleCommand.getCardOptions();
        ArrayList<String> result = new ArrayList<>();
        result.add("true");
        result.add("false");

        if (options.contains(tokens[depth])) {
            if (tokens.length > depth + 1) {
                if (result.contains(tokens[depth + 1])) {
                    ConsoleCommand.complete = true;
                } else if (tokens.length > depth + 2) {
                    tooManyTokensError();
                }
                return result;
            }
        } else if (tokens.length > depth + 1) {
            tooManyTokensError();
        }
        return options;
    }

    public void errorMsg() {
        cmdMakeStrikeHelp();
    }

    private static void cmdMakeStrikeHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* makestrike [id]");
    }

    private String getCardID(String[] tokens) {
        String[] cardNameArray = Arrays.copyOfRange(tokens, 1, 2);
        String cardName = String.join(" ", cardNameArray);
        if (BaseMod.underScoreCardIDs.containsKey(cardName)) {
            cardName = BaseMod.underScoreCardIDs.get(cardName);
        }

        return cardName;
    }

    private void consoleLogForSuccessfulCommand(boolean addedTag, String cardID) {
        if (addedTag) {
            DevConsole.log("Strike tag added to: " + cardID);
        } else {
            DevConsole.log("Strike tag removed from: " + cardID);
        }
    }
}

