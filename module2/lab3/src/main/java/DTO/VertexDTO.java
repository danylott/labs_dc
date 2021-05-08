package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexDTO {
    private long id;
    private long polygonName;
    private String name;
    private long angle;
}
