package org.grape;


import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@NoArgsConstructor
@MappedSuperclass
public abstract class GrapeResource extends GrapeModel {
    public GrapeResource(String key) {
        super(key);
    }

    public GrapeResource(String key, String name) {
        super(key, name);
    }
}
