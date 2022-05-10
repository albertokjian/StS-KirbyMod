package kirby.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kirby.rewards.CopyAbilityReward;
import kirby.util.InformationLookup;

public class InhaleAction extends AbstractGameAction {
    public float startX, startY;
    public float targetX, targetY;
    public float t;

    private boolean startDying = false;
    private AbstractPlayer p;
    private AbstractMonster m;

    public InhaleAction(final AbstractPlayer p, final AbstractMonster m) {
        duration = startDuration = Settings.FAST_MODE ? 1.25f : 1.5f;
        this.p = p;
        this.m = m;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            t = 0;
            startX = m.drawX;
            startY = m.drawY;
            targetX = p.drawX + p.hb_w / 2.0F;
            targetY = p.drawY;
        }
        tickDuration();
        if (this.isDone) {
            AbstractCard card = InformationLookup.getCardFromCreature(m);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
            AbstractDungeon.getCurrRoom().rewards.add(new CopyAbilityReward(card.makeStatEquivalentCopy()));
        } else {
            t = (startDuration - duration) / startDuration;
            if (!startDying && duration < startDuration * 0.7F) {
                this.m.gold = 0;
                this.m.currentHealth = 0;
                this.m.die(true);
                this.m.healthBarUpdatedEvent();
                startDying = true;
            }
            m.drawX = MathUtils.lerp(startX, targetX, t);
            m.drawY = MathUtils.lerp(startY, targetY, t);
            ReflectionHacks.privateMethod(AbstractCreature.class, "refreshHitboxLocation").invoke(m);
        }
    }
}