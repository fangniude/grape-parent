package dict.domain;

import lombok.Getter;
import lombok.Setter;
import org.grape.GrapeModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "dict_dict")
public class Dict extends GrapeModel {
    @Column(nullable = false, length = 32)
    private String category;

    @Column(nullable = false, length = 32)
    private String value;

    public Dict() {
    }

    public Dict(String category, String value) {
        super(key(category, value));
        this.category = category;
        this.value = value;
    }

    public static String key(String category, String value) {
        return String.format("%s_%s", category, value);
    }

}
