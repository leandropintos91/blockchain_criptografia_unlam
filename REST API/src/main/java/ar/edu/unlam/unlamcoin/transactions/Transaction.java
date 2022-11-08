package ar.edu.unlam.unlamcoin.transactions;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Transaction {
    private String sender;
    private String receiver;
    private Double amount;
}
