package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Descent extends CustomCard {
    public static final String ID = "Descent";
    public static final String NAME = "Descent";
    public static final String DESCRIPTION = "Pay 4 Fallen Energy. NL Apply !M! Vulnerable. NL Exhaust.";
    public static final String UPGRADED_DESCRIPTION = "Pay 4 Fallen Energy. NL Apply !M! Vulnerable. NL Exhaust.";
    public static final String IMG_PATH = "cards/Descent.png";
    private static final int COST = 0;
    private static final int FALLEN_ENERGY = 4;
    private static final int VULNERABLE_AMT = 1;
    private static final int UPGRADE_PLUS_VULNERABLE = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ENEMY;

    public Descent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.GREY, rarity,
                target, POOL);
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((p.hasPower("FallenEnergy")) && (p.getPower("FallenEnergy").amount >= FALLEN_ENERGY)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -FALLEN_ENERGY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Descent();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
