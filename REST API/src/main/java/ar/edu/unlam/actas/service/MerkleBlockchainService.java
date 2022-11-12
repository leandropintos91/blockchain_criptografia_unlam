package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.exception.BlockNotFoundException;
import ar.edu.unlam.actas.exception.InvalidBlockchainException;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.repository.MerkleBlockchainRepository;
import ar.edu.unlam.actas.utils.HashUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Service
public class MerkleBlockchainService implements IMerkleBlockchainService {

    private final MerkleBlockchainRepository blockchainRepository;

    public MerkleBlockchainService(final MerkleBlockchainRepository blockchainRepository) {
        this.blockchainRepository = blockchainRepository;
    }

    @Override
    public List<MerkleBlock> getAllBlocks() throws IOException {
        List<MerkleBlock> blockchain = blockchainRepository.findAll();
        if (CollectionUtils.isEmpty(blockchain)) {
            blockchain.add(createGenesisBlock());
            blockchainRepository.save(blockchain);
        }
        if (!HashUtils.isValidMerkleChain(blockchain)) {
            throw new InvalidBlockchainException();
        }
        return blockchain;
    }

    private MerkleBlock createGenesisBlock() {
        return MerkleBlock.builder().previousHash(HashUtils.GENESIS_HASH).build();
    }

    @Override
    public MerkleBlock getBlockByHash(String hash) throws IOException {
        MerkleBlock retrievedBlock = blockchainRepository.findByHash(hash);
        if (retrievedBlock == null) {
            throw new BlockNotFoundException();
        }
        return blockchainRepository.findByHash(hash);
    }

    @Override
    public void saveNewBlock(List<Acta> transaction) throws IOException {
        List<MerkleBlock> blockchain = getAllBlocks();
        final String previousHash = blockchain.get(blockchain.size() - 1).getHash();
        final MerkleBlock newBlock = MerkleBlock.builder().merkleTree(transaction).previousHash(previousHash).build();
        blockchain.add(newBlock);

        if (!HashUtils.isValidMerkleChain(blockchain)) {
            throw new InvalidBlockchainException();
        }

        blockchainRepository.save(blockchain);
    }

    @Override
    public void deleteBlockchain() throws IOException {
        blockchainRepository.deleteAll();
    }

}
