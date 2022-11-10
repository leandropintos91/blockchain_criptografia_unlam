package ar.edu.unlam.actas.model.linearblockchain;

import ar.edu.unlam.actas.utils.HashUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class Block<T> {
    private String previousHash;
    private long timeStamp;
    private T data;
    private String hash;


    @Builder
    public Block(final String previousHash, final T data) {
        this.previousHash = previousHash;
        this.data = data;
        Date today = new Date();
        this.timeStamp = today.getTime();
        this.hash = recalculateHash();
    }

    public String recalculateHash() {
        return HashUtils.hash256(previousHash + (data != null ? data.toString() : "") + timeStamp);
    }
}
