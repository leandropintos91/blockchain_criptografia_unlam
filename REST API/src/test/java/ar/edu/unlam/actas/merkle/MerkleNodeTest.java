package ar.edu.unlam.actas.merkle;

import ar.edu.unlam.actas.model.merkletree.MerkleLeaf;
import ar.edu.unlam.actas.model.merkletree.MerkleNode;
import ar.edu.unlam.actas.model.transactions.Acta;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerkleNodeTest {

    @Test
    public void testConstructor_BalancedTree() {
        final MerkleLeaf firstLeaf = MerkleLeaf.builder().data(Acta.builder().dniAlumno("dniAlumno1").materia("materia1").nota(1).build()).build();
        final MerkleLeaf secondLeaf = MerkleLeaf.builder().data(Acta.builder().dniAlumno("dniAlumno2").materia("materia2").nota(2).build()).build();
        final MerkleLeaf thirdLeaf = MerkleLeaf.builder().data(Acta.builder().dniAlumno("dniAlumno3").materia("materia3").nota(3).build()).build();
        final MerkleLeaf fourthLeaf = MerkleLeaf.builder().data(Acta.builder().dniAlumno("dniAlumno4").materia("materia4").nota(4).build()).build();

        final MerkleNode firstNode = new MerkleNode();
        firstNode.setFirstNode(firstLeaf);
        firstNode.setSecondNode(secondLeaf);
        final MerkleNode secondNode = new MerkleNode();
        secondNode.setFirstNode(thirdLeaf);
        secondNode.setSecondNode(fourthLeaf);

        final MerkleNode expectedRootNode = new MerkleNode();
        expectedRootNode.setFirstNode(firstNode);
        expectedRootNode.setSecondNode(secondNode);

        final List<Acta> actasList = List.of(
                Acta.builder().dniAlumno("dniAlumno1").materia("materia1").nota(1).build(),
                Acta.builder().dniAlumno("dniAlumno2").materia("materia2").nota(2).build(),
                Acta.builder().dniAlumno("dniAlumno3").materia("materia3").nota(3).build(),
                Acta.builder().dniAlumno("dniAlumno4").materia("materia4").nota(4).build()
        );

        final MerkleNode actualRootNode = MerkleNode.builder().data(actasList).build();

        assertEquals(expectedRootNode.getDataBlock(), actualRootNode.getDataBlock());
    }

}
