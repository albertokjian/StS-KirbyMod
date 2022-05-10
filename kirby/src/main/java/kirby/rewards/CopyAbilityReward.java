package kirby.rewards;

import basemod.abstracts.CustomReward;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import kirby.KirbyMod;

import java.util.ArrayList;

public class CopyAbilityReward extends CustomReward {
    public static final String ID = KirbyMod.makeID(CopyAbilityReward.class.getSimpleName());
    public static final String[] TEXT;

    public CopyAbilityReward(AbstractCard card) {
        super(ImageMaster.REWARD_CARD_NORMAL, TEXT[0], RewardType.CARD);
        this.cards = new ArrayList<AbstractCard>() {{ add(card); }};
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[1]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }

        return false;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    }
}
