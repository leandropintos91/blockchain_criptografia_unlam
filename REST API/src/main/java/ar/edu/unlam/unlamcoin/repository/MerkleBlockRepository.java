package ar.edu.unlam.unlamcoin.repository;

import ar.edu.unlam.unlamcoin.structure.MerkleBlock;
import ar.edu.unlam.unlamcoin.transactions.HasheableTransaction;
import ar.edu.unlam.unlamcoin.utils.FileUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MerkleBlockRepository {
    private static final String FILENAME = "/merkleBlockchain.json";
    private static final String PENDING_TRANSACTIONS_FILENAME = "/pendingTransactions.json";
    private static final String LOGGER_HEADER = "[MerkleBlockRepository] - ";

    public static MerkleBlock<HasheableTransaction> getBlock(String hash) throws IOException {

        List<MerkleBlock<HasheableTransaction>> allBlocks = getAll();

        for(MerkleBlock<HasheableTransaction> block : allBlocks){
            if(block.obtainHash().equals(hash))
                return block;
        }

        return null;
    }

    public static List<MerkleBlock<HasheableTransaction>> getAll() throws IOException {
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<MerkleBlock<HasheableTransaction>>> typeReference = new TypeReference<List<MerkleBlock<HasheableTransaction>>>() {};
        List<MerkleBlock<HasheableTransaction>> merkleBlockList = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(FileUtils.getFile(FILENAME));
            merkleBlockList = mapper.readValue(is,typeReference);

        } catch (IOException e) {
            System.out.println(LOGGER_HEADER + "La cadena merkle esta vacia.");
        }

        return merkleBlockList;
    }

    public static void save(List<MerkleBlock<HasheableTransaction>> blockChain) throws IOException {
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
        mapper.writeValue(out, blockChain);
        out.close();
    }

    public static List<HasheableTransaction> getPendingTransactions() throws IOException {
        FileUtils.checkFile(PENDING_TRANSACTIONS_FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<HasheableTransaction>> typeReference = new TypeReference<List<HasheableTransaction>>() {};

        try {
            InputStream is = new FileInputStream(FileUtils.getFile(PENDING_TRANSACTIONS_FILENAME));
            List<HasheableTransaction> pendingTransactionsList = mapper.readValue(is,typeReference);
            return pendingTransactionsList;
        } catch (IOException e) {
            System.out.println(LOGGER_HEADER + "No hay transacciones pendientes.");
        }

        return null;
    }

    public static void savePendingTransactions(List<HasheableTransaction> pendingTransactions) throws IOException {
        FileUtils.checkFile(PENDING_TRANSACTIONS_FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        out = new BufferedWriter(FileUtils.getFileWriter(PENDING_TRANSACTIONS_FILENAME));
        mapper.writeValue(out, pendingTransactions);
        out.close();
    }

    public static void deleteAll() throws UnsupportedEncodingException {
        FileUtils.checkFile(PENDING_TRANSACTIONS_FILENAME);
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        try {
            List<MerkleBlock<HasheableTransaction>> b = getAll();
            b.clear();
            out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
            mapper.writeValue(out, b);
            out.close();
            List<HasheableTransaction> pendT = getPendingTransactions();
            pendT.clear();
            out = new BufferedWriter(FileUtils.getFileWriter(PENDING_TRANSACTIONS_FILENAME));
            mapper.writeValue(out, pendT);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
