package ar.edu.unlam.actas.repository;

import ar.edu.unlam.actas.model.merkletree.IMerkleNode;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
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
public class MerkleBlockchainRepository {

    private static final String FILENAME = ".\\merkleBlockChain.json";

    public MerkleBlock findByHash(String hash) throws IOException {
        List<MerkleBlock> blocks = findAll();
        for (MerkleBlock block : blocks) {
            if (block.getHash().equals(hash))
                return block;
        }
        return null;
    }

    public List<MerkleBlock> findAll() throws IOException {
        FileUtils.checkFileExists(FILENAME);
        FileReader fileReader = new FileReader(FileUtils.getFile(FILENAME));
        Type typeMyType = new TypeToken<List<MerkleBlock>>() {
        }.getType();
        List<MerkleBlock> blockchain = new GsonBuilder().registerTypeAdapter(IMerkleNode.class, new MerkleNodeAdapter()).serializeNulls().create().fromJson(fileReader, typeMyType);
        fileReader.close();
        if (blockchain != null) {
            return blockchain;
        }
        return new ArrayList<>();
    }

    public void save(List<MerkleBlock> blockChain) throws IOException {
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
