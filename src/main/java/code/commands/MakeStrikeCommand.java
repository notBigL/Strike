package code.commands;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

            for (AbstractCard handCard : new ArrayList<>(AbstractDungeon.player.hand.group)) {
                if (c.cardID.equals(handCard.cardID) && !handCard.hasTag(AbstractCard.CardTags.STRIKE)) {
                    CardTags.addTags(handCard, AbstractCard.CardTags.STRIKE);
                    System.out.println("Strike tag added to" + handCard.cardID);
                }
            }
            for (AbstractCard drawPileCard : new ArrayList<>(AbstractDungeon.player.drawPile.group)) {
                if (c.cardID.equals(drawPileCard.cardID) && !drawPileCard.hasTag(AbstractCard.CardTags.STRIKE)) {
                    CardTags.addTags(drawPileCard, AbstractCard.CardTags.STRIKE);
                    System.out.println("Strike tag added to" + drawPileCard.cardID);
                }
            }
            for (AbstractCard discardPileCard : new ArrayList<>(AbstractDungeon.player.discardPile.group)) {
                if (c.cardID.equals(discardPileCard.cardID) && !discardPileCard.hasTag(AbstractCard.CardTags.STRIKE)) {
                    CardTags.addTags(discardPileCard, AbstractCard.CardTags.STRIKE);
                    System.out.println("Strike tag added to" + discardPileCard.cardID);
                }
            }
            for (AbstractCard exhaustPileCard : new ArrayList<>(AbstractDungeon.player.exhaustPile.group)) {
                if (c.cardID.equals(exhaustPileCard.cardID) && !exhaustPileCard.hasTag(AbstractCard.CardTags.STRIKE)) {
                    CardTags.addTags(exhaustPileCard, AbstractCard.CardTags.STRIKE);
                    System.out.println("Strike tag added to" + exhaustPileCard.cardID);
                }
            }
        } else {
            DevConsole.log("could not find card " + cardName);
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return ConsoleCommand.getCardOptions();
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
        String[] cardNameArray = Arrays.copyOfRange(tokens, 1, tokens.length);
        String cardName = String.join(" ", cardNameArray);
        if (BaseMod.underScoreCardIDs.containsKey(cardName)) {
            cardName = BaseMod.underScoreCardIDs.get(cardName);
        }

        return cardName;
    }
}
