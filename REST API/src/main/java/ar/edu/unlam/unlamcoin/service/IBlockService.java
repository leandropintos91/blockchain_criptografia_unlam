package ar.edu.unlam.unlamcoin.service;

import ar.edu.unlam.unlamcoin.structure.Block;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IBlockService<T> {

    List<Block<?>> getAll() throws IOException;

    Block<?> getByHash(String hash) throws UnsupportedEncodingException;

    boolean save(Block<T> block) throws IOException;

    void deleteAll() throws IOException;
}
