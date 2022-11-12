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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Acta)) {
            return false;
        }

        Acta other = (Acta) obj;
        return this.dniAlumno.equals(other.dniAlumno) && this.materia.equals(other.materia) && this.nota == other.nota;
    }
}