package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;

import java.util.List;

public interface IMerkleNode {
    String calculateHash();

    String getHash();

    List<Acta> getDataBlock();
}
