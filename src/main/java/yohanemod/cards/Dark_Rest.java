package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Dark_Rest extends CustomCard{
    public static final String ID = "Dark_Rest";
    public static final String NAME = "Dark Rest";
    public static final String DESCRIPTION = "Apply !M! Weak. NL Gain 4 Fallen Energy.";
    public static final String UPGRADED_DESCRIPTION = "Apply !M! Weak. NL Gain 8 Fallen Energy.";
    public static final int WEAK_AMT = 1;
    public static final String IMG_PATH = "cards/Dark_Rest.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Dark_Rest () {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = WEAK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(m, p,
                        new com.megacrit.cardcrawl.powers.WeakPower(m, this.magicNumber, false), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 4), 4));
            } else {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(m, p,
                        new com.megacrit.cardcrawl.powers.WeakPower(m, this.magicNumber, false), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 8), 8));
            }
    }



    @Override
    public AbstractCard makeCopy() {
        return new Dark_Rest();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription =UPGRADED_DESCRIPTION;
            this.initializeDescription();
            this.upgradeMagicNumber(1);
        }
    }

}
