package co.fullstacklabs.battlemonsters.challenge.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Paths.get;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.fullstacklabs.battlemonsters.challenge.ApplicationConfig;
import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;


/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)
public class MonsterControllerTest {
    private static final String MONSTER_PATH = "/monster";
    public static final String ID_PATH_VARIABLE = "/{id}";

    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ObjectMapper objectMapper;

    @Test
    void shouldFetchAllMonsters() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MONSTER_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(1)))
                .andExpect(jsonPath("$[0].name", Is.is("Monster 1")))
                .andExpect(jsonPath("$[0].attack", Is.is(50)))
                .andExpect(jsonPath("$[0].defense", Is.is(40)))
                .andExpect(jsonPath("$[0].hp", Is.is(30)))
                .andExpect(jsonPath("$[0].speed", Is.is(25)));

    }

    @Test
    void shouldGetMosterSuccessfully() throws Exception {
        long id = 1l;
        this.mockMvc.perform(get(MONSTER_PATH + ID_PATH_VARIABLE, id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is("Monster 1")));
    }

    @Test
    void shoulGetMonsterNotExists() throws Exception {
        long id = 3l;
        this.mockMvc.perform(get(MONSTER_PATH + ID_PATH_VARIABLE, id))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldDeleteMonsterSuccessfully() throws Exception {
        int id = 4;
        
        MonsterDTO newMonster = MonsterDTO.builder().id(id).name("Monster 4")
                .attack(50).defense(30).hp(30).speed(22)
                .imageUrl("ImageURL1").build();

        this.mockMvc.perform(post(MONSTER_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMonster)));
                

        this.mockMvc.perform(delete(MONSTER_PATH + ID_PATH_VARIABLE, id))
            .andExpect(status().isOk());                
    }

    @Test
    void shouldDeleteMonsterNotFound() throws Exception {
        int id = 5;

        this.mockMvc.perform(delete(MONSTER_PATH + ID_PATH_VARIABLE, id))
                .andExpect(status().isNotFound());
    }
    
     @Test
     void testImportCsvSucessfully() throws Exception {
         this.mockMvc.perform(MockMvcRequestBuilders.multipart(MONSTER_PATH + "/import")
                         .file("file", Files.readAllBytes(get("data/monsters-correct.csv")))
                         .characterEncoding(UTF_8))
                 .andExpect(status().isOk());
     }
     
     @Test
     void testImportCsvInexistenctColumns() throws Exception {
         this.mockMvc.perform(MockMvcRequestBuilders.multipart(MONSTER_PATH + "/import")
                         .file("file", Files.readAllBytes(get("data/monsters-wrong-column.csv")))
                         .characterEncoding(UTF_8))
                 .andExpect(status().isInternalServerError());
     }
     
     @Test
     void testImportCsvInexistenctMonster () throws Exception {
         this.mockMvc.perform(MockMvcRequestBuilders.multipart(MONSTER_PATH + "/import")
                         .file("file", Files.readAllBytes(get("data/monsters-empty-monster.csv")))
                         .characterEncoding(UTF_8))
                 .andExpect(status().isInternalServerError());
     } 
}
