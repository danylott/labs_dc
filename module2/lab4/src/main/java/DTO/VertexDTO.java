package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VertexDTO implements Serializable {
    private long id;
    private long polygonName;
    private String name;
    private long angle;
}
