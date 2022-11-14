package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleBlock {

    private String previousHash;
    private long timeStamp;
    private IMerkleNode merkleTree;
    private String hash;


    @Builder
    public MerkleBlock(final String previousHash, final List<Acta> merkleTree) {
        this.previousHash = previousHash;
        if (merkleTree == null || merkleTree.size() == 0) {
            this.merkleTree = null;
        } else {
            if (merkleTree.size() == 1) {
                this.merkleTree = MerkleLeaf.builder().data(merkleTree.get(0)).build();
            } else {
                this.merkleTree = MerkleNode.builder().data(merkleTree).build();
            }
        }
        Date today = new Date();
        this.timeStamp = today.getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256(previousHash + (merkleTree != null ? new GsonBuilder().serializeNulls().create().toJson(merkleTree) : "") + timeStamp);
    }

    public boolean hasValidHash() {
        if (!this.hash.equals(calculateHash())) {
            return false;
        }
        if (this.merkleTree != null) {
            return this.merkleTree.hasValidHash();
        }
        return true;
    }
}
