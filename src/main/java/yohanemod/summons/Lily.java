package yohanemod.summons;

import monsters.AbstractFriendlyMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import actions.ChooseAction;
import actions.ChooseActionInfo;
import cards.MonsterCard;
import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import yohanemod.powers.FallenEnergy;


public class Lily extends AbstractFriendlyMonster {
    public static String NAME = "Lily";
    public static String ID = "Lily";
    public int upgradeCount;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;


    public Lily() {
        super(NAME, ID, 15,
                null, -8.0F, 10.0F, 230.0F, 240.0F, "summons/Lily.png", -750F, 0);

    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new MonsterCard(), target, "Choose your attack");
            this.moveInfo.forEach( move -> {
                pickAction.add(move.getName(), move.getDescription(), move.getAction());
            });
            AbstractDungeon.actionManager.addToBottom(pickAction);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    //Create possible moves for the monster
    private ArrayList<ChooseActionInfo> makeMoves(){
        if (this.hasPower("LilyStrength") && this.getPower("LilyStrength").amount != 0) {
            upgradeCount = this.getPower("LilyStrength").amount;
        }
        int attackDamage = (4 + upgradeCount * 2);
        int chargeAmount = (6 + upgradeCount);
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        target = AbstractDungeon.getRandomMonster();
        String attackDesc = String.format("Deal %d damage to a random enemy.", attackDamage);
        String chargeDesc = String.format("Gain %d Fallen Energy.", chargeAmount);
        tempInfo.add(new ChooseActionInfo("Attack", attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                    new DamageInfo(this, attackDamage, DamageInfo.DamageType.NORMAL)));
        }));
        tempInfo.add(new ChooseActionInfo("Charge", chargeDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, chargeAmount), chargeAmount));
        }));
        return tempInfo;
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {

    }
}
