package com.example.inventory_m_s;

import com.example.inventory_m_s.entities.Goods;
import com.example.inventory_m_s.entities.User;
import com.example.inventory_m_s.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                        + "role VARCHAR(20), "
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
                        + "status VARCHAR(255), "
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

    public Long insert_users_register(Connection conn, User user) {
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            String query = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
            preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Inserting user failed, no rows affected.");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("Inserting user failed, no ID obtained.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                // обработка исключений в случае ошибок закрытия ресурсов
            }
        }
    }


    public List<Goods> selectGoods(Connection conn) {
        Statement statement;
        List<Goods> goodsList = new ArrayList<>();

        try {
            String query = "select * from goods where status = 'active';";
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Goods goods = new Goods();
                goods.setId(resultSet.getLong("id"));
                goods.setType(resultSet.getString("type"));
                goods.setSize(Integer.parseInt(resultSet.getString("size")));
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
    public List<Goods> selectGoodsWithIds(Connection conn, List<Integer> goodsIds) {
        Statement statement;
        List<Goods> goodsList = new ArrayList<>();

        try {
            // Convert the list of goods IDs to a comma-separated string
            String idsString = goodsIds.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            System.out.println("ther idsString: "+idsString);

            // Build the SQL query with a WHERE clause to filter by goods IDs
            String query = "SELECT * FROM goods WHERE id IN (" + idsString + ") ";
            System.out.println("Query: " + query);


            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("its while working");
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

    public List<Integer> selectUsersGoods(Connection conn, Long userId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> goodsList = new ArrayList<>();

        try {
            String query = "SELECT * FROM usersgoods WHERE userId = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setLong(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                goodsList.add(resultSet.getInt("goodsId"));
            }
        } catch (SQLException e) {
            // Обработка исключений
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // Обработка исключений в случае ошибок закрытия ресурсов
                e.printStackTrace();
            }
        }

        return goodsList;
    }
    public void goods_insert(Connection conn, Goods goods){
        Statement statement;
        try {
            String query=String.format("insert into goods (type, name, description, date, prize, status, size)" +
                    " values('%s','%s','%s','%s','%s', '%s', '%s');",goods.getType(),goods.getName(), goods.getDescription(), goods.getDate(), goods.getPrize(), goods.getStatus(), goods.getSize());
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
                user.setRole(Role.valueOf(rs.getString("role")));

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public List<Goods> searchGoods(Connection conn, String table_name, String searchString) {
        Statement statement;
        ResultSet rs = null;
        List<Goods> result = new ArrayList<>();
        System.out.println("the table name: "+table_name);

        try {
            // Build the SQL query based on the search criteria
            String query = String.format("SELECT * FROM %s WHERE " +
                            "id::text LIKE '%%%s%%' OR " +
                            "description LIKE '%%%s%%' OR " +
                            "type LIKE '%%%s%%' OR " +
                            "size::text LIKE '%%%s%%' OR " +
                            "name LIKE '%%%s%%' OR " +
                            "date LIKE '%%%s%%' OR " +
                            "prize::text LIKE '%%%s%%'",
                    table_name,
                    searchString, searchString, searchString,
                    searchString, searchString, searchString, searchString);

            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            int i = 0;
            System.out.println("start while: ");

            // Iterate over the result set and populate the list
            while (rs.next()) {
                i++;
                System.out.println("the i: "+i);
                Goods foundGoods = new Goods();
                foundGoods.setId(rs.getLong("id"));
                foundGoods.setDescription(rs.getString("description"));
                foundGoods.setType(rs.getString("type"));
                foundGoods.setSize(rs.getInt("size"));
                foundGoods.setName(rs.getString("name"));
                foundGoods.setDate(rs.getString("date"));
                foundGoods.setPrize(rs.getInt("prize"));

                result.add(foundGoods);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
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

    public void setGoodsForUser(Connection conn, Long userId, Goods goods) {
        System.out.println("worked0");

        createTableUsersGoods(conn, "usersgoods");
        System.out.println("worked1");
        System.out.println("the goods id:"+goods.getId());
        System.out.println("the user id:"+userId);

        Statement statement;
        try {
            String query = String.format("insert into usersgoods (userId, goodsId)" +
                    " values('%s','%s');", userId, goods.getId());

            statement=conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("worked2");


    }
    public void createTableUsersGoods(Connection conn, String table_name) {
        System.out.println("the table_name: "+ table_name);
        Statement statement;
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, table_name, null);

            if (!resultSet.next()) {
                String query = "CREATE TABLE "+ table_name +" (id SERIAL PRIMARY KEY, userId integer,goodsId integer);";


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

    public void setGoodStatus(Connection conn, String status, Long goodId) {
        Statement statement;
        try {
            String query = String.format("UPDATE goods SET status = '%s' WHERE id = %d;", status, goodId);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Status Updated");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}