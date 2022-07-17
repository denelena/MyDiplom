package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.ApplicationPage;

import static com.codeborne.selenide.Selenide.open;

//This class contains common tests that do not involve connecting to the database
@DisplayName("AppCommonTests: This class contains common tests that do not involve connecting to the database")
public class AppCommonTests {

    @BeforeEach
    public void setUpTests() {
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Should click on 'Buy' button; expecting to get the card entry form visible")
    public void shouldDisplayCardEntryFormOnBtnBuy() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.showCardFormWithBuyBtn();
    }

    @Test
    @DisplayName("Should click on 'Credit' button; expecting to get the card entry form visible")
    public void shouldDisplayCardEntryFormOnBtnCredit() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.showCardFormWithCreditBtn();
    }

    @Test
    @DisplayName("Should leave the 'Card Number' field empty; expecting to get validation error - 'wrong format'")
    public void shouldLeaveEmptyCardNumberField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.raiseValidationErrorEmptyCardNumber();
    }

    @Test
    @DisplayName("Should leave the 'Card Month' field empty; expecting to get validation error - 'wrong format'")
    public void shouldLeaveEmptyCardMonthField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.raiseValidationErrorEmptyCardMonth();
    }

    @Test
    @DisplayName("Should leave the 'Card Year' field empty; expecting to get validation error - 'wrong format'")
    public void shouldLeaveEmptyCardYearField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.raiseValidationErrorEmptyCardYear();
    }

    @Test
    @DisplayName("Should leave the 'Card Owner' field empty; expecting to get validation error - 'field must be filled'")
    public void shouldLeaveEmptyCardOwnerField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.raiseValidationErrorEmptyCardOwner();
    }

    @Test
    @DisplayName("Should leave the 'Card CVV' field empty; expecting to get validation error - 'wrong format'")
    public void shouldLeaveEmptyCardCvvField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.raiseValidationErrorEmptyCardCvv();
    }

    @Test
    @DisplayName("Should enter '13' for the 'Card Month' field; expecting to get validation error - 'invalid card expiration date' for the 'Card Month' field")
    public void shouldEnterWrongValueCardMonthField() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterWrongValueCardMonth();
    }

    @Test
    @DisplayName("Should enter expired date for the 'Card Month' and 'Year' fields; expecting to get validation error - 'card expired' for the 'Year' field")
    public void shouldEnterExpiredDate() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterExpiredDate();
    }

    @Test
    @DisplayName("Should enter > 10 years expiration date for the 'Card Month' and 'Year' fields; expecting to get validation error - 'invalid card expiration date' for the 'Year' field")
    public void shouldEnterFarFutureExpirationDate() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterFarFutureExpirationDate();
    }

    @Test
    @DisplayName("Should enter valid card data and make successful buy order; expecting to see 'Success' popup, not expecting to see 'Error' popup.")
    public void shouldEnterValidCardBuySuccess() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterValidCardBuySuccess();
    }

    @Test
    @DisplayName("Should enter valid card data and make successful credit order; expecting to see 'Success' popup, not expecting to see 'Error' popup.")
    public void shouldEnterValidCardCreditSuccess() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterValidCardCreditSuccess();
    }

    @Test
    @DisplayName("Should enter invalid card data and make failed buy order; expecting to see 'Error' popup, not expecting to see 'Success' popup.")
    public void shouldEnterInvalidCardBuyFail() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterInvalidCardBuyFail();
    }

    @Test
    @DisplayName("Should enter invalid card data and make failed credit order; expecting to see 'Error' popup, not expecting to see 'Success' popup.")
    public void shouldEnterInvalidCardCreditFail() {
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterInvalidCardCreditFail();
    }
}
