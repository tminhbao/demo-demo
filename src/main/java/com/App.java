package com;

import java.beans.IntrospectionException;
import java.sql.*;

import com.Connector.ConnectorFactory.MySqlConnectorFactory;
import com.Example.*;
import com.Ulti.*;
import com.Connector.*;
import com.QueryBuilder.Implement.SelectQuery;
import com.Repository.AccountRepository;
import com.Repository.CreditCardRepository;
import com.Repository.Repository;
import com.Repository.UserRepository;


public class App {
    static public void main(String[] args) throws SQLException, IntrospectionException, IllegalAccessException, NoSuchFieldException {

//            // Get data
//            Connection connection = null;
//
//            String DB_URL = "jdbc:mysql://localhost:3306/simpledb";
//            String USER_NAME = "root";
//            String PASSWORD = "MySQL3082001";
//            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
//            System.out.println("Connection successfully MYSQL");
//
//            String query = "SELECT * FROM users";
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet resultSet = null;
//            resultSet = statement.executeQuery();
//            resultSet.next();
//            // Mapping
//            Connector connector = new MySqlConnector("jdbc:mysql://localhost:3306/simpledb", "root", "MySQL3082001");
//            SqlMapper sqlMapper = new SqlMapper();
//
//            var user = sqlMapper.MapWithOutRelationship(User.class, connector, resultSet);
//            System.out.println(user);
//
//            String tableName = sqlMapper.GetTableName(User.class);
//            System.out.println("table name of class user= " + tableName);
//
//            ArrayList<PrimaryKey> primaryKeys = sqlMapper.GetPrimaryKeys(User.class);
//            for (PrimaryKey primaryKey : primaryKeys) {
//                System.out.println("primary key of class user= " + primaryKey.name());
//            }
//
//            ArrayList<ForeignKey> foreignKeys = sqlMapper.GetForeignKeys(User.class, "2");
//            for (ForeignKey foreignKey : foreignKeys) {
//                System.out.println("foreign key of User.class= " + foreignKey.name());
//            }
//
//            ArrayList<ColumnInfo> columnInfos = sqlMapper.GetColumns(User.class);
//            for (ColumnInfo columnInfo : columnInfos) {
//                System.out.println("column info of class user= " + columnInfo.name());
//            }
//
//            HashMap<ColumnInfo, Object> columnInfoObjectHashMap = sqlMapper.GetColumnValues(user);
//            for (ColumnInfo columnInfo : columnInfoObjectHashMap.keySet()) {
//                System.out.println("column info of class user= " + columnInfo.name() + " value = " + columnInfoObjectHashMap.get(columnInfo));
//            }
//
//            ColumnInfo testFindOneCollum = sqlMapper.FindColumn("PersonID", columnInfoObjectHashMap);
//            System.out.println("test find one collum = " + testFindOneCollum.name());
//
//            ColumnInfo testFindOneCollum2 = sqlMapper.FindColumn("PersonID", columnInfos);
//            System.out.println("test find one collum = " + testFindOneCollum2.name());
//
//            var account = new Account();
//            var annotations = account.getClass().getField("Person").getAnnotations();
//            var getAnnoAllTest = sqlMapper.GetAll(annotations, OneToOne.class);
//            for (OneToOne oneToOne : getAnnoAllTest) {
//                System.out.println("test get all anno,relatinship id = " + oneToOne.relationshipId() + "tableName= " + oneToOne.tableName());
//            }
//
//            User userwithrelation = sqlMapper.MapWithRelationship(User.class, connector, resultSet);
//            System.out.println("test map user with relation onetomany cresitcards: ");
//            for (CreditCard card : userwithrelation.creditCards) {
//                System.out.println("user: " + userwithrelation.PersonID + " has card " + card.CreditCardID);
//            }
//            System.out.println("test map user with relation onetoone account: ");
//            System.out.println("user: " + userwithrelation.PersonID + " has account " + userwithrelation.account.AccountID);

            // Instruction for using query builder - Vinh

            SqlUlti sqlMapper = new SqlUlti();
            MySqlConnectorFactory.provideConnectionInfo("jdbc:mysql://localhost:3306/simpledb", "root", "admin");

            // [SELECT] GET ALL TABLES IN DATABASE
            Repository repoA = new UserRepository();
            Object users = repoA.SelectAll();

            Repository repoB = new AccountRepository();
            Object accounts = repoB.SelectAll();

            Repository repoC = new CreditCardRepository();
            Object creditCards = repoC.SelectAll();

            System.out.println(users);

            //[SELECT] get rows with where statement
            System.out.println(repoA.SelectWhere("personID = '1'"));

            //[SELECT] get rows with where statement and group by statement
            System.out.println(repoA.SelectWhereAndGroupBy("personId = '1'","personId"));

            //[SELECT] get rows with group by and having statement
            // Find the latest user
            System.out.println(repoA.SelectGroupByAndHaving("PersonID","PersonID >= all ( select u2.PersonID from users u2)"));

            //[INSERT] Primary key auto increment
            // Add new user
            User insertedUser = new User("6","John","Stones");
            repoA.Insert(insertedUser);

            //[INSERT] Primary key is not auto increment
            Account insertedAccount = new Account(5,"5");
            //repoB.Insert(insertedAccount);

            //[UPDATE]
            User updatedUser= new User("5","John", "Cena",insertedAccount);
            repoA.Update(updatedUser);

            //[DELETE] Account
            repoB.Delete(insertedAccount);

            //[DELETE] User
            User deleteUser = new User("6","John", "Stones");
            repoA.Delete(deleteUser);

    }
}
