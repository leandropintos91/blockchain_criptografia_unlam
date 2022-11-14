package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleNode implements IMerkleNode {
    private IMerkleNode firstNode;
    private IMerkleNode secondNode;
    private String hash;

    @Builder
    public MerkleNode(final List<Acta> data) {
        List<List<Acta>> dataSplitted = split(data);
        if (dataSplitted.get(0).size() == 1) {
            this.firstNode = MerkleLeaf.builder().data(dataSplitted.get(0).get(0)).build();
        } else {
            this.firstNode = MerkleNode.builder().data(dataSplitted.get(0)).build();
        }

        if (dataSplitted.size() > 1 && dataSplitted.get(1) != null) {
            if (dataSplitted.get(1).size() == 1) {
                this.secondNode = MerkleLeaf.builder().data(dataSplitted.get(1).get(0)).build();
            } else {
                this.secondNode = MerkleNode.builder().data(dataSplitted.get(1)).build();
            }
        } else {
            this.secondNode = null;
        }
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256((firstNode != null ? firstNode.getHash() : "") + (secondNode != null ? secondNode.getHash() : ""));
    }

    @Override
    @JsonIgnore
    public List<Acta> getDataBlock() {
        var list = firstNode.getDataBlock();
        list.addAll(secondNode.getDataBlock());
        return list;
    }

    @Override
    public boolean hasValidHash() {
        if (this.firstNode != null && !this.firstNode.hasValidHash()) {
            return false;
        }

        if (this.secondNode != null && !this.secondNode.hasValidHash()) {
            return false;
        }

        return this.hash.equals(calculateHash());
    }

    private static List<List<Acta>> split(List<Acta> list) {
        int size = list.size();
        if (size == 1) {
            return List.of(list);
        } else {
            int limit = (int) Math.round(((double) size) / 2);
            List<Acta> first
                    = new ArrayList<>(list.subList(0, limit));
            List<Acta> second = new ArrayList<>(
                    list.subList(limit, size));

            return List.of(first, second);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MerkleNode)) {
            return false;
        }

        MerkleNode other = (MerkleNode) obj;
        return this.firstNode.equals(other.firstNode) && ((this.secondNode == null && other.secondNode == null) || (this.secondNode.equals(other.secondNode))) && this.hash.equals(other.hash);
    }
}
