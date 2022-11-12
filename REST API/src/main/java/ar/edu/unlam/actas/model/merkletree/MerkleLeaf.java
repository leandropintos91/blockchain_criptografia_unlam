package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleLeaf implements IMerkleNode {
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
    @JsonIgnore
    public List<Acta> getDataBlock() {
        return new LinkedList<>(List.of(data));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MerkleLeaf)) {
            return false;
        }

        MerkleLeaf other = (MerkleLeaf) obj;
        return this.data.equals(other.data) && this.hash.equals(other.hash);
    }
}
