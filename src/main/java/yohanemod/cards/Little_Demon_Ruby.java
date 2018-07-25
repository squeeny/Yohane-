package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import characters.AbstractPlayerWithMinions;
import yohanemod.powers.RubyStrength;
import yohanemod.summons.Ruby;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import yohanemod.powers.SoulLink;

public class Little_Demon_Ruby extends CustomCard {
    public static final String ID = "Little_Demon_Ruby";
    public static final String NAME = "Little Demon Ruby";
    public static final String DESCRIPTION = "Summon 1 Ruby. Playing this card with Ruby already on the field Evolves her.";
    public static final String IMG_PATH = "cards/Little_Demon_Ruby.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Little_Demon_Ruby() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);

    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Ruby();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new yohanemod.summons.Ruby());
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals("Ruby")) {
                    //Upgrade
                    AbstractMonster ruby0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby0Upgraded, abstractPlayer, new RubyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(ruby0Upgraded, ruby0Upgraded, 5));
                } else {
                    //No Upgrade
                    player.addMinion(new yohanemod.summons.Ruby());
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals("Ruby")) {
                    //Upgrade
                    AbstractMonster ruby0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby0Upgraded, abstractPlayer, new RubyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(ruby0Upgraded, ruby0Upgraded, 5));
                } else if (player.minions.monsters.get(1).id.equals("Ruby")) {
                    //Upgrade
                    AbstractMonster ruby1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby1Upgraded, abstractPlayer, new RubyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(ruby1Upgraded, ruby1Upgraded, 5));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }
}
