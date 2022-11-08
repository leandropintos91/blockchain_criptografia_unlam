package ar.edu.unlam.unlamcoin.transactions;

import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.structure.Hasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TransactionWithCurrencyBlockChainTest {

    private List<Block<?>> blockChain;

    @BeforeEach
    public void init() {
        blockChain = new ArrayList<>();
        blockChain.add(new Block<TransactionWithCurrency>("0", new TransactionWithCurrency("Messi", "Maradona", "USD", 300D)));
        blockChain.add(new Block<TransactionWithCurrency>(blockChain.get(blockChain.size() - 1).getHash(), new TransactionWithCurrency("Maradona", "Messi", "USD", 300D)));
        blockChain.add(new Block<TransactionWithCurrency>(blockChain.get(blockChain.size() - 1).getHash(), new TransactionWithCurrency("Messi", "Pelé", "USD", 300D)));
        blockChain.add(new Block<TransactionWithCurrency>(blockChain.get(blockChain.size() - 1).getHash(), new TransactionWithCurrency("Pelé", "Ronaldinho", "USD", 300D)));
        blockChain.add(new Block<TransactionWithCurrency>(blockChain.get(blockChain.size() - 1).getHash(), new TransactionWithCurrency("Ronaldinho", "Ronaldo", "USD", 300D)));
        blockChain.add(new Block<TransactionWithCurrency>(blockChain.get(blockChain.size() - 1).getHash(), new TransactionWithCurrency("Ronaldo", "Agüero", "USD", 300D)));
    }

    @Test
    public void testValidTransactionBlockChain() {
        Assert.isTrue(Hasher.isValidChain(blockChain), "BlockChain invalida");
    }

    @Test
    public void testInvalidTransactionBlockChain() {
        Block<TransactionWithCurrency> interceptedBlock = (Block<TransactionWithCurrency>) blockChain.get(2);
        interceptedBlock.setData(new TransactionWithCurrency("Maradona", "Messi", "ARS", 300D));

        Assert.isTrue(!Hasher.isValidChain(blockChain), "BlockChain valida");
    }

}
