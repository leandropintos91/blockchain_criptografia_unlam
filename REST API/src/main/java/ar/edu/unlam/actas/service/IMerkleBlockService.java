package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.transactions.HashableTransaction;

import java.io.IOException;
import java.util.List;

public interface IMerkleBlockService {
    List<MerkleBlock<HashableTransaction>> getAll() throws IOException;

    MerkleBlock<HashableTransaction> getByHash(String hash) throws IOException;

    void save(MerkleBlock<HashableTransaction> block) throws IOException;

    boolean save(HashableTransaction transaction) throws IOException;

    List<HashableTransaction> getPendingTransactions() throws IOException;

    void deleteAll() throws IOException;
}
