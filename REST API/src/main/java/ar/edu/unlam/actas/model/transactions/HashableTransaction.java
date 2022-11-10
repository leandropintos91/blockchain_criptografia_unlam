package ar.edu.unlam.actas.model.transactions;

import ar.edu.unlam.actas.model.Hashable;
import ar.edu.unlam.actas.utils.HashUtils;
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
        return HashUtils.hash256(sender + receiver + amount + timestamp);
    }

    public void recalculateTimeStampAndHash() {
        if (this.timestamp == 0) {
            Date today = new Date();
            this.timestamp = today.getTime();
        }
        this.hash = recalculateHash();
    }
}
