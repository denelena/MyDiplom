package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.*;
import ru.netology.pages.ApplicationPage;
import ru.netology.database.*;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;


public abstract class AppDbTestsBase {
    //Should be implemented in derived classes. Returns MySql or PostgreSql
    abstract DbConnectionKind getConnectionKind();

    @BeforeEach
    public void setUpTests(){
        DatabaseHelper.ClearTables(getConnectionKind());
        open("http://localhost:8080");
    }

    protected void analyzeDatabaseAfterValidCardBuyOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(true, true);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(getConnectionKind());
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<PaymentEntity> payments = DatabaseHelper.getAllPayments(getConnectionKind());
        assertEquals(1, payments.size(), "New record should be added to payment_entity table");
        assertTrue(payments.get(0).getStatus().equals("APPROVED"), "New record in payment_entity table should have status='APPROVED'");

        assertTrue(payments.get(0).getTransactionID().equals(orders.get(0).getPaymentID()),
                "New record in 'order_entity' should refer to the new record in 'payment_entity' by foreign key (transaction_id -> payment_id).");

        assertEquals("4500000", payments.get(0).getAmount(), "New payment record should have amount = 45 000 rub");
    }

    protected void analyzeDatabaseAfterInvalidalidCardBuyOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(false, true);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(getConnectionKind());
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<PaymentEntity> payments = DatabaseHelper.getAllPayments(getConnectionKind());
        assertEquals(1, payments.size(), "New record should be added to payment_entity table");
        assertTrue(payments.get(0).getStatus().equals("DECLINED"), "New record in payment_entity table should have status='DECLINED'");

        assertTrue(payments.get(0).getTransactionID().equals(orders.get(0).getPaymentID()),
                "New record in 'order_entity' should refer to the new record in 'payment_entity' by foreign key (transaction_id -> payment_id).");

        assertEquals("4500000", payments.get(0).getAmount(), "New payment record should have amount = 45 000 rub");
    }

    protected void analyzeDatabaseAfterValidCardCreditOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(true, false);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(getConnectionKind());
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<CreditRequestEntity> credits = DatabaseHelper.getAllCreditRequests(getConnectionKind());
        assertEquals(1, credits.size(), "New record should be added to credit_request_entity table");
        assertTrue(credits.get(0).getStatus().equals("APPROVED"), "New record in credit_request_entity table should have status='APPROVED'");

        assertTrue(credits.get(0).getBankID().equals(orders.get(0).getCreditID()),
                "New record in 'order_entity' should refer to the new record in 'credit_request_entity' by foreign key (bank_id -> credit_id).");
    }

    protected void analyzeDatabaseAfterInvalidCardCreditOrder(){
        ApplicationPage appPage = new ApplicationPage();
        appPage.enterCardAndProceed(false, false);

        sleep(15000);

        ArrayList<OrderEntity> orders = DatabaseHelper.getAllOrders(getConnectionKind());
        assertEquals(1, orders.size(), "New record should be added to order_entity table");

        ArrayList<CreditRequestEntity> credits = DatabaseHelper.getAllCreditRequests(getConnectionKind());
        assertEquals(1, credits.size(), "New record should be added to credit_request_entity table");
        assertTrue(credits.get(0).getStatus().equals("DECLINED"), "New record in credit_request_entity table should have status='DECLINED'");

        assertTrue(credits.get(0).getBankID().equals(orders.get(0).getCreditID()),
                "New record in 'order_entity' should refer to the new record in 'credit_request_entity' by foreign key (bank_id -> credit_id).");
    }

}