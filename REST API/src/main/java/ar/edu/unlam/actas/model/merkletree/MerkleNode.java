package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleNode {
    private MerkleNode firstNode;
    private MerkleNode secondNode;
    private String hash;

    @Builder
    public MerkleNode(final String previousHash, final List<Acta> data) {
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256(previousHash + (data != null ? data.toString() : "") + timeStamp);
    }
}
