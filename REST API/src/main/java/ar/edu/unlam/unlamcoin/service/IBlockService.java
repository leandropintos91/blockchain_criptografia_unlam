package ar.edu.unlam.unlamcoin.service;

import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.transactions.Transaction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IBlockService<T> {

    List<Block<T>> getAll() throws IOException;

    Block<T> getByHash(String hash) throws UnsupportedEncodingException;


    void saveNewBlock(T transaction) throws IOException;

    void deleteAll() throws IOException;
}
