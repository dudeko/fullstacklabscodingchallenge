package co.fullstacklabs.battlemonsters.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.model.Monster;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.repository.MonsterRepository;
import co.fullstacklabs.battlemonsters.challenge.service.impl.BattleServiceImpl;
import co.fullstacklabs.battlemonsters.challenge.testbuilders.BattleTestBuilder;
import co.fullstacklabs.battlemonsters.challenge.testbuilders.MonsterTestBuilder;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@ExtendWith(MockitoExtension.class)
public class BattleServiceTest {
    
    @InjectMocks
    public transient BattleServiceImpl battleService;

    @Mock
    public transient BattleRepository battleRepository;

    @Mock
    public transient MonsterService monsterService;

    @Mock
    private transient ModelMapper mapper;

    @Test
    public void testGetAll() {
        
        Battle battle1 = BattleTestBuilder.builder().id(1).build();
        Battle battle2 = BattleTestBuilder.builder().id(2).build();            

        List<Battle> battleList = Lists.newArrayList(battle1, battle2);
        Mockito.when(battleRepository.findAll()).thenReturn(battleList);
        
        battleService.getAll();

        Mockito.verify(battleRepository).findAll();
        Mockito.verify(mapper).map(battleList.get(0), BattleDTO.class);
        Mockito.verify(mapper).map(battleList.get(1), BattleDTO.class);        
    }
    

    @Test
    void shouldFailBattleWithUndefinedMonster() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> battleService.startBattle(BattleDTO.builder().build()));
    }

    @Test
    void shouldFailBattleWithInexistentMonster() {
        Mockito.when(monsterService.findById(1)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> battleService.startBattle(BattleDTO.builder()
                        .monsterA(MonsterDTO.builder().id(1).build())
                        .monsterB(MonsterDTO.builder().id(2).build())
                        .build()));
    }

    @Test
    void shouldInsertBattleWithMonsterAWinning() {
        MonsterDTO monsterA = MonsterDTO.builder()
                .id(1)
                .hp(100)
                .attack(100)
                .defense(100)
                .speed(100)
                .build();
        MonsterDTO monsterB = MonsterDTO.builder()
                .id(2)
                .hp(1)
                .attack(1)
                .defense(1)
                .speed(1)
                .build();
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(monsterA)
                .monsterB(monsterB)
                .build();
        Mockito.when(monsterService.findById(1)).thenReturn(monsterA);
        Mockito.when(monsterService.findById(2)).thenReturn(monsterB);
        Mockito.when(mapper.map(any(BattleDTO.class), any())).thenReturn(new Battle());
        Mockito.when(mapper.map(any(Battle.class), any())).thenReturn(battleDTO);
        BattleDTO resultBattle = battleService.startBattle(battleDTO);
        Assertions.assertEquals(resultBattle.getWinner(), resultBattle.getMonsterA());
    }

    @Test
    void shouldInsertBattleWithMonsterBWinning() {
        MonsterDTO monsterA = MonsterDTO.builder()
                .id(1)
                .hp(10)
                .attack(1)
                .defense(1)
                .speed(100)
                .build();
        MonsterDTO monsterB = MonsterDTO.builder()
                .id(2)
                .hp(1000)
                .attack(10)
                .defense(100)
                .speed(1)
                .build();
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(monsterA)
                .monsterB(monsterB)
                .build();
        Mockito.when(monsterService.findById(1)).thenReturn(monsterA);
        Mockito.when(monsterService.findById(2)).thenReturn(monsterB);
        Mockito.when(mapper.map(any(BattleDTO.class), any())).thenReturn(new Battle());
        Mockito.when(mapper.map(any(Battle.class), any())).thenReturn(battleDTO);
        BattleDTO resultBattle = battleService.startBattle(battleDTO);
        Assertions.assertEquals(resultBattle.getWinner(), resultBattle.getMonsterB());
    }

    @Test
    void shouldDeleteBattleSucessfully() {
        int id = 1;
        Mockito.doNothing().when(battleRepository).deleteById(id);
        Assertions.assertDoesNotThrow(() -> battleService.deleteBattle(id));
    }

    @Test
    void shouldFailDeletingInexistentBattle() {
        int id = 4234234;
        Mockito.doThrow(EmptyResultDataAccessException.class).when(battleRepository).deleteById(id);
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> battleService.deleteBattle(id));
    }



}
