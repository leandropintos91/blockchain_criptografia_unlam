package com.grupo2.blockchain.transactions;

public class TransactionWithCurrency {
    private String transmitter;
    private String receiver;
    private String currency;
    private Double mount;

    public TransactionWithCurrency() { }

    public TransactionWithCurrency(String transmitter, String receiver, String currency, Double mount) {
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.currency = currency;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }
}
