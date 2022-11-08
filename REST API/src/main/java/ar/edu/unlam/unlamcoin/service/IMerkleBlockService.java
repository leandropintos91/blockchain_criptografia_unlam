package ar.edu.unlam.unlamcoin.service;

import ar.edu.unlam.unlamcoin.structure.MerkleBlock;
import ar.edu.unlam.unlamcoin.transactions.HasheableTransaction;

import java.io.IOException;
import java.util.List;

public interface IMerkleBlockService {
    List<MerkleBlock<HasheableTransaction>> getAll() throws IOException;
    MerkleBlock<HasheableTransaction> getByHash(String hash) throws IOException;
    void save(MerkleBlock<HasheableTransaction> block) throws IOException;
    boolean save(HasheableTransaction transaction) throws IOException;
    List<HasheableTransaction> getPendingTransactions() throws IOException;
    void deleteAll() throws IOException;
}
