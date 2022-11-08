package com.grupo2.blockchain.service;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.grupo2.blockchain.repository.BlockRepository;
import com.grupo2.blockchain.repository.MerkleBlockRepository;
import com.grupo2.blockchain.structure.Hasher;
import com.grupo2.blockchain.structure.MerkleBlock;
import com.grupo2.blockchain.structure.MerkleTree;
import com.grupo2.blockchain.transactions.HasheableTransaction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerkleBlockService implements IMerkleBlockService {


    @Override
    public List<MerkleBlock<HasheableTransaction>> getAll() throws IOException {
        return MerkleBlockRepository.getAll();
    }

    @Override
    public MerkleBlock<HasheableTransaction> getByHash(String hash) throws IOException {
        return MerkleBlockRepository.getBlock(hash);
    }

    @Override
	public List<HasheableTransaction> getPendingTransactions() throws IOException {
		return MerkleBlockRepository.getPendingTransactions();
	}

    @Override
    public void save(MerkleBlock<HasheableTransaction> block) throws IOException {
        //MerkleBlockRepository.save(block);
    }

    @Override
	public boolean save(HasheableTransaction transaction) throws IOException {
    	
    	//Calculamos el hash de la transacci�n
    	transaction.recalculateTimeStampAndHash();
    	
    	//Obtenemos las transacciones pendientes
		List<HasheableTransaction> pendingTransactions = MerkleBlockRepository.getPendingTransactions();
		
		if(pendingTransactions == null)
			pendingTransactions = new ArrayList<HasheableTransaction>();
		
		pendingTransactions.add(transaction);
		
		//Si llegamos a la cantidad necesaria para armar un bloque, lo armamos
		if(pendingTransactions.size() == MerkleTree.TRANSACTION_LIMIT) {
			
			List<MerkleBlock<HasheableTransaction>> merkleBlockChain = null;
			
			try {
				merkleBlockChain = getAll();
			} catch (MismatchedInputException e) {
		    	e.printStackTrace();
				System.out.println("[MerkleBlockService] - Blockchain Merkle vacia");
			}
			
			MerkleBlock<HasheableTransaction> newBlock;
			
			if(merkleBlockChain.size() > 0) {
				MerkleBlock<?> lastBlock = merkleBlockChain.get(merkleBlockChain.size() - 1);
				newBlock = new MerkleBlock<HasheableTransaction>(lastBlock.obtainHash(), pendingTransactions);
			} else {
				newBlock = new MerkleBlock<HasheableTransaction>(BlockRepository.GENESIS_HASH, pendingTransactions);
				merkleBlockChain = new ArrayList<>();
			}
			
			//Validamos la cadena antes de guardar
			if(Hasher.isValidMerkleChain(merkleBlockChain)) {
				merkleBlockChain.add(newBlock);
				MerkleBlockRepository.save(merkleBlockChain);
				
				//Limpiamos las transacciones pendientes, ya que se agregaron a la cadena
				pendingTransactions.clear();
			} else
				return false;
		}
		
		MerkleBlockRepository.savePendingTransactions(pendingTransactions);
		return true;
	}

    @Override
	public void deleteAll() throws IOException {
    	MerkleBlockRepository.deleteAll();
	}
}
