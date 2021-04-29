import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class CityDTO implements Serializable {
    private long id;
    private String name;
    private long foundationYear;
    private long area;
    private List<CitizenTypeDTO> citizenTypes = new ArrayList<>();
}
