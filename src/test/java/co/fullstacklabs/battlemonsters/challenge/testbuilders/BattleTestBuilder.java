package co.fullstacklabs.battlemonsters.challenge.testbuilders;

import java.io.Serializable;

import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.model.Monster;
import lombok.Builder;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class BattleTestBuilder {
    
    @Builder
    public static Battle monster(Integer id, Monster monsterA, Monster monsterB, Monster monsterWinner) {
        Battle battle = new Battle();
        battle.setId(id);
        battle.setMonsterA(monsterA);
        battle.setMonsterB(monsterB);
        battle.setWinner(monsterWinner);
        return battle;
    }

    public static class BattleBuilder {

        private Integer id = 1;
        private transient Monster monsterA = MonsterTestBuilder.builder().id(1).build();
        private Monster monsterB = MonsterTestBuilder.builder().id(2).build();
        private transient Monster monsterWinner = monsterA;
    }
}
