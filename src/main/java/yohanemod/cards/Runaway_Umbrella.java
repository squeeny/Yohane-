package yohanemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Runaway_Umbrella extends CustomCard {
    public static final String ID = "Runaway_Umbrella";
    public static final String NAME = "Runaway Umbrella";
    public static final String DESCRIPTION = "Deal !D! damage. NL Gain !M! Fallen Energy. NL Ethereal.";
    public static final String IMG_PATH = "cards/Runaway_Umbrella.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int FALLEN_ENERGY = 6;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Runaway_Umbrella() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.GREY, rarity,
                target, POOL);

        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Runaway_Umbrella();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(4);
        }
    }
}