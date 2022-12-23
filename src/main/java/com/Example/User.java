package com.Example;


import com.Annotation.*;

import java.util.ArrayList;

@Table(name = "users")
public  class User {


    public User(){

    }
    public User(String personID, String lastname, String firstname){
        this.PersonID = personID;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public User(String personID, String lastname, String firstname, Account account) {
        this.PersonID = personID;
        this.lastname = lastname;
        this.firstname = firstname;
        this.account = account;
        this.AccountID = account.AccountID;
    }

    @PrimaryKey(name = "PersonID",autoId = false)
    @ColumnInfo(type = DataType.VARCHAR, name = "PersonID")
    public String PersonID;
    @ColumnInfo(type = DataType.VARCHAR, name = "lastname")
    public String lastname;//  varchar(255),
    @ColumnInfo(type = DataType.VARCHAR, name = "firstname")
    public String firstname;// varchar(255),

    @OneToMany(relationshipId = "1", tableName = "creditcards", itemType = CreditCard.class)
    public ArrayList<CreditCard> creditCards;

    @ColumnInfo(name = "AccountId", type = DataType.INT)
    public int AccountID;

    @OneToOne(relationshipId = "2", tableName = "accounts")
    @ForeignKey(relationshipId = "2", name = "AccountID", references = "AccountID")
    public Account account;

    //override toString
    public String toString() {
        return PersonID + " " + lastname + " " + firstname;
    }

}