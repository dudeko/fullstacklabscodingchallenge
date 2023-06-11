package co.fullstacklabs.battlemonsters.challenge.dto;

import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
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
        while (monsterA.stillAlive() && monsterB.stillAlive()) {
            if (monsterA.attacksFirst(monsterB)) {
                monsterA.doAttack(monsterB);
                if (monsterB.stillAlive()) {
                    monsterB.doAttack(monsterA);
                }
            } else {
                monsterB.doAttack(monsterA);
                if (monsterA.stillAlive()) {
                    monsterA.doAttack(monsterB);
                }
            }
        }
        this.setWinner(monsterA.stillAlive() ? monsterA : monsterB);
    }

    public void validateMonstersAreNotNull() {
        if (monsterA == null || monsterB == null) {
            throw new ResourceNotFoundException("One of the monsters is missing. We can´t start the battle.");
        }
    }
}
