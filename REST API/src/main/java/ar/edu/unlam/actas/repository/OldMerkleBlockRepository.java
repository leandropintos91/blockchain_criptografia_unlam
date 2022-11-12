package ar.edu.unlam.actas.repository;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.utils.FileUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OldMerkleBlockRepository {
    private static final String FILENAME = "/merkleBlockchain.json";
    private static final String PENDING_TRANSACTIONS_FILENAME = "/pendingTransactions.json";
    private static final String LOGGER_HEADER = "[MerkleBlockRepository] - ";

    public static MerkleBlock getBlock(String hash) throws IOException {

        List<MerkleBlock> allBlocks = getAll();

        for (MerkleBlock block : allBlocks) {
            if (block.getHash().equals(hash))
                return block;
        }

        return null;
    }

    public static List<MerkleBlock> getAll() throws IOException {
        FileUtils.checkFileExists(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<MerkleBlock>> typeReference = new TypeReference<List<MerkleBlock>>() {
        };
        List<MerkleBlock> merkleBlockList = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(FileUtils.getFile(FILENAME));
            merkleBlockList = mapper.readValue(is, typeReference);

        } catch (IOException e) {
            System.out.println(LOGGER_HEADER + "La cadena merkle esta vacia.");
        }

        return merkleBlockList;
    }

    public static void save(List<MerkleBlock> blockChain) throws IOException {
        FileUtils.checkFileExists(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
        mapper.writeValue(out, blockChain);
        out.close();
    }

    public static List getPendingTransactions() throws IOException {
        FileUtils.checkFileExists(PENDING_TRANSACTIONS_FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List> typeReference = new TypeReference<List>() {
        };

        try {
            InputStream is = new FileInputStream(FileUtils.getFile(PENDING_TRANSACTIONS_FILENAME));
            List pendingTransactionsList = mapper.readValue(is, typeReference);
            return pendingTransactionsList;
        } catch (IOException e) {
            System.out.println(LOGGER_HEADER + "No hay transacciones pendientes.");
        }

        return null;
    }

    public static void savePendingTransactions(List pendingTransactions) throws IOException {
        FileUtils.checkFileExists(PENDING_TRANSACTIONS_FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        out = new BufferedWriter(FileUtils.getFileWriter(PENDING_TRANSACTIONS_FILENAME));
        mapper.writeValue(out, pendingTransactions);
        out.close();
    }

    public static void deleteAll() throws UnsupportedEncodingException {
        FileUtils.checkFileExists(PENDING_TRANSACTIONS_FILENAME);
        FileUtils.checkFileExists(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        try {
            List<MerkleBlock> b = getAll();
            b.clear();
            out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
            mapper.writeValue(out, b);
            out.close();
            List pendT = getPendingTransactions();
            pendT.clear();
            out = new BufferedWriter(FileUtils.getFileWriter(PENDING_TRANSACTIONS_FILENAME));
            mapper.writeValue(out, pendT);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
