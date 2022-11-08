package com.grupo2.blockchain.transactions;

import com.grupo2.blockchain.structure.Hasher;
import com.grupo2.blockchain.structure.MerkleBlock;
import com.grupo2.blockchain.structure.MerkleTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TransactionMerkleBlockChainTest {

    private List<MerkleBlock<HasheableTransaction>> merkleBlockChain;

    @BeforeEach
    public void init(){
        List<HasheableTransaction> transactions = new ArrayList<>();
        merkleBlockChain = new ArrayList<>();
        transactions.add(new HasheableTransaction("Messi", "Maradona", 300D));
        transactions.add(new HasheableTransaction("Maradona", "Messi", 300D));
        transactions.add(new HasheableTransaction("Messi", "Pelé", 300D));
        transactions.add(new HasheableTransaction("Pelé", "Ronaldinho", 300D));
        merkleBlockChain.add(new MerkleBlock<HasheableTransaction>("0", transactions));
        transactions = new ArrayList<>();
        transactions.add(new HasheableTransaction("Maradona", "Messi", 300D));
        transactions.add(new HasheableTransaction("Pelé", "Ronaldinho", 300D));
        transactions.add(new HasheableTransaction("Messi", "Maradona", 300D));
        transactions.add(new HasheableTransaction("Pelé", "Ronaldinho", 300D));
        merkleBlockChain.add(new MerkleBlock<HasheableTransaction>(merkleBlockChain.get(merkleBlockChain.size() - 1).obtainHash(), transactions));
        transactions = new ArrayList<>();
        transactions.add(new HasheableTransaction("Agüero", "Pelé", 300D));
        transactions.add(new HasheableTransaction("Maradona", "Ronaldinho", 300D));
        transactions.add(new HasheableTransaction("Messi", "Maradona", 300D));
        transactions.add(new HasheableTransaction("Pelé", "Agüero", 300D));
        merkleBlockChain.add(new MerkleBlock<HasheableTransaction>(merkleBlockChain.get(merkleBlockChain.size() - 1).obtainHash(), transactions));
    }

    @Test
    public void testValidTransactionBlockChain(){
        Assert.isTrue(Hasher.isValidMerkleChain(merkleBlockChain), "MerkleBlockChain invalida");
    }

    @Test
    public void testInvalidTransactionBlockChain(){
        MerkleBlock<HasheableTransaction> b3 = (MerkleBlock<HasheableTransaction>) merkleBlockChain.get(2);
        MerkleTree<HasheableTransaction> b4 = b3.getMerkleTree();
        List<HasheableTransaction> b5 = b4.getDataList();
        b5.get(2).setReceiver("ROMPI TODO");

        Assert.isTrue(!Hasher.isValidMerkleChain(merkleBlockChain), "MerkleBlockChain valida");
    }

}
