package com.idknoo.DBWork;

import com.idknoo.Values.Values;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBWorkerBean implements DBWorking {

    private Connector connector = new Connector();
    private Connection connection;

    public DBWorkerBean() {
        try {
            connection = connector.connect();
        } catch (SQLException throwables) {
            connection = null;
        }
    }

    @Override
    public void addValues(Values values) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO HITS (x,y,r,time) VALUES (");
        stringBuilder.append(values.getX());
        stringBuilder.append(",");
        stringBuilder.append(values.getY());
        stringBuilder.append(",");
        stringBuilder.append(values.getR());
        stringBuilder.append(",");
        stringBuilder.append(values.getCreateDate().getTime());
        stringBuilder.append(")");

        Statement statement = connection.createStatement();
        statement.executeUpdate(stringBuilder.toString());
        statement.close();
        connection.commit();
    }

    @Override
    public List<Values> getLastValues() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM HITS");
        List<Values> valuesList = new ArrayList<>();
        while (resultSet.next()) {
            valuesList.add(new Values(resultSet.getDouble("X"), resultSet.getDouble("Y"), resultSet.getDouble("R"), new Date(resultSet.getLong("TIME"))));
        }
        return valuesList;
    }

    @Override
    public boolean isConnectionValid() {
        try {
            if (connection != null && connection.isValid(0)) {
                return true;
            } else {
                if (connection != null) {
                    connection.close();
                }
                connection = connector.connect();
                return connection.isValid(0);
            }
        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public void clearLastRequests() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE HITS");
        statement.close();
        connection.commit();
    }
}
