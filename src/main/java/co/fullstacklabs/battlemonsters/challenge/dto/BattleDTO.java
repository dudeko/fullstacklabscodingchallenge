package co.fullstacklabs.battlemonsters.challenge.dto;

import co.fullstacklabs.battlemonsters.challenge.exceptions.BattleException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BattleDTO {
    
    private Integer id;
    private MonsterDTO monsterA;
    private MonsterDTO monsterB;
    private MonsterDTO winner;


    public void calculateWinner() {
        do {
            executeBattleTurn();
        } while (bothMonstersStillAlive());
        evaluateAndSetWinner();
    }

    private void evaluateAndSetWinner() {
        if (getMonsterA().isAlive()) {
            this.setWinner(getMonsterA());
        } else if (getMonsterB().isAlive()) {
            this.setWinner(getMonsterB());
        } else {
            throw new BattleException("Nobody won. Something weird happened.");
        }
        if (this.getWinner() != null) {
            System.out.println(this.getWinner().getName() + " is the winner!");
        }
    }

    private boolean bothMonstersStillAlive() {
        return getMonsterA().isAlive() && getMonsterB().isAlive();
    }

    private void executeBattleTurn() {
        if (monsterA.attacksFirstThan(monsterB)) {
            monsterA.doDamageTo(monsterB);
            if (monsterB.isAlive()) {
                monsterB.doDamageTo(monsterA);
            }
        } else {
            monsterB.doDamageTo(monsterA);
            if (monsterA.isAlive()) {
                monsterA.doDamageTo(monsterB);
            }
        }
    }
}
