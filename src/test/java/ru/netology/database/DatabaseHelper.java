package ru.netology.database;

import ru.netology.data.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static String dbUrlMysql = "jdbc:mysql://localhost:3306/sample-app";
    private static String dbUrlPostgreSql = "jdbc:postgresql://localhost:5432/sample-app";

    private static String dbUser = "Karl";
    private static String dbPwd = "KlaraUkralaKlarnet";


    public static void ClearTables(DbConnectionKind cnnKind) {
        try {
            Connection cnn = getDbConnection(cnnKind);
            Statement dbCommand = cnn.createStatement();
            dbCommand.execute("delete from payment_entity;");
            dbCommand.execute("delete from credit_request_entity;");
            dbCommand.execute("delete from order_entity;");
        } catch (Exception e) {
        }
    }

    public static ArrayList<OrderEntity> getAllOrders(DbConnectionKind cnnKind) {
        ArrayList<OrderEntity> entities = new ArrayList<OrderEntity>();
        try {
            Connection cnn = getDbConnection(cnnKind);
            PreparedStatement dbCommand = cnn.prepareStatement("select * from order_entity;");

            ResultSet rs = dbCommand.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String created = rs.getDate("created").toString();
                String creditID = rs.getString("credit_id");
                String paymentID = rs.getString("payment_id");
                entities.add(new OrderEntity(id, created, creditID, paymentID));
            }
        } catch (Exception e) {
        }
        return entities;
    }

    public static ArrayList<PaymentEntity> getAllPayments(DbConnectionKind cnnKind) {
        ArrayList<PaymentEntity> entities = new ArrayList<PaymentEntity>();
        try {
            Connection cnn = getDbConnection(cnnKind);
            PreparedStatement dbCommand = cnn.prepareStatement("select * from payment_entity;");

            ResultSet rs = dbCommand.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String amount = Integer.toString(rs.getInt("amount"));
                String created = rs.getDate("created").toString();
                String status = rs.getString("status");
                String transactionID = rs.getString("transaction_id");
                entities.add(new PaymentEntity(id, amount, created, status, transactionID));
            }
        } catch (Exception e) {
        }
        return entities;
    }

    public static ArrayList<CreditRequestEntity> getAllCreditRequests(DbConnectionKind cnnKind) {
        ArrayList<CreditRequestEntity> entities = new ArrayList<CreditRequestEntity>();
        try {
            Connection cnn = getDbConnection(cnnKind);
            PreparedStatement dbCommand = cnn.prepareStatement("select * from credit_request_entity;");

            ResultSet rs = dbCommand.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String bankID = rs.getString("bank_id");
                String created = rs.getDate("created").toString();
                String status = rs.getString("status");
                entities.add(new CreditRequestEntity(id, bankID, created, status));
            }
        } catch (Exception e) {
        }
        return entities;
    }


    private static Connection getDbConnection(DbConnectionKind cnnKind) throws SQLException {
        String dbUrl = (cnnKind == DbConnectionKind.MySql) ? dbUrlMysql : dbUrlPostgreSql;
        Connection cnn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        return cnn;
    }
}
