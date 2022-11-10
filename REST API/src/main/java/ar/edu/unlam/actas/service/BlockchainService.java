package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.model.transactions.Acta;

import java.io.IOException;
import java.util.List;

public interface BlockchainService {

    List<Block> getAllBlocks() throws IOException;

    Block getBlockByHash(String hash) throws IOException;


    void saveNewBlock(List<Acta> transaction) throws IOException;

    void deleteBlockchain() throws IOException;
}
