package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.transactions.Acta;

import java.io.IOException;
import java.util.List;

public interface IMerkleBlockchainService {

    List<MerkleBlock> getAllBlocks() throws IOException;

    MerkleBlock getBlockByHash(String hash) throws IOException;


    void saveNewBlock(List<Acta> transaction) throws IOException;

    void deleteBlockchain() throws IOException;
}
