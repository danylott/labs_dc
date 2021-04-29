import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenTypeDTO {
    private long id;
    private long cityId;
    private String name;
    private String language;
    private long population;
}
