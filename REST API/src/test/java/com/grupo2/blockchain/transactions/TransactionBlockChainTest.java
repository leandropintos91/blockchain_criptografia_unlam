package com.grupo2.blockchain.transactions;

import com.grupo2.blockchain.structure.Block;
import com.grupo2.blockchain.structure.Hasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TransactionBlockChainTest {
    private List<Block<?>> blockChain;

    @BeforeEach
    public void initBlockChain(){
        blockChain = new ArrayList<>();
        blockChain.add(new Block<>("0", new Transaction("Messi", "Maradona", 300D)));
        blockChain.add(new Block<>(blockChain.get(blockChain.size() - 1).getHash(), new Transaction("Maradona", "Messi", 300D)));
        blockChain.add(new Block<>(blockChain.get(blockChain.size() - 1).getHash(), new Transaction("Messi", "Pelé", 300D)));
        blockChain.add(new Block<>(blockChain.get(blockChain.size() - 1).getHash(), new Transaction("Pelé", "Ronaldinho", 300D)));
        blockChain.add(new Block<>(blockChain.get(blockChain.size() - 1).getHash(), new Transaction("Ronaldinho", "Ronaldo", 300D)));
        blockChain.add(new Block<>(blockChain.get(blockChain.size() - 1).getHash(), new Transaction("Ronaldo", "Agüero", 300D)));
    }
    @Test
    public void testValidTransactionBlockChain(){
        Assert.isTrue(Hasher.isValidChain(blockChain), "La blockChain es invalida");
    }

    @Test
    public void testInvalidTransactionBlockChain(){
        Block<Transaction> interceptedBlock = (Block<Transaction>) blockChain.get(2);

        interceptedBlock.setData(new Transaction("Maradona", "Messi", 300D));

        Assert.isTrue(!Hasher.isValidChain(blockChain), "La blockChain es valida");
    }
}
