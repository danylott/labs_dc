import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenTypeDTO implements Serializable {
    private long id;
    private long cityId;
    private String name;
    private String language;
    private long population;
}
