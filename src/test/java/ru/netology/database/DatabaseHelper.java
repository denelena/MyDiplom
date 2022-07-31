package ru.netology.database;

import ru.netology.data.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static String dbUrlMysql = "jdbc:mysql://localhost:3306/sample-app";
    private static String dbUrlPostgreSql = "jdbc:postgresql://localhost:5432/sample-app";

    private static String dbUser = "Karl";
    private static String dbPwd = "KlaraUkralaKlarnet";


    public static void ClearTables(String datasource, String username, String pwd) {
        try {
            Connection cnn = getDbConnection(datasource, username, pwd);
            Statement dbCommand = cnn.createStatement();
            dbCommand.execute("delete from payment_entity;");
            dbCommand.execute("delete from credit_request_entity;");
            dbCommand.execute("delete from order_entity;");
        } catch (Exception e) {
        }
    }

    public static ArrayList<OrderEntity> getAllOrders(String datasource, String username, String pwd) {
        ArrayList<OrderEntity> entities = new ArrayList<OrderEntity>();
        try {
            Connection cnn = getDbConnection(datasource, username, pwd);
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

    public static ArrayList<PaymentEntity> getAllPayments(String datasource, String username, String pwd) {
        ArrayList<PaymentEntity> entities = new ArrayList<PaymentEntity>();
        try {
            Connection cnn = getDbConnection(datasource, username, pwd);
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

    public static ArrayList<CreditRequestEntity> getAllCreditRequests(String datasource, String username, String pwd) {
        ArrayList<CreditRequestEntity> entities = new ArrayList<CreditRequestEntity>();
        try {
            Connection cnn = getDbConnection(datasource, username, pwd);
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


    private static Connection getDbConnection(String datasource, String username, String pwd) throws SQLException {
        Connection cnn = DriverManager.getConnection(datasource, username, pwd);
        return cnn;
    }
}
