package ru.netology.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.database.DbConnectionKind;

//This class contains tests that verify correctness of SUT writing to the MySql database
@DisplayName("AppTestsWithMySql: These tests will fail if SUT runs in PostgreSql mode.")
public class AppTestsWithMySql extends AppDbTestsBase{

    @Override
    DbConnectionKind getConnectionKind() {
        return DbConnectionKind.MySql;
    }

    @Test
    @DisplayName("Should make successful buy order; expecting 1 new row in 'payment_entity' with status='APPROVED' and 1 new row in 'order_entity' linked by a foreign key. This test will fail if SUT runs in PostgreSql mode.")
    public void shouldAnalyzeDatabaseAfterValidCardBuyOrder(){
        analyzeDatabaseAfterValidCardBuyOrder();
    }

    @Test
    @DisplayName("Should make failed buy order; expecting 1 new row in 'payment_entity' with status='DECLINED' and 1 new row in 'order_entity' linked by a foreign key. This test will fail if SUT runs in PostgreSql mode.")
    public void shouldAnalyzeDatabaseAfterInvalidCardBuyOrder(){
        analyzeDatabaseAfterInvalidalidCardBuyOrder();
    }

    @Test
    @DisplayName("Should make successful credit order; expecting 1 new row in 'credit_request_entity' with status='APPROVED' and 1 new row in 'order_entity' linked by a foreign key. This test will fail if SUT runs in PostgreSql mode.")
    public void shouldAnalyzeDatabaseAfterValidCardCreditOrder(){
        analyzeDatabaseAfterValidCardCreditOrder();
    }

    @Test
    @DisplayName("Should make failed credit order; expecting 1 new row in 'credit_request_entity' with status='DECLINED' and 1 new row in 'order_entity' linked by a foreign key. This test will fail if SUT runs in PostgreSql mode.")
    public void shouldAnalyzeDatabaseAfterInvalidCardCreditOrder(){
        analyzeDatabaseAfterInvalidCardCreditOrder();
    }
}
