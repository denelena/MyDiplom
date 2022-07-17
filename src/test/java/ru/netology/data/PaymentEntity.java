package ru.netology.data;

public class PaymentEntity {
    private String id;
    private String amount;
    private String created;
    private String status;
    private String transactionID;

    public PaymentEntity(String id, String amount, String created, String status, String transactionID) {
        this.id = id;
        this.amount = amount;
        this.created = created;
        this.status = status;
        this.transactionID = transactionID;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionID() {
        return transactionID;
    }


}
