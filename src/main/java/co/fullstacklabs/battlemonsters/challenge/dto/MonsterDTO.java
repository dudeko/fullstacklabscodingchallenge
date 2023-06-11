package co.fullstacklabs.battlemonsters.challenge.dto;

import java.util.Arrays;

import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanListProcessor;

import co.fullstacklabs.battlemonsters.challenge.exceptions.UnprocessableFileException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonsterDTO {
    private Integer id;

    @Parsed
    private String name;

    @Parsed
    private Integer attack;

    @Parsed
    private Integer defense;

    @Parsed
    private Integer hp;

    @Parsed
    private Integer speed;

    @Parsed
    private String imageUrl;

    public static void validateHeaders(BeanListProcessor<MonsterDTO> rowProcessor) {
        Arrays.stream(rowProcessor.getHeaders()).forEach(header -> {
            if (Arrays.stream(MonsterDTO.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(header))) {
                throw new UnprocessableFileException("Column " + header + " does not exist.");
            }
        });

    }

    public boolean attacksFirst(MonsterDTO otherMonster) {
        return this.speed > otherMonster.speed ||
                (this.speed.equals(otherMonster.speed) && this.attack >= otherMonster.attack);
    }

    public void doAttack(MonsterDTO otherMonster) {
        otherMonster.receiveDamage(this.attack - otherMonster.defense);
    }

    private void receiveDamage(int damage) {
        this.hp = this.hp - damage;
    }

    public boolean stillAlive() {
        return this.hp > 0;
    }
}
