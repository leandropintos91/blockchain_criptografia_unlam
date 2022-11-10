package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.exception.BlockNotFoundException;
import ar.edu.unlam.actas.exception.InvalidBlockchainException;
import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.repository.BlockchainRepository;
import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Service
public class LinearBlockchainService implements BlockchainService {

    private final HashUtils hashUtils;
    private final BlockchainRepository blockchainRepository;

    public LinearBlockchainService(final HashUtils hashUtils, final BlockchainRepository blockchainRepository) {
        this.hashUtils = hashUtils;
        this.blockchainRepository = blockchainRepository;
    }

    @Override
    public List<Block> getAllBlocks() throws IOException {
        List<Block> blockchain = blockchainRepository.findAll();
        if (CollectionUtils.isEmpty(blockchain)) {
            blockchain.add(createGenesisBlock());
            blockchainRepository.save(blockchain);
        }
        if(!hashUtils.isValidLinearChain(blockchain)) {
            throw new InvalidBlockchainException();
        }
        return blockchain;
    }

    private Block createGenesisBlock() {
        return Block.builder().previousHash(HashUtils.GENESIS_HASH).build();
    }

    @Override
    public Block getBlockByHash(String hash) throws IOException {
        Block retrievedBlock = blockchainRepository.findByHash(hash);
        if (retrievedBlock == null) {
            throw new BlockNotFoundException();
        }
        return blockchainRepository.findByHash(hash);
    }

    @Override
    public void saveNewBlock(Acta transaction) throws IOException {
        List<Block> blockchain = getAllBlocks();
        final String previousHash = blockchain.get(blockchain.size() - 1).getHash();
        final Block newBlock = Block.builder().data(transaction).previousHash(previousHash).build();
        blockchain.add(newBlock);

        if (!hashUtils.isValidLinearChain(blockchain)) {
            throw new InvalidBlockchainException();
        }

        blockchainRepository.save(blockchain);
    }

    @Override
    public void deleteBlockchain() throws IOException {
        blockchainRepository.deleteAll();
    }
}