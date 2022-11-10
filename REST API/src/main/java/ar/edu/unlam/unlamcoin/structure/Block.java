package ar.edu.unlam.unlamcoin.structure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class Block<T> {
    private String hash;
    private String prevHash;
    private long timeStamp;

    private T data;

    @Builder
    public Block(final String prevHash, final T data) {
        this.prevHash = prevHash;
        this.data = data;
        Date today = new Date();
        this.timeStamp = today.getTime();
        this.hash = recalculateHash();
    }

    public void setData(final T data) {
        this.data = data;
        this.hash = recalculateHash();
    }

    public String recalculateHash() {
        return Hasher.hashSHA256Hex(getPrevHash() + (getData() != null ? getData().toString() : "") + getTimeStamp());
    }
}
