package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Perfection extends CustomCard {
    public static final String ID = "Yohane:Perfection";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Perfection.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;


    public Perfection() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.YOHANE_GREY, rarity,
                target);
        this.damage = this.baseDamage;
    }

    public boolean hasEnoughEnergy() {
        boolean retVal = super.hasEnoughEnergy();
        if (!AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID)) {
            retVal = false;
        }
        if ((AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount < this.magicNumber)) {
            retVal = false;
        }
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID)  && p.getPower(FallenEnergy.POWER_ID).amount >= 1 ){
            DamageInfo info = new DamageInfo(p, p.getPower(FallenEnergy.POWER_ID).amount);
            info.applyPowers(p, m);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), - p.getPower(FallenEnergy.POWER_ID).amount));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Perfection();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }
}