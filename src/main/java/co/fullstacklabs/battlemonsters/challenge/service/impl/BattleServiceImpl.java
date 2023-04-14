package co.fullstacklabs.battlemonsters.challenge.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.BattleException;
import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
import co.fullstacklabs.battlemonsters.challenge.model.Monster;
import co.fullstacklabs.battlemonsters.challenge.service.MonsterService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.service.BattleService;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@Service
public class BattleServiceImpl implements BattleService {

    private transient BattleRepository battleRepository;
    private transient ModelMapper modelMapper;

    private transient MonsterService monsterService;

   
    @Autowired
    public BattleServiceImpl(BattleRepository battleRepository, ModelMapper modelMapper, MonsterService monsterService) {
        this.battleRepository = battleRepository;
        this.modelMapper = modelMapper;
        this.monsterService = monsterService;
    }

    /**
     * List all existence battles
     */
    @Override
    public List<BattleDTO> getAll() {
        List<Battle> battles = battleRepository.findAll();
        return battles.stream().map(battle -> modelMapper.map(battle, BattleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BattleDTO create(BattleDTO battleDTO) {
        Battle battle = modelMapper.map(battleDTO, Battle.class);
        battle = battleRepository.save(battle);
        return modelMapper.map(battle, BattleDTO.class);
    }

    @Override
    public BattleDTO update(BattleDTO battleDTO) {
        findBattleById(battleDTO.getId());
        Battle battle = modelMapper.map(battleDTO, Battle.class);
        battle = battleRepository.save(battle);
        return modelMapper.map(battle, BattleDTO.class);
    }

    @Override
    public BattleDTO start(BattleDTO battleDTO, Integer firstMonsterId, Integer secondMonsterId) throws BattleException, ResourceNotFoundException {
        validateMonsters(firstMonsterId, secondMonsterId);
        battleDTO.setMonsterA(monsterService.findById(firstMonsterId));
        battleDTO.setMonsterB(monsterService.findById(secondMonsterId));
        battleDTO.calculateWinner();
        return update(battleDTO);
    }

    private static void validateMonsters(Integer firstMonsterId, Integer secondMonsterId) {
        if (firstMonsterId == null || secondMonsterId == null) {
            throw new BattleException("The monster is undefined.");
        }
    }

    @Override
    public BattleDTO delete(Integer id) {
        Battle battle = findBattleById(id);
        battleRepository.delete(battle);
        return modelMapper.map(battle, BattleDTO.class);
    }

    private Battle findBattleById(Integer id) {
        return battleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Battle not found"));
    }
}
