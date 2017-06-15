package dict.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grape.GrapeModel;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dict_dict")
public final class Dict extends GrapeModel {
    @Column(nullable = false, length = 32)
    private String category;

    @Column(nullable = false, length = 32)
    private String value;

    public Dict(String category, String value, String name) {
        this.category = category;
        this.value = value;
        super.name = name;
    }

    @NotNull
    @Override
    public String key() {
        return String.format("%s_%s", category, value);
    }
}
