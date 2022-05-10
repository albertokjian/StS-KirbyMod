package kirby.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InformationLookup {
    private static final Map<String, Function<AbstractCreature, AbstractCard>> MONSTER_CARD_MAP = new HashMap<String, Function<AbstractCreature, AbstractCard>>() {{
        put("SpikeSlime_S", t -> new Inflame());
    }};

    public static AbstractCard getCardFromCreature(AbstractCreature creature) {
        Function<AbstractCreature, AbstractCard> func = MONSTER_CARD_MAP.getOrDefault(creature.id, t -> new FlashOfSteel());
        return func.apply(creature);
    }
}