package ar.edu.unlam.actas.utils;


import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class HashUtils {

    public static final String GENESIS_HASH = "GenesisBlock";

    public static String hash256(String stringToHash) {
        return Hashing.sha256().hashString(stringToHash, StandardCharsets.UTF_8).toString();
    }

    public boolean isValidLinearChain(List<Block> blockChain) {
        Block current;
        Block previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currentHash = current.getHash();
            String previosHash = previous.getHash();

            if (!current.getPreviousHash().equals(previous.getHash()))
                return false;

            if (!currentHash.equals(current.calculateHash()))
                return false;

            if (!previosHash.equals(previous.calculateHash()))
                return false;
        }

        return true;
    }

    public static boolean isValidMerkleChain(List<MerkleBlock> blockChain) {
        MerkleBlock current;
        MerkleBlock previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currHash = current.getHash();
            String previousHash = previous.getHash();

            if (!currHash.equals(current.calculateHash()))
                return false;

            if (!previousHash.equals(current.getPreviousHash()))
                return false;
        }
        return true;
    }
}
