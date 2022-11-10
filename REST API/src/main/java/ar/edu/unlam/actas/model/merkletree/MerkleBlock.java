package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.Hashable;

import java.util.List;

public class MerkleBlock<T extends Hashable> implements Hashable {

    private String prevHash;
    private MerkleTree<T> merkleTree;

    public MerkleBlock() {
    }

    public MerkleBlock(String prevHash, List<T> data) {
        this.prevHash = prevHash;
        this.merkleTree = new MerkleTree<T>(data);
    }

    @Override
    public String getHash() {
        return merkleTree.getHash();
    }

    @Override
    public String recalculateHash() {
        return merkleTree.recalculateHash();
    }

    public void setPrevHash(final String prevHash) {
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public MerkleTree<T> getMerkleTree() {
        return merkleTree;
    }

    public void setMerkleTree(MerkleTree<T> merkleTree) {
        this.merkleTree = merkleTree;
    }
}
