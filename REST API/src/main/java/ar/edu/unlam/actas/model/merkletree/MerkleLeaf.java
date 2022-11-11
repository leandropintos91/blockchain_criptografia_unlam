package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleLeaf implements IMerkleNode{
    private Acta data;
    private String hash;

    @Builder
    public MerkleLeaf(final Acta data) {
        this.data = data;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256(data.toString());
    }

    @Override
    public List<Acta> getDataBlock() {
        return new LinkedList<>(List.of(data));
    }
}
