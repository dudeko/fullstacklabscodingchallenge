package co.fullstacklabs.battlemonsters.challenge.controller;

import java.util.List;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.fullstacklabs.battlemonsters.challenge.dto.BattleDTO;
import co.fullstacklabs.battlemonsters.challenge.service.BattleService;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private transient BattleService battleService;

    @GetMapping
    public List<BattleDTO> getAll() {
        return battleService.getAll();
    }

    @GetMapping("/{firstMonsterId}/{secondMonsterId}")
    public BattleDTO start(@PathVariable int firstMonsterId, @PathVariable int secondMonsterId) throws NotFoundException {
        return battleService.start(BattleDTO.builder().build(), firstMonsterId, secondMonsterId);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        battleService.delete(id);
    }
    
}
