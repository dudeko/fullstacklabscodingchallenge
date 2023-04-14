package co.fullstacklabs.battlemonsters.challenge.dto;

import com.univocity.parsers.annotations.Parsed;

import com.univocity.parsers.annotations.Validate;
import com.univocity.parsers.common.DataValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;


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

    @Validate(validators = {})
    @Parsed
    private Integer defense;

    @Parsed
    private Integer hp;

    @Parsed
    private Integer speed;

    @Parsed
    private String imageUrl;

    public boolean attacksFirstThan(MonsterDTO otherMonsterDTO) {
        return this.getSpeed() > otherMonsterDTO.getSpeed() ||
                (this.getSpeed().equals(this.getSpeed()) && this.getAttack() > otherMonsterDTO.getAttack());
    }

    public static void validateCsvHeaders(String[] headers) throws DataValidationException {
        if (headers != null) {
            Arrays.stream(headers).forEach(headerName -> {
                try {
                    MonsterDTO.class.getDeclaredField(headerName);
                } catch (NoSuchFieldException e) {
                    throw new DataValidationException(e.getMessage());
                }
            });
        }
    }

    public void doDamageTo(MonsterDTO otherMonsterDTO) {
        otherMonsterDTO.reduceHp(getDamageGivenTo(otherMonsterDTO));
        System.out.println(this.getName() + " did damage to " + otherMonsterDTO.getName() + " leaving him with " + otherMonsterDTO.getHp() + " HP.");
    }

    private void reduceHp(int damage) {
        this.setHp(this.getHp() - damage);
    }

    private int getDamageGivenTo(MonsterDTO otherMonsterDTO) {
        int damage = this.getAttack() - otherMonsterDTO.getDefense();
        return damage > 0 ? damage : 1;
    }

    public boolean isAlive() {
        return getHp() > 0;
    }
}
