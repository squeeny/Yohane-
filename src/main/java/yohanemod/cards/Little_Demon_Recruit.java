package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.actions.SummonFromDeckToHand;
import yohanemod.patches.AbstractCardEnum;

import java.util.Iterator;

public class Little_Demon_Recruit extends CustomCard {
    public static final String ID = "Little_Demon_Recruit";
    public static final String NAME = "Little Demon Recruit";
    public static final String DESCRIPTION = "Add !M! card that Summons from your draw pile to your hand. NL Exhaust";
    public static final String UPGRADED_DESCRIPTION = "Add !M! cards that Summons from your draw pile to your hand. NL Exhaust";
    public static final String[] EXTENDED_DESCRIPTION = {"I have no Little Demons"};
    public static final String IMG_PATH = "cards/Little_Demon_Recruit.png";
    private static final int ADD = 1;
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Little_Demon_Recruit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = ADD;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SummonFromDeckToHand(this.magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            boolean hasSummon = false;
            Iterator var5 = p.drawPile.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (c.rawDescription.contains("Summon")) {
                    hasSummon = true;
                }
            }

            if (!hasSummon) {
                this.cantUseMessage = EXTENDED_DESCRIPTION[0];
                canUse = false;
            }

            return canUse;
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Recruit();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}