package com.grupo2.blockchain.structure;

import java.util.Date;

public class Block<T> {
    private String hash;
    private String prevHash;
    private long timeStamp;

    private T data;

    public Block(){
        Date today = new Date();
        this.timeStamp = today.getTime();
    }

    public Block(final String prevHash, final T data){
        this.prevHash = prevHash;
        this.data = data;
        Date today = new Date();
        this.timeStamp = today.getTime();
        this.hash = recalculateHash();

    }

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(final String prevHash) {
        this.prevHash = prevHash;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
        this.hash = Hasher.hashSHA256Hex(getPrevHash() + data.toString() + getTimeStamp());
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String recalculateHash() {
        return Hasher.hashSHA256Hex(getPrevHash() + getData().toString() + getTimeStamp());
    }
}
