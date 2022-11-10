package ar.edu.unlam.actas.model.transactions;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Acta {
    private String dniAlumno;
    private String materia;
    private int nota;
}