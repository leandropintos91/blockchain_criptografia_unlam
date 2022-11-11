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
public class MerkleBlock {
    private String previousHash;
    private long timeStamp;
    private List<Acta> data;
    private String hash;


    @Builder
    public MerkleBlock(final String previousHash, final List<Acta> data) {
        this.previousHash = previousHash;
        this.data = data;
        Date today = new Date();
        this.timeStamp = today.getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256(previousHash + (data != null ? data.toString() : "") + timeStamp);
    }
}
