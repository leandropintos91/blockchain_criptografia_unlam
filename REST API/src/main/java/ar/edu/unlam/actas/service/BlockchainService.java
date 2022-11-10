package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.linearblockchain.Block;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BlockchainService<T> {

    List<Block<T>> getAll() throws IOException;

    Block<T> getByHash(String hash) throws UnsupportedEncodingException;


    void saveNewBlock(T transaction) throws IOException;

    void deleteAll() throws IOException;
}
