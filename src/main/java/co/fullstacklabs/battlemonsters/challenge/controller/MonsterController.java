package co.fullstacklabs.battlemonsters.challenge.controller;

import java.io.IOException;
import java.util.List;

import co.fullstacklabs.battlemonsters.challenge.exceptions.ResourceNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.fullstacklabs.battlemonsters.challenge.dto.MonsterDTO;
import co.fullstacklabs.battlemonsters.challenge.exceptions.UnprocessableFileException;
import co.fullstacklabs.battlemonsters.challenge.service.MonsterService;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@RestController
@RequestMapping("/monster")
public class MonsterController {
    
    @Autowired
    private transient MonsterService monsterService;

    @GetMapping("/{id}")
    public MonsterDTO getMonsterById(@PathVariable("id") int monsterId) throws ResourceNotFoundException {
        return monsterService.findById(monsterId);
    }

    @PostMapping
    public MonsterDTO create(@RequestBody MonsterDTO monsterDTO) {
        return monsterService.create(monsterDTO);
    }

    @GetMapping
    public List<MonsterDTO> getAll() {
        return monsterService.getAll();
    }

    @PutMapping
    public MonsterDTO update(@RequestBody MonsterDTO monsterDTO) {
        return monsterService.update(monsterDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int monsterId) throws ResourceNotFoundException {
        monsterService.delete(monsterId);
    }
    
    @PostMapping("/import")
    public void importCsv(@RequestParam("file") MultipartFile file, 
            RedirectAttributes redirectAttributes) {
        try{
            monsterService.importFromInputStream(file.getInputStream());
        } catch (IOException ex) {
           throw new UnprocessableFileException(ex.getMessage());
        }
    }

}
