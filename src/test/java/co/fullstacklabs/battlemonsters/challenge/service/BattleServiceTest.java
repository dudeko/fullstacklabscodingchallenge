package co.fullstacklabs.battlemonsters.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.BattleException;
import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
import co.fullstacklabs.battlemonsters.challenge.model.Monster;
import co.fullstacklabs.battlemonsters.challenge.testbuilders.MonsterTestBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.repository.MonsterRepository;
import co.fullstacklabs.battlemonsters.challenge.service.impl.BattleServiceImpl;
import co.fullstacklabs.battlemonsters.challenge.testbuilders.BattleTestBuilder;

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

    /**** Delete */
    @Mock
    public transient MonsterRepository monsterRepository;

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
        Assertions.assertThrows(BattleException.class, () -> battleService.start(BattleDTO.builder().build(), null, null));
    }

    @Test
    void shouldFailBattleWithInexistentMonster() {
        Mockito.when(monsterService.findById(1)).thenThrow(BattleException.class);
        Assertions.assertThrows(BattleException.class, () -> battleService.start(any(BattleDTO.class),1, 2));
    }

    @Test
    void shouldInsertBattleWithMonsterAWinning() {
        int monster1Id = 1;
        int monster2Id = 2;
        MonsterDTO monster1 = MonsterDTO.builder()
                .id(monster1Id)
                .name("Frankie")
                .hp(100)
                .attack(8)
                .defense(2)
                .speed(5)
                .build();
        MonsterDTO monster2 = MonsterDTO.builder()
                .id(monster2Id)
                .name("Johnny")
                .hp(15)
                .attack(4)
                .defense(3)
                .speed(2)
                .build();

        BattleDTO battleDTO = BattleDTO.builder().build();

        Mockito.when(monsterService.findById(monster1Id)).thenReturn(monster1);
        Mockito.when(monsterService.findById(monster2Id)).thenReturn(monster2);
        Mockito.when(battleRepository.findById(any())).thenReturn(Optional.of(new Battle()));

        battleService.start(battleDTO, monster1Id, monster2Id);

        Assertions.assertEquals(battleDTO.getWinner(), monster1);
    }

    @Test
    void shouldInsertBattleWithMonsterBWinning() {
        int monster1Id = 1;
        int monster2Id = 2;
        MonsterDTO monster1 = MonsterDTO.builder()
                .id(monster1Id)
                .name("Frankie")
                .hp(10)
                .attack(1)
                .defense(1)
                .speed(1)
                .build();
        MonsterDTO monster2 = MonsterDTO.builder()
                .id(monster2Id)
                .name("Johnny")
                .hp(100)
                .attack(2)
                .defense(50)
                .speed(50)
                .build();

        BattleDTO battleDTO = BattleDTO.builder().build();

        Mockito.when(monsterService.findById(monster1Id)).thenReturn(monster1);
        Mockito.when(monsterService.findById(monster2Id)).thenReturn(monster2);
        Mockito.when(battleRepository.findById(any())).thenReturn(Optional.of(new Battle()));

        battleService.start(battleDTO, monster1Id, monster2Id);

        Assertions.assertEquals(battleDTO.getWinner(), monster2);
    }

    @Test
    void shouldDeleteBattleSucessfully() {
        int id = 2;
        Battle battle1 = BattleTestBuilder.builder().id(id).build();
        Mockito.when(battleRepository.findById(id)).thenReturn(Optional.of(battle1));
        Mockito.doNothing().when(battleRepository).delete(battle1);

        battleService.delete(id);

        Mockito.verify(battleRepository).findById(id);
        Mockito.verify(battleRepository).delete(battle1);
    }

    @Test
    void shouldFailDeletingInexistentBattle() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> battleService.delete(123));
    }



}
