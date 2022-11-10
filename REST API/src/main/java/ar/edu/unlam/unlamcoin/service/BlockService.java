package ar.edu.unlam.unlamcoin.service;

import ar.edu.unlam.unlamcoin.exception.InvalidBlockchainException;
import ar.edu.unlam.unlamcoin.repository.BlockRepository;
import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.structure.Hasher;
import ar.edu.unlam.unlamcoin.transactions.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@Service
public class BlockService<T> implements IBlockService<T> {

    private final Hasher hasher;
    private final BlockRepository<T> blockRepository;

    public BlockService(final Hasher hasher, final BlockRepository<T> blockRepository) {
        this.hasher = hasher;
        this.blockRepository = blockRepository;
    }

    @Override
    public List<Block<T>> getAll() throws IOException {
        List<Block<T>> blockchain = blockRepository.getAll();
        if (CollectionUtils.isEmpty(blockchain)) {
            blockchain.add(createGenesisBlock());
            blockRepository.save(blockchain);
        }
        return blockchain;
    }

    private Block<T> createGenesisBlock() {
        return (Block<T>) Block.builder().prevHash(BlockRepository.GENESIS_HASH).build();
    }

    @Override
    public Block<T> getByHash(String hash) throws UnsupportedEncodingException {
        return blockRepository.getByHash(hash);
    }

    @Override
    public void saveNewBlock(T transaction) throws IOException {
        List<Block<T>> blockChain = getAll();
        final String previousHash = blockChain.get(blockChain.size() - 1 ).getPrevHash();
        final Block<T> block = (Block<T>) Block.builder().data(transaction).prevHash(previousHash).build();

        if (hasher.isValidChain(blockChain)) {
            throw new InvalidBlockchainException();
        }

        blockChain.add(block);
        blockRepository.save(blockChain);
    }

    @Override
    public void deleteAll() throws IOException {
        blockRepository.deleteAll();
    }
}