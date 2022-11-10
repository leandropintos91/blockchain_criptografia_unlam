package ar.edu.unlam.actas.service;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.merkletree.MerkleTree;
import ar.edu.unlam.actas.repository.MerkleBlockRepository;
import ar.edu.unlam.actas.model.transactions.HashableTransaction;
import ar.edu.unlam.actas.utils.HashUtils;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerkleBlockService implements IMerkleBlockService {


    @Override
    public List<MerkleBlock<HashableTransaction>> getAll() throws IOException {
        return MerkleBlockRepository.getAll();
    }

    @Override
    public MerkleBlock<HashableTransaction> getByHash(String hash) throws IOException {
        return MerkleBlockRepository.getBlock(hash);
    }

    @Override
    public List<HashableTransaction> getPendingTransactions() throws IOException {
        return MerkleBlockRepository.getPendingTransactions();
    }

    @Override
    public void save(MerkleBlock<HashableTransaction> block) throws IOException {
        //MerkleBlockRepository.save(block);
    }

    @Override
    public boolean save(HashableTransaction transaction) throws IOException {

        //Calculamos el hash de la transacci�n
        transaction.recalculateTimeStampAndHash();

        //Obtenemos las transacciones pendientes
        List<HashableTransaction> pendingTransactions = MerkleBlockRepository.getPendingTransactions();

        if (pendingTransactions == null)
            pendingTransactions = new ArrayList<HashableTransaction>();

        pendingTransactions.add(transaction);

        //Si llegamos a la cantidad necesaria para armar un bloque, lo armamos
        if (pendingTransactions.size() == MerkleTree.TRANSACTION_LIMIT) {

            List<MerkleBlock<HashableTransaction>> merkleBlockChain = null;

            try {
                merkleBlockChain = getAll();
            } catch (MismatchedInputException e) {
                e.printStackTrace();
                System.out.println("[MerkleBlockService] - Blockchain Merkle vacia");
            }

            MerkleBlock<HashableTransaction> newBlock;

            if (merkleBlockChain.size() > 0) {
                MerkleBlock<?> lastBlock = merkleBlockChain.get(merkleBlockChain.size() - 1);
                newBlock = new MerkleBlock<HashableTransaction>(lastBlock.getHash(), pendingTransactions);
            } else {
                newBlock = new MerkleBlock<HashableTransaction>(HashUtils.GENESIS_HASH, pendingTransactions);
                merkleBlockChain = new ArrayList<>();
            }

            //Validamos la cadena antes de guardar
            if (HashUtils.isValidMerkleChain(merkleBlockChain)) {
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
