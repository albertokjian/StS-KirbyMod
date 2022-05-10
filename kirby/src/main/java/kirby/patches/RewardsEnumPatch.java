package kirby.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RewardsEnumPatch {
    @SpireEnum
    public static RewardItem.RewardType CopyAbilityReward;
}
