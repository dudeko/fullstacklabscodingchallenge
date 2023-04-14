package co.fullstacklabs.battlemonsters.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.model.Monster;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.repository.MonsterRepository;
import co.fullstacklabs.battlemonsters.challenge.service.BattleService;
import co.fullstacklabs.battlemonsters.challenge.service.MonsterService;
import co.fullstacklabs.battlemonsters.challenge.testbuilders.BattleTestBuilder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import co.fullstacklabs.battlemonsters.challenge.ApplicationConfig;


/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
public class BattleControllerTest {
    private static final String BATTLE_PATH = "/battle";

    @Autowired
    transient BattleService battleService;
    @Autowired
    transient MonsterService monsterService;

    @Autowired
    private transient MockMvc mockMvc;

//    @BeforeAll
//    void setup() {
//        MonsterDTO monsterA = MonsterDTO.builder().name("Rob").attack(1).defense(1).hp(1)
//                .speed(1)
//                .imageUrl("")
//                .build();
//        MonsterDTO monsterB = MonsterDTO.builder().name("Matt").attack(1).defense(1).hp(1)
//                .speed(1)
//                .imageUrl("")
//                .build();
//        BattleDTO battle = BattleDTO.builder()
//                .monsterA(monsterService.create(monsterA))
//                .monsterB(monsterService.create(monsterB))
//                .build();
//        battle.setWinner(battle.getMonsterA());
//        battleService.create(battle);
//    }

    @Test
    void shouldFetchAllBattles() throws Exception {
        this.mockMvc.perform(get(BATTLE_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(1)));
    }
    
    @Test
    void shouldFailBattleWithUndefinedMonster() {
        //TODO: Implement
        assertEquals(1, 1);
    }

    @Test
    void shouldFailBattleWithInexistentMonster() {
        //TODO: Implement
        assertEquals(1, 1);
    }

    @Test
    void shouldInsertBattleWithMonsterAWinning() {
        //TODO: Implement
        assertEquals(1, 1);
    }

    @Test
    void shouldInsertBattleWithMonsterBWinning() {
        //TODO: Implement
        assertEquals(1, 1);
    }

    @Test
    void shouldDeleteBattleSucessfully() {
        //TODO: Implement
        assertEquals(1, 1);
    }

    @Test
    void shouldFailDeletingInexistentBattle() {
        //TODO: Implement
        assertEquals(1, 1);
    }
}