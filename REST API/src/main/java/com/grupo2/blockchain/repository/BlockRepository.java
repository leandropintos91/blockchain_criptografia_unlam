package com.grupo2.blockchain.repository;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo2.blockchain.structure.Block;
import com.grupo2.blockchain.utils.FileUtils;

public class BlockRepository<T> {
	
	private static final String FILENAME = "/blockChain.json";
	public static final String GENESIS_HASH = "0";

	public static Block<?> getByHash(String hash) throws UnsupportedEncodingException {
		List<Block<?>> blocks = getAll();
		for(Block<?> block : blocks){
            if(block.getHash().equals(hash))
                return block;
        }

        return null;
	}
	
	public static List<Block<?>> getAll() throws UnsupportedEncodingException {
		FileUtils.checkFile(FILENAME);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Block<?>>> typeReference = new TypeReference<List<Block<?>>>(){};
		List<Block<?>> b = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(FileUtils.getFile(FILENAME));
			b = mapper.readValue(is,typeReference);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return b;

	}

	public static void save(List<Block<?>> blockChain) throws UnsupportedEncodingException {
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

	public static void deleteAll() throws UnsupportedEncodingException {
		FileUtils.checkFile(FILENAME);
		ObjectMapper mapper = new ObjectMapper();
		BufferedWriter out;
		
		try {
			List<Block<?>> b = getAll();
			b.clear();
			out = new BufferedWriter(FileUtils.getFileWriter(FILENAME));
			mapper.writeValue(out, b);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
