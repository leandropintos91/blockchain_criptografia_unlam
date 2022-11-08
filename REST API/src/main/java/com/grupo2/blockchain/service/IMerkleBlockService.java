package com.grupo2.blockchain.service;

import com.grupo2.blockchain.structure.MerkleBlock;
import com.grupo2.blockchain.transactions.HasheableTransaction;

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
