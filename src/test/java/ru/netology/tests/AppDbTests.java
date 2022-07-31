package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.*;
import ru.netology.pages.ApplicationPage;
import ru.netology.database.*;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//This class contains tests that verify correctness of SUT writing to the database
@DisplayName("AppDbTests: This class contains tests that verify correctness of SUT writing to the database")
public class AppDbTests {

    private String _username = "";
    private String _password = "";
    private String _datasource = "";

    public AppDbTests(){
        _username = System.getProperty("username", "");
        _password = System.getProperty("password", "");
        _datasource = System.getProperty("datasource", "");
    }

    @BeforeEach
    public void setUpTests(){
        DatabaseHelper.ClearTables(_datasource, _username, _password);
        open("http://localhost:8080");
    }

    @Test
    @DisplayName("Should make successful buy order; expecting 1 new row in 'payment_entity' with status='APPROVED' and 1 new row in 'order_entity' linked by a foreign key.")
    public void shouldAnalyzeDatabaseAfterValidCardBuyOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(true, true);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(_datasource, _username, _password);
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<PaymentEntity> payments = DatabaseHelper.getAllPayments(_datasource, _username, _password);
        assertEquals(1, payments.size(), "New record should be added to payment_entity table");
        assertTrue(payments.get(0).getStatus().equals("APPROVED"), "New record in payment_entity table should have status='APPROVED'");

        assertTrue(payments.get(0).getTransactionID().equals(orders.get(0).getPaymentID()),
                "New record in 'order_entity' should refer to the new record in 'payment_entity' by foreign key (transaction_id -> payment_id).");

        assertEquals("4500000", payments.get(0).getAmount(), "New payment record should have amount = 45 000 rub");
    }

    @Test
    @DisplayName("Should make failed buy order; expecting 1 new row in 'payment_entity' with status='DECLINED' and 1 new row in 'order_entity' linked by a foreign key.")
    public void shouldAnalyzeDatabaseAfterInvalidalidCardBuyOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(false, true);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(_datasource, _username, _password);
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<PaymentEntity> payments = DatabaseHelper.getAllPayments(_datasource, _username, _password);
        assertEquals(1, payments.size(), "New record should be added to payment_entity table");
        assertTrue(payments.get(0).getStatus().equals("DECLINED"), "New record in payment_entity table should have status='DECLINED'");

        assertTrue(payments.get(0).getTransactionID().equals(orders.get(0).getPaymentID()),
                "New record in 'order_entity' should refer to the new record in 'payment_entity' by foreign key (transaction_id -> payment_id).");

        assertEquals("4500000", payments.get(0).getAmount(), "New payment record should have amount = 45 000 rub");
    }
    @Test
    @DisplayName("Should make successful credit order; expecting 1 new row in 'credit_request_entity' with status='APPROVED' and 1 new row in 'order_entity' linked by a foreign key.")
    public void shouldAnalyzeDatabaseAfterValidCardCreditOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(true, false);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(_datasource, _username, _password);
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<CreditRequestEntity> credits = DatabaseHelper.getAllCreditRequests(_datasource, _username, _password);
        assertEquals(1, credits.size(), "New record should be added to credit_request_entity table");
        assertTrue(credits.get(0).getStatus().equals("APPROVED"), "New record in credit_request_entity table should have status='APPROVED'");

        assertTrue(credits.get(0).getBankID().equals(orders.get(0).getCreditID()),
                "New record in 'order_entity' should refer to the new record in 'credit_request_entity' by foreign key (bank_id -> credit_id).");
    }
    @Test
    @DisplayName("Should make failed credit order; expecting 1 new row in 'credit_request_entity' with status='DECLINED' and 1 new row in 'order_entity' linked by a foreign key.")
    public void shouldAnalyzeDatabaseAfterInvalidCardCreditOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(false, false);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(_datasource, _username, _password);
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<CreditRequestEntity> credits = DatabaseHelper.getAllCreditRequests(_datasource, _username, _password);
        assertEquals(1, credits.size(), "New record should be added to credit_request_entity table");
        assertTrue(credits.get(0).getStatus().equals("DECLINED"), "New record in credit_request_entity table should have status='DECLINED'");

        assertTrue(credits.get(0).getBankID().equals(orders.get(0).getCreditID()),
                "New record in 'order_entity' should refer to the new record in 'credit_request_entity' by foreign key (bank_id -> credit_id).");
    }

}