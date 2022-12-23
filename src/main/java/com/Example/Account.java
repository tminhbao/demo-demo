package com.Example;

import com.Annotation.*;


@Table(name = "accounts")
public class Account {

    public Account(){
    }

    public Account(int accountId, String personID){
        this.AccountID = accountId;
        this.PersonID = personID;
    }
    //primary key
    @PrimaryKey(name = "AccountID")
    @ColumnInfo(name = "AccountID", type = DataType.INT)
    public int AccountID;// int
    @ColumnInfo(name = "PersonID", type = DataType.NCHAR)
    @ForeignKey(relationshipId = "1", name = "PersonID", references = "PersonID")
    public String PersonID;// int,
    @OneToOne(relationshipId = "1", tableName = "users")
    @ForeignKey(relationshipId = "1", name = "PersonID", references = "PersonID")
    public User Person;
}