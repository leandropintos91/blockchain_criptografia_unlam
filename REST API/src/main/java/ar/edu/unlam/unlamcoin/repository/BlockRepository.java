package ar.edu.unlam.unlamcoin.repository;

import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.utils.FileUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlockRepository<T> {

    private static final String FILENAME = "/blockChain.json";
    public static final String GENESIS_HASH = "GenesisBlock";

    public Block<T> getByHash(String hash) throws UnsupportedEncodingException {
        List<Block<T>> blocks = getAll();
        for (Block<T> block : blocks) {
            if (block.getHash().equals(hash))
                return block;
        }

        return null;
    }

    public List<Block<T>> getAll() throws UnsupportedEncodingException {
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Block<T>>> typeReference = new TypeReference<>() {
        };
        List<Block<T>> blockList = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(FileUtils.getFile(FILENAME));
            blockList = mapper.readValue(is, typeReference);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return blockList;

    }

    public void save(List<Block<T>> blockChain) throws UnsupportedEncodingException {
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        try {
            out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
            mapper.writeValue(out, blockChain);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() throws UnsupportedEncodingException {
        FileUtils.checkFile(FILENAME);
        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter out;

        try {
            List<Block<T>> b = getAll();
            b.clear();
            out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
            mapper.writeValue(out, b);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
