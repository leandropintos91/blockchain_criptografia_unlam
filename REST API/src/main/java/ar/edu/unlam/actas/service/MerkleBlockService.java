package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.transactions.HashableTransaction;
import ar.edu.unlam.actas.repository.OldMerkleBlockRepository;
import ar.edu.unlam.actas.utils.HashUtils;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerkleBlockService {


    public List<MerkleBlock> getAll() throws IOException {
        return OldMerkleBlockRepository.getAll();
    }

    public MerkleBlock getByHash(String hash) throws IOException {
        return OldMerkleBlockRepository.getBlock(hash);
    }

    public List getPendingTransactions() throws IOException {
        return OldMerkleBlockRepository.getPendingTransactions();
    }

    public void save(MerkleBlock block) throws IOException {
        //MerkleBlockRepository.save(block);
    }

    public boolean save(HashableTransaction transaction) throws IOException {

        //Calculamos el hash de la transacci�n
        transaction.recalculateTimeStampAndHash();

        //Obtenemos las transacciones pendientes
        List pendingTransactions = OldMerkleBlockRepository.getPendingTransactions();

        if (pendingTransactions == null)
            pendingTransactions = new ArrayList();

        pendingTransactions.add(transaction);

        //Si llegamos a la cantidad necesaria para armar un bloque, lo armamos
        if (pendingTransactions.size() == 4) {

            List<MerkleBlock> merkleBlockChain = null;

            try {
                merkleBlockChain = getAll();
            } catch (MismatchedInputException e) {
                e.printStackTrace();
                System.out.println("[MerkleBlockService] - Blockchain Merkle vacia");
            }

            MerkleBlock newBlock;

            if (merkleBlockChain.size() > 0) {
                MerkleBlock lastBlock = merkleBlockChain.get(merkleBlockChain.size() - 1);
                newBlock = new MerkleBlock(lastBlock.getHash(), pendingTransactions);
            } else {
                newBlock = new MerkleBlock(HashUtils.GENESIS_HASH, pendingTransactions);
                merkleBlockChain = new ArrayList<>();
            }

            //Validamos la cadena antes de guardar
            if (HashUtils.isValidMerkleChain(merkleBlockChain)) {
                merkleBlockChain.add(newBlock);
                OldMerkleBlockRepository.save(merkleBlockChain);

                //Limpiamos las transacciones pendientes, ya que se agregaron a la cadena
                pendingTransactions.clear();
            } else
                return false;
        }

        OldMerkleBlockRepository.savePendingTransactions(pendingTransactions);
        return true;
    }

    public void deleteAll() throws IOException {
        OldMerkleBlockRepository.deleteAll();
    }
}
