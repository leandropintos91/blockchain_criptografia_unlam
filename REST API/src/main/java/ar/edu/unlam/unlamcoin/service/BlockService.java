package ar.edu.unlam.unlamcoin.service;

import ar.edu.unlam.unlamcoin.repository.BlockRepository;
import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.structure.Hasher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlockService<T> implements IBlockService<T> {

    @Override
    public List<Block<?>> getAll() throws IOException {
        return BlockRepository.getAll();
    }

    @Override
    public Block<?> getByHash(String hash) throws UnsupportedEncodingException {
        return BlockRepository.getByHash(hash);
    }

    @Override
    public boolean save(Block<T> block) throws IOException {
        List<Block<?>> blockChain = getAll();

        if (blockChain.size() > 0) {
            Block<?> lastBlock = blockChain.get(blockChain.size() - 1);
            block.setPrevHash(lastBlock.getHash());
        } else {
            block.setPrevHash(BlockRepository.GENESIS_HASH);
            blockChain = new ArrayList<>();
        }

        if (Hasher.isValidChain(blockChain)) {
            blockChain.add(block);
            BlockRepository.save(blockChain);
            return true;
        } else
            return false;
    }

    @Override
    public void deleteAll() throws IOException {
        BlockRepository.deleteAll();
    }
}