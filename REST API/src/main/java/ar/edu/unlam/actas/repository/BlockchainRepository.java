package ar.edu.unlam.actas.repository;

import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.utils.FileUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlockchainRepository {

    private static final String FILENAME = "D:\\Blockchain\\blockChain.json";

    public Block findByHash(String hash) throws IOException {
        List<Block> blocks = findAll();
        for (Block block : blocks) {
            if (block.getHash().equals(hash))
                return block;
        }

        return null;
    }

    public List<Block> findAll() throws IOException {
        FileUtils.checkFileExists(FILENAME);
        FileReader fileReader = new FileReader(FileUtils.getFile(FILENAME));
        Type typeMyType = new TypeToken<List<Block>>() {
        }.getType();
        List<Block> blockchain = new GsonBuilder().serializeNulls().create().fromJson(fileReader, typeMyType);
        fileReader.close();
        if (blockchain != null) {
            return blockchain;
        }
        return new ArrayList<>();
    }

    public void save(List<Block> blockChain) throws IOException {
        FileUtils.checkFileExists(FILENAME);
        String serializedBlockchain = new GsonBuilder().serializeNulls().create().toJson(blockChain);
        FileWriter fileWriter = FileUtils.getFileWriter(FILENAME);
        fileWriter.write(serializedBlockchain);
        fileWriter.close();
    }

    public void deleteAll() throws IOException {
        save(List.of());
    }
}
