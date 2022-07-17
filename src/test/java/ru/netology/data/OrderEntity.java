package ru.netology.data;

public class OrderEntity {
    private String id;
    private String created;
    private String creditID;
    private String paymentID;

    public OrderEntity(String id, String created, String creditID, String paymentID){
        this.id = id;
        this.created = created;
        this.creditID = creditID;
        this.paymentID = paymentID;
    }

    public String getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getCreditID() {
        return creditID;
    }

    public String getPaymentID() {
        return paymentID;
    }
}
