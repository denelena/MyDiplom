package ru.netology.data;

public class CreditRequestEntity {
    private String id;
    private String bankID;
    private String created;
    private String status;


    public CreditRequestEntity(String id, String bankID, String created, String status){
        this.id = id;
        this.bankID = bankID;
        this.created = created;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getBankID() {
        return bankID;
    }

    public String getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }
}
