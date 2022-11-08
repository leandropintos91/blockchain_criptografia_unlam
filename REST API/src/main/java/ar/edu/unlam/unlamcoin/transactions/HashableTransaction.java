package ar.edu.unlam.unlamcoin.transactions;

import ar.edu.unlam.unlamcoin.structure.Hashable;
import ar.edu.unlam.unlamcoin.structure.Hasher;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Getter
public class HashableTransaction implements Hashable {
    private String sender;
    private String receiver;
    private Double amount;
    private long timestamp;
    private String hash;

    @Builder
    public HashableTransaction(final String sender, final String receiver, final Double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = new Date().getTime();
        this.hash = recalculateHash();
    }

    @Override
    public String recalculateHash() {
        return Hasher.hashSHA256Hex(sender + receiver + amount + timestamp);
    }

    public void recalculateTimeStampAndHash() {
        if (this.timestamp == 0) {
            Date today = new Date();
            this.timestamp = today.getTime();
        }
        this.hash = recalculateHash();
    }
}
