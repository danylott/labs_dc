package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private long id;
    private String name;
    private long foundationYear;
    private long area;
    private List<CitizenTypeDTO> citizenTypes = new ArrayList<>();
}
