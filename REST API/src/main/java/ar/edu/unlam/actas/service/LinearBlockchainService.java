package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.exception.BlockNotFoundException;
import ar.edu.unlam.actas.exception.InvalidBlockchainException;
import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.repository.BlockRepository;
import ar.edu.unlam.actas.utils.HashUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class LinearBlockchainService<T> implements BlockchainService<T> {

    private final HashUtils hashUtils;
    private final BlockRepository<T> blockRepository;

    public LinearBlockchainService(final HashUtils hashUtils, final BlockRepository<T> blockRepository) {
        this.hashUtils = hashUtils;
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
        return (Block<T>) Block.builder().previousHash(BlockRepository.GENESIS_HASH).build();
    }

    @Override
    public Block<T> getByHash(String hash) throws UnsupportedEncodingException {
        Block<T> retrievedBlock = blockRepository.getByHash(hash);
        if(retrievedBlock == null) {
            throw new BlockNotFoundException();
        }
        return blockRepository.getByHash(hash);
    }

    @Override
    public void saveNewBlock(T transaction) throws IOException {
        List<Block<T>> blockchain = getAll();
        final String previousHash = blockchain.get(blockchain.size() - 1 ).getHash();
        final Block<T> newBlock = (Block<T>) Block.builder().data(transaction).previousHash(previousHash).build();
        blockchain.add(newBlock);

        if (!hashUtils.isValidLinearChain(blockchain)) {
            throw new InvalidBlockchainException();
        }

        blockRepository.save(blockchain);
    }

    @Override
    public void deleteAll() throws IOException {
        blockRepository.deleteAll();
    }
}