package com.grupo2.blockchain.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupo2.blockchain.repository.BlockRepository;
import com.grupo2.blockchain.structure.Block;
import com.grupo2.blockchain.structure.Hasher;

@Service
public class BlockService<T> implements IBlockService<T> {

	@Override
	public List<Block<?>> getAll() throws IOException {
		return BlockRepository.getAll();
	}

	@Override
	public Block<?> getByHash(String hash) throws UnsupportedEncodingException {
		return BlockRepository.getByHash(hash);
	}

	@Override
	public boolean save(Block<T> block) throws IOException {
		List<Block<?>> blockChain = getAll();
		
		if(blockChain.size() > 0) {
			Block<?> lastBlock = blockChain.get(blockChain.size() - 1);
			block.setPrevHash(lastBlock.getHash());
		} else {
			block.setPrevHash(BlockRepository.GENESIS_HASH);
			blockChain = new ArrayList<>();
		}
		
		if(Hasher.isValidChain(blockChain)) {
			blockChain.add(block);
			BlockRepository.save(blockChain);
			return true;
		} else
			return false;
	}

	@Override
	public void deleteAll() throws IOException {
		BlockRepository.deleteAll();
	}
}
