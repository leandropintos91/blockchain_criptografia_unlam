package ar.edu.unlam.unlamcoin.transactions;

import ar.edu.unlam.unlamcoin.structure.Hasher;
import ar.edu.unlam.unlamcoin.structure.MerkleBlock;
import ar.edu.unlam.unlamcoin.structure.MerkleTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TransactionMerkleBlockChainTest {

    private List<MerkleBlock<HashableTransaction>> merkleBlockChain;

    @BeforeEach
    public void init() {
        List<HashableTransaction> transactions = List.of(
                HashableTransaction.builder().sender("Leandro").receiver("Santiago").amount(300.00).build(),
                HashableTransaction.builder().sender("Santiago").receiver("Leandro").amount(300.00).build(),
                HashableTransaction.builder().sender("Leandro").receiver("Carolina").amount(300.00).build(),
                HashableTransaction.builder().sender("Carolina").receiver("Agustín").amount(300.00).build()
        );
        merkleBlockChain = new ArrayList<>();
        merkleBlockChain.add(new MerkleBlock<>("0", transactions));

        transactions = List.of(
                HashableTransaction.builder().sender("Santiago").receiver("Leandro").amount(300.00).build(),
                HashableTransaction.builder().sender("Carolina").receiver("Agustín").amount(300.00).build(),
                HashableTransaction.builder().sender("Leandro").receiver("Santiago").amount(300.00).build(),
                HashableTransaction.builder().sender("Carolina").receiver("Agustín").amount(300.00).build()
        );
        merkleBlockChain.add(new MerkleBlock<>(merkleBlockChain.get(merkleBlockChain.size() - 1).getHash(), transactions));

        transactions = List.of(
                HashableTransaction.builder().sender("Cristian").receiver("Carolina").amount(300.00).build(),
                HashableTransaction.builder().sender("Santiago").receiver("Agustín").amount(300.00).build(),
                HashableTransaction.builder().sender("Leandro").receiver("Santiago").amount(300.00).build(),
                HashableTransaction.builder().sender("Carolina").receiver("Cristian").amount(300.00).build()
        );
        merkleBlockChain.add(new MerkleBlock<>(merkleBlockChain.get(merkleBlockChain.size() - 1).getHash(), transactions));
    }

    @Test
    public void testValidTransactionBlockChain() {
        Assert.isTrue(Hasher.isValidMerkleChain(merkleBlockChain), "MerkleBlockChain invalida");
    }

    @Test
    public void testInvalidTransactionBlockChain() {
        MerkleBlock<HashableTransaction> b3 = merkleBlockChain.get(2);
        MerkleTree<HashableTransaction> b4 = b3.getMerkleTree();
        List<HashableTransaction> b5 = b4.getDataList();
        b5.get(2).setReceiver("ROMPI TODO");

        Assert.isTrue(!Hasher.isValidMerkleChain(merkleBlockChain), "MerkleBlockChain valida");
    }

}
