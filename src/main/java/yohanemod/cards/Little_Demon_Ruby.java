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
import yohanemod.summons.Ruby;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import yohanemod.powers.SoulLink;

public class Little_Demon_Ruby extends CustomCard {
    public static final String ID = "Little_Demon_Ruby";
    public static final String NAME = "Little Demon Ruby";
    public static final String DESCRIPTION = "Summon 1 Ruby.";
    public static final String IMG_PATH = "cards/Little_Demon_Ruby.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Little_Demon_Ruby() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
    }

    //Can't be upgraded
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

        if(abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            MonsterGroup minions = player.getMinions();
            if (minions.monsters.size() < 2) {
                if (minions.monsters.size() == 0) {
                    player.addMinion(new Ruby());
                    AbstractMonster Ruby = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, abstractPlayer, new SoulLink(player,1), 1));
                } else {
                    if (player.minions.monsters.get(0).id.equals("Ruby")) {
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Ruby is already summoned!", 1.0F, 2.0F));
                    } else {
                        player.addMinion(new Ruby());
                        AbstractMonster Ruby = player.minions.monsters.get(1);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, abstractPlayer, new SoulLink(player,1), 1));
                    }
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }

}
