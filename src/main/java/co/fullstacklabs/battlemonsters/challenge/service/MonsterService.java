package co.fullstacklabs.battlemonsters.challenge.service;

import java.io.InputStream;
import java.util.List;

import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
import javassist.NotFoundException;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public interface MonsterService {
    
    MonsterDTO create(MonsterDTO monsterDTO);

    MonsterDTO findById(int id) throws ResourceNotFoundException;

    MonsterDTO update(MonsterDTO monsterDTO);

    void delete(Integer id) throws ResourceNotFoundException;

    List<MonsterDTO> getAll();

    void importFromInputStream(InputStream inputStream);


}
