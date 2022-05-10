package kirby.cards;

import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kirby.KirbyMod;
import kirby.actions.InhaleAction;
import kirby.characters.Kirby;

import static kirby.KirbyMod.makeCardPath;

public class Inhale extends AbstractKirbyCard {
    public static final String ID = KirbyMod.makeID(Inhale.class.getSimpleName());
    public static final String IMG = makeCardPath("Inhale.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Kirby.Enums.COLOR_PINK;
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    private static final int ENEMY_HP_LIMIT = 999;
    private static final int ENEMY_HP_INCREASE = 1;


    public Inhale() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ENEMY_HP_LIMIT;
    }

    // Can this card be used.
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }

        if (m == null) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo.currentHealth <= magicNumber) {
                    return true;
                }
            }
            return false;
        }

        if (m.currentHealth > magicNumber) {
            canUse = false;
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }

        return canUse;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
        AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("KIRBY_INHALE"));
        AbstractDungeon.actionManager.addToBottom(new InhaleAction(p, m));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
        AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(ENEMY_HP_INCREASE);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
