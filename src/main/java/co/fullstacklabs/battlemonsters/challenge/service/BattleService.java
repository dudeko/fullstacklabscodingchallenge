package co.fullstacklabs.battlemonsters.challenge.service;

import java.util.List;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.BattleException;
import javassist.NotFoundException;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public interface BattleService {

    List<BattleDTO> getAll();

    BattleDTO create(BattleDTO battleDTO);

    BattleDTO update(BattleDTO battleDTO);

    BattleDTO start(BattleDTO battleDTO, Integer firstMonsterId, Integer secondMonsterId) throws BattleException, NotFoundException;

    BattleDTO delete(Integer id);
}
