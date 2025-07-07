package com.petthegarden.petthegarden.stray;

import lombok.Data;
import java.util.List;

@Data
public class StrayApiResponseWrapper {
    private List<Body> AbdmAnimalProtect;

    @Data
    public static class Body {
        private List<StrayDto> row;
    }
}
