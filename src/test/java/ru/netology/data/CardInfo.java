package ru.netology.data;

public class CardInfo {

    private String cardNumber;
    private String cardMonth;
    private String cardYear;
    private String cardOwner;
    private String cardCvv;

    public CardInfo(String cardNumber, String cardMonth, String cardYear, String cardOwner, String cardCvv){
        this.cardCvv = cardCvv;
        this.cardMonth = cardMonth;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.cardYear = cardYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardMonth() {
        return cardMonth;
    }

    public String getCardYear() {
        return cardYear;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardMonth(String cardMonth) {
        this.cardMonth = cardMonth;
    }

    public void setCardYear(String cardYear) {
        this.cardYear = cardYear;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }
}
