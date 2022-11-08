package com.grupo2.blockchain.structure;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.grupo2.blockchain.transactions.HasheableTransaction;

public class Hasher {
    /**
     * Método para hacer un hash en SHA-256 de una cadena
     * @param t: cadena a hashear
     * @return cadena hash en SHA-256
     */
    public static String hashSHA256Hex(String t){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodeHash = digest.digest(t.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < encodeHash.length; i++) {
                final String hex = Integer.toHexString(0xff & encodeHash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    /**
     * Método para validar la cadena de bloques.
     * @param blockChain: Cadena a validar
     * @return true en caso de válida y falso en caso de errónea
     */
    public static  boolean isValidChain(List<Block<?>> blockChain){
        Block<?> current;
        Block<?> previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currentHash = current.getHash();
            String previosHash = previous.getHash();

            if(!currentHash.equals(current.recalculateHash()))
                return false;

            if(!previosHash.equals(current.getPrevHash()))
                return false;
        }

        return true;
    }
    /**
     * Método para validar la cadena de bloques Merkle.
     * La diferenciamos de la cadena normal para no generar confusión
     * con más interfaces y clases genéricas
     * @param blockChain: Cadena Merkle a validar
     * @return true en caso de válida y falso en caso de errónea
     */
    public static boolean isValidMerkleChain(List<MerkleBlock<HasheableTransaction>> blockChain){
        MerkleBlock<?> current;
        MerkleBlock<?> previous;

        for (int i = 1; i < blockChain.size(); i++) {
            current = blockChain.get(i);
            previous = blockChain.get(i - 1);

            String currHash = current.obtainHash();
            String previousHash = previous.obtainHash();

            //Primero nos fijamos que el hash del bloque actual sea válido volviendo a hashearlo
            if(!currHash.equals(current.recalculateHash()))
                return false;

            //Luego nos fijamos que el hash del bloque anterior sea válido comparándolo con
            //el hash anterior que tiene el bloque actual
            if(!previousHash.equals(current.getPrevHash()))
                return false;
        }
        return true;
    }
}
