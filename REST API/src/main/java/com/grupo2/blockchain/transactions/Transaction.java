package com.grupo2.blockchain.transactions;

public class Transaction {
    private String transmitter;
    private String receiver;
    private Double mount;

    public Transaction(){}

    public Transaction(String transmitter, String receiver, Double mount) {
        super();
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.mount = mount;
    }

    public String getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(String transmitter) {
        this.transmitter = transmitter;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }
}
