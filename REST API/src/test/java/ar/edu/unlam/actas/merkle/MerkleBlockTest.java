package ar.edu.unlam.actas.merkle;

import ar.edu.unlam.actas.model.merkletree.IMerkleNode;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.merkletree.MerkleLeaf;
import ar.edu.unlam.actas.model.merkletree.MerkleNode;
import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.repository.MerkleNodeAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerkleBlockTest {

    private final String FILE_PATH = "./src/test/java/ar/edu/unlam/actas/merkle/resources/MerkleBlock.json";

    @Test
    public void testMerkleBlock_ReadOk() throws IOException {
        Acta acta1 = Acta.builder().dniAlumno("dniAlumno1").materia("materia1").nota(1).build();
        Acta acta2 = Acta.builder().dniAlumno("dniAlumno2").materia("materia2").nota(2).build();
        Acta acta3 = Acta.builder().dniAlumno("dniAlumno3").materia("materia3").nota(3).build();
        Acta acta4 = Acta.builder().dniAlumno("dniAlumno4").materia("materia4").nota(4).build();

        MerkleLeaf merkleLeaf1 = MerkleLeaf.builder().data(acta1).build();
        merkleLeaf1.setHash("hash");
        MerkleLeaf merkleLeaf2 = MerkleLeaf.builder().data(acta2).build();
        merkleLeaf2.setHash("hash");
        MerkleLeaf merkleLeaf3 = MerkleLeaf.builder().data(acta3).build();
        merkleLeaf3.setHash("hash");
        MerkleLeaf merkleLeaf4 = MerkleLeaf.builder().data(acta4).build();
        merkleLeaf4.setHash("hash");

        MerkleNode merkleNode1 = new MerkleNode();
        merkleNode1.setFirstNode(merkleLeaf1);
        merkleNode1.setSecondNode(merkleLeaf2);
        merkleNode1.setHash("hash");

        MerkleNode merkleNode2 = new MerkleNode();
        merkleNode2.setFirstNode(merkleLeaf3);
        merkleNode2.setSecondNode(merkleLeaf4);
        merkleNode2.setHash("hash");

        MerkleNode expectedMerkleNode = new MerkleNode();
        expectedMerkleNode.setFirstNode(merkleNode1);
        expectedMerkleNode.setSecondNode(merkleNode2);
        expectedMerkleNode.setHash("hash");

        MerkleBlock expectedMerkleBlock = new MerkleBlock();
        expectedMerkleBlock.setPreviousHash("previousHash");
        expectedMerkleBlock.setHash("hash");
        expectedMerkleBlock.setTimeStamp(123456789);
        expectedMerkleBlock.setMerkleTree(expectedMerkleNode);


        FileReader fileReader = new FileReader(FILE_PATH);

        Type typeMyType = new TypeToken<List<MerkleBlock>>() {
        }.getType();
        List<MerkleBlock> actualMerkleBlock = new GsonBuilder().registerTypeAdapter(IMerkleNode.class, new MerkleNodeAdapter()).create().fromJson(fileReader, typeMyType);
        fileReader.close();

        assertEquals(expectedMerkleBlock.getMerkleTree(), actualMerkleBlock.get(0).getMerkleTree());
    }

}
