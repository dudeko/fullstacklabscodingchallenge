package co.fullstacklabs.battlemonsters.challenge.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.model.Battle;
import co.fullstacklabs.battlemonsters.challenge.repository.BattleRepository;
import co.fullstacklabs.battlemonsters.challenge.service.BattleService;
import co.fullstacklabs.battlemonsters.challenge.service.MonsterService;

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
    public BattleDTO startBattle(BattleDTO battleDTO) {
        validateMonstersExist(battleDTO);
        battleDTO.calculateWinner();
        Battle battle = modelMapper.map(battleDTO, Battle.class);
        battleRepository.save(battle);
        return modelMapper.map(battle, BattleDTO.class);
    }

    @Override
    public void deleteBattle(int id) {
        battleRepository.deleteById(id);
    }

    private void validateMonstersExist(BattleDTO battleDTO) {
        battleDTO.validateMonstersAreNotNull();
        battleDTO.setMonsterA(monsterService.findById(battleDTO.getMonsterA().getId()));
        battleDTO.setMonsterB(monsterService.findById(battleDTO.getMonsterB().getId()));
    }


}
