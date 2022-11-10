package ar.edu.unlam.actas.utils;


import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.transactions.HashableTransaction;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class HashUtils {

    public static String hash256(String stringToHash) {
        return Hashing.sha256().hashString(stringToHash, StandardCharsets.UTF_8).toString();
    }

    public <T> boolean isValidLinearChain(List<Block<T>> blockChain) {
        Block<?> current;
        Block<?> previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currentHash = current.getHash();
            String previosHash = previous.getHash();

            if (!currentHash.equals(current.recalculateHash()))
                return false;

            if (!previosHash.equals(current.getPreviousHash()))
                return false;
        }

        return true;
    }

    public static boolean isValidMerkleChain(List<MerkleBlock<HashableTransaction>> blockChain) {
        MerkleBlock<?> current;
        MerkleBlock<?> previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currHash = current.getHash();
            String previousHash = previous.getHash();

            //Primero nos fijamos que el hash del bloque actual sea válido volviendo a hashearlo
            if (!currHash.equals(current.recalculateHash()))
                return false;

            //Luego nos fijamos que el hash del bloque anterior sea válido comparándolo con
            //el hash anterior que tiene el bloque actual
            if (!previousHash.equals(current.getPrevHash()))
                return false;
        }
        return true;
    }
}