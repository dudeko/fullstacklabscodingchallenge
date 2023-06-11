package co.fullstacklabs.battlemonsters.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.fullstacklabs.battlemonsters.challenge.ApplicationConfig;
import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.service.BattleService;
import co.fullstacklabs.battlemonsters.challenge.service.MonsterService;


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
    private transient  MockMvc mockMvc;
    @Autowired
    private transient BattleService battleService;
    @Autowired
    private transient MonsterService monsterService;
    @Autowired
    private transient ObjectMapper objectMapper;
    @Autowired
    private transient ModelMapper modelMapper;

    @Test
    void shouldFetchAllBattles() throws Exception {
        this.mockMvc.perform(get(BATTLE_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(1)));
    }
    
    @Test
    void shouldFailBattleWithUndefinedMonster() throws Exception {
        BattleDTO battleDTO = BattleDTO.builder().build();
        this.mockMvc.perform(post(BATTLE_PATH).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(battleDTO)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFailBattleWithInexistentMonster() throws Exception {
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(MonsterDTO.builder().id(999).build())
                .monsterB(MonsterDTO.builder().id(999).build())
                .build();
        this.mockMvc.perform(post(BATTLE_PATH).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(battleDTO)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldInsertBattleWithMonsterAWinning() throws Exception {
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(MonsterDTO.builder().id(2).build())
                .monsterB(MonsterDTO.builder().id(7).build())
                .build();
        this.mockMvc.perform(post(BATTLE_PATH).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(battleDTO)))
                .andExpect(jsonPath("$.winner.id", Is.is(2)));
    }

    @Test
    void shouldInsertBattleWithMonsterBWinning() throws Exception {
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(MonsterDTO.builder().id(7).build())
                .monsterB(MonsterDTO.builder().id(2).build())
                .build();
        this.mockMvc.perform(post(BATTLE_PATH).contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(battleDTO)))
                .andExpect(jsonPath("$.winner.id", Is.is(2)));
    }

    @Test
    void shouldDeleteBattleSucessfully() throws Exception {
        BattleDTO battleDTO = BattleDTO.builder()
                .monsterA(MonsterDTO.builder().id(7).build())
                .monsterB(MonsterDTO.builder().id(2).build())
                .build();
        BattleDTO battleAfterResult = battleService.startBattle(battleDTO);
        this.mockMvc.perform(delete(BATTLE_PATH + "/" + battleAfterResult.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailDeletingInexistentBattle() throws Exception {
        this.mockMvc.perform(delete(BATTLE_PATH + "/423897492374"))
                .andExpect(status().is4xxClientError());
    }
}