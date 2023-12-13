package com.example.inventory_m_s;

import com.example.inventory_m_s.entities.Goods;
import com.example.inventory_m_s.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbFunctions {
    public Connection connect_to_db(String dbname,String user,String pass){
        Connection conn=null;
        try{
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
            if(conn!=null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }
    public void createTableUser(Connection conn, String table_name) {
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, table_name, null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE " + table_name + " ("
                        + "id SERIAL PRIMARY KEY, "
                        + "surname VARCHAR(100), "
                        + "lastname VARCHAR(100), "
                        + "email VARCHAR(255), "
                        + "password VARCHAR(255), "
                        + "phone VARCHAR(20), "
                        + "address VARCHAR(200)"
                        + ");";
                statement = conn.createStatement();
                statement.executeUpdate(query);
                System.out.println("Table Created");
            } else {
                System.out.println("Table already exists");
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createTableType(Connection conn, String table_name) {
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, table_name, null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE " + table_name + " ("
                        + "id SERIAL PRIMARY KEY, "
                        + "type VARCHAR(200)"
                        + ");";
                statement = conn.createStatement();
                statement.executeUpdate(query);
                System.out.println("Table Created");
            } else {
                System.out.println("Table already exists");
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createTableGoods(Connection conn, String table_name) {
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, table_name, null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE " + table_name + " ("
                        + "id SERIAL PRIMARY KEY, "
                        + "type VARCHAR(255), "
                        + "size INT, "
                        + "prize INT, "
                        + "name VARCHAR(255), "
                        + "description VARCHAR(400), "
                        + "date VARCHAR(255)"
                        + ");";

                statement = conn.createStatement();
                statement.executeUpdate(query);
                System.out.println("Table Created");
            } else {
                System.out.println("Table already exists");
            }

            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insert_users_register(Connection conn,
                                 String email, String password){
        Statement statement;
        try {
            String query=String.format("insert into users (email, password)" +
                    " values('%s','%s');",email, password);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insert_users_row(Connection conn,String surname, String lastname,
                                 String email, String password, String phone, String address){
        Statement statement;
        try {
            String query=String.format("insert into users (surname, lastname, email, password, phone, address)" +
                    " values('%s','%s','%s','%s','%s','%s');",surname,lastname, email, password, phone, address);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public List<Goods> selectGoods(Connection conn) {
        Statement statement;
        List<Goods> goodsList = new ArrayList<>();

        try {
            String query = "select * from goods;";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Goods goods = new Goods();
                goods.setId(resultSet.getLong("id"));
                goods.setType(resultSet.getString("type"));
                goods.setName(resultSet.getString("name"));
                goods.setDescription(resultSet.getString("description"));
                goods.setDate(resultSet.getString("date"));
                goods.setPrize(resultSet.getInt("prize"));

                goodsList.add(goods);
            }

            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return goodsList;
    }
    public void goods_insert(Connection conn, Goods goods){
        Statement statement;
        try {
            String query=String.format("insert into goods (type, name, description, date, prize)" +
                    " values('%s','%s','%s','%s','%s');",goods.getType(),goods.getName(), goods.getDescription(), goods.getDate(), goods.getPrize());
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insert_into_types(Connection conn, String type){
        Statement statement;
        try {
            String query=String.format("insert into types (type)" +
                    " values('%s');",type);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insert_row(Connection conn,String table_name,String name, String address){
        Statement statement;
        try {
            String query=String.format("insert into %s(name,address) values('%s','%s');",table_name,name,address);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void read_data(Connection conn, String table_name){
        Statement statement;
        ResultSet rs=null;
        try {
            String query=String.format("select * from %s",table_name);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                System.out.print(rs.getString("empid")+" ");
                System.out.print(rs.getString("name")+" ");
                System.out.println(rs.getString("Address")+" ");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public int read_data_type(Connection conn, String table_name){
        Statement statement;
        ResultSet rs=null;
        int i = 0;
        try {
            String query=String.format("select * from %s",table_name);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                i++;
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return i;
    }
    public List<String> read_data_types(Connection conn, String table_name){
        Statement statement;
        List<String> types = new ArrayList<>();
        ResultSet rs=null;
        int i = 0;
        try {
            String query=String.format("select * from %s",table_name);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while(rs.next()){
                types.add(rs.getString("type"));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return types;

    }

    public void update_name(Connection conn,String table_name, String old_name,String new_name){
        Statement statement;
        try {
            String query=String.format("update %s set name='%s' where name='%s'",table_name,new_name,old_name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Updated");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public User users_search_by_email_and_password(Connection conn, String table_name, String email) throws SQLException {
        Statement statement;
        ResultSet rs=null;
        User user = new User();
        try {
            String query=String.format("select * from %s where email = '%s'",table_name,email);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSurname(rs.getString("surname"));
                user.setLastname(rs.getString("lastname"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phone"));

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }
    public void user_search_by_email(Connection conn,String email){
        Statement statement;
        ResultSet rs=null;
        try {
            String query=String.format("select * from users where email= %s",email);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                System.out.print(rs.getString("empid")+" ");
                System.out.print(rs.getString("name")+" ");
                System.out.println(rs.getString("address"));

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void search_by_id(Connection conn, String table_name,int id){
        Statement statement;
        ResultSet rs=null;
        try {
            String query=String.format("select * from %s where empid= %s",table_name,id);
            statement=conn.createStatement();
            rs=statement.executeQuery(query);
            while (rs.next()){
                System.out.print(rs.getString("empid")+" ");
                System.out.print(rs.getString("name")+" ");
                System.out.println(rs.getString("address"));

            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void delete_row_by_name(Connection conn,String table_name, String name){
        Statement statement;
        try{
            String query=String.format("delete from %s where name='%s'",table_name,name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void delete_type_by_type_name(Connection conn,String table_name, String name){
        Statement statement;
        try{
            String query=String.format("delete from %s where type='%s'",table_name,name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void delete_row_by_id(Connection conn,String table_name, int id){
        Statement statement;
        try{
            String query=String.format("delete from %s where id= %s",table_name,id);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void updateGoods(Connection conn, Goods updatedGoods) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE goods SET type=?, name=?, description=?, date=?, prize=? WHERE id=?";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, updatedGoods.getType());
            preparedStatement.setString(2, updatedGoods.getName());
            preparedStatement.setString(3, updatedGoods.getDescription());
            preparedStatement.setString(4, updatedGoods.getDate());
            preparedStatement.setInt(5, updatedGoods.getPrize());
            preparedStatement.setLong(6, updatedGoods.getId());

            preparedStatement.executeUpdate();

            System.out.println("Row Updated");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }


    public void delete_table(Connection conn, String table_name){
        Statement statement;
        try {
            String query= String.format("drop table %s",table_name);
            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}