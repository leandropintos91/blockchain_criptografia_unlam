package com.grupo2.blockchain.structure;

import java.util.List;

public class MerkleBlock<T extends Hasheable> implements Hasheable {

    private String prevHash;
    private MerkleTree<T> merkleTree;

    public MerkleBlock() {}

    public MerkleBlock(String prevHash, List<T> data){
        this.prevHash = prevHash;
        this.merkleTree = new MerkleTree<T>(data);
    }

    @Override
    public String obtainHash() {
        return merkleTree.obtainHash();
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
