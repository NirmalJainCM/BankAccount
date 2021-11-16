package com.example.projectaccountmanagement.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transactions {
	Connection dbConnection;
	String  query;
	Statement newStatement;
	PreparedStatement newPreparedStatement;
	Integer minBalance = 1000;
	Integer balance1;
	
	public Transactions(){
		final String URLCONNECT = "jdbc:mysql://localhost:3306/day_3_demodb";
		final String USERNAME = "root";
		final String PASSWORD = "Killua$123";
		try {
			//create a connection with the database
			dbConnection = DriverManager.getConnection(URLCONNECT, USERNAME, PASSWORD);
			newStatement = dbConnection.createStatement();
			
			System.out.println("we are able to connect to the db");
		}catch(SQLException e) {
			System.out.println("the db cant be connected to: "+e.getMessage());
		}
	}
	
	public void addBankAccounts(String name, String address, String password, String phone) {
		//System.out.println(address+ " "+phone+" "+" "+name+" ");
		//sc.nextLine();
		query = "insert into bankaccount(name, phone, address, password) values(?,?, ?, ?)";
		//query = "insert into details(name, addr, previousCompany, salary) values(?, ?, ?, ?)";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setString(1, name);
			newPreparedStatement.setString(2, phone);
			newPreparedStatement.setString(3, address);
			newPreparedStatement.setString(4,  password);
			if(newPreparedStatement.executeUpdate()>0) {
				System.out.println("the details are added successfully");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("the details could not be added because: "+e.getMessage()+" "+e.getErrorCode());
		}
	}
	
	public int lowBalanceCheck(Long accountId) {
		query = "select balance from bankaccount where accountId = ?";
		Integer amountBalance = 0;
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, accountId);
			
			ResultSet values = newPreparedStatement.executeQuery();
			if(values.next()) {
				if(values.getInt("balance") > minBalance) {
					amountBalance = values.getInt("balance");
					System.out.println("the balance in the account is: "+ amountBalance);
					
					}
				
				
				else {
					System.out.println("the balance in the account is low please add some more money to avoid penalties");
					return 0;}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("could not execute the low balance check query: "+ e.getMessage());
		}
		//return 0;
		//System.out.println(amountBalance);
		return amountBalance;
	}
	
	
	public String details(Long accountId) {
		String password = new String();
		//System.out.println(accountId);
		query = "select password from bankaccount where accountId = ?";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, accountId);
			ResultSet value = newPreparedStatement.executeQuery();
			if(value.next()) {
				password = value.getString("password");
				System.out.println("successful check for password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("the error is in fetching the details enter proper username/password: "+e.getMessage());
		}
		return password;
	}
	
	
	public void deposit(Integer amount, Long accountId, Integer balance) {
		query = "update bankaccount set balance = ? where accountId = ?;";
		//query = "update bankaccount set balance = ? where accountId = ?;";
		
		try {
			
			newPreparedStatement = dbConnection.prepareStatement(query);
			amount = amount + balance;
			//System.out.println(balance);
			newPreparedStatement.setInt(1, amount);
			newPreparedStatement.setLong(2, accountId);
			if(newPreparedStatement.executeUpdate()>0) {
				System.out.println("Successfully deposited");

			}
		}catch(SQLException e) {
			System.out.println("the error is in depositing the amount to the bank:"+e.getMessage());
		}
		

		
		query = "insert into transactions(rxId, txId, amount, time) values(?, 1, ?, CURRENT_TIMESTAMP);";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, accountId);
			newPreparedStatement.setInt(2, amount);
			if(newPreparedStatement.executeUpdate()>0) {
				System.out.println("successfully added transactions");
			}
		}catch(SQLException e)
		{
			System.out.println("error is due to this "+ e.getMessage());
		}
	}
	
	public void withdraw(Integer amount, Long accountId, Integer balance) {
		query = "update bankaccount set balance = ? where accountId = ?;";
		//query = "update bankaccount set balance = ? where accountId = ?;";
		
		try {
			
			newPreparedStatement = dbConnection.prepareStatement(query);
			amount =  balance-amount;
			//System.out.println(balance);
			if(amount > minBalance) {
				newPreparedStatement.setInt(1, amount);
				newPreparedStatement.setLong(2, accountId);
				if(newPreparedStatement.executeUpdate()>0) {
					System.out.println("Successfully withdrawn");
				}
			}
			
		}catch(SQLException e) {
			System.out.println("the error is:"+e.getMessage());
		}
		query = "insert into transactions(rxId, txId, amount, time) values(1, ?, ?, CURRENT_TIMESTAMP);";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, accountId);

			newPreparedStatement.setInt(2, amount);
			if(newPreparedStatement.executeUpdate()>0) {
				System.out.println("successfully added transactions");
			}
		}catch(SQLException e)
		{
			System.out.println("error in withdrawing the amount from bank"+ e.getMessage());
		}
	}
	
	public void fundstransfer(Integer amount, Long accountId, Integer balance, Long touser) {
		query = "update bankaccount set balance = ? where accountId = ?;";
		
		//sender
		try {
			
			newPreparedStatement = dbConnection.prepareStatement(query);
			
			
			if(amount > minBalance) {
				newPreparedStatement.setInt(1, balance-amount);
				newPreparedStatement.setLong(2, accountId);
				if(newPreparedStatement.executeUpdate()>0) {
					System.out.println("Successfully withdrawn");
				}
			}
			
		}catch(SQLException e) {
			System.out.println("the error is in withdrawal:"+e.getMessage());
		}
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			
			
			
				newPreparedStatement.setInt(1, balance+amount);
				newPreparedStatement.setLong(2, touser);
				if(newPreparedStatement.executeUpdate()>0) {
					System.out.println("Successfully deposited");
	
			}
			
		}catch(SQLException e) {
			System.out.println("the error is in depositing:"+e.getMessage());
		}
		
		
		query = "insert into transactions(rxId, txId, amount, time) values(?, ?, ?, CURRENT_TIMESTAMP);";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, touser);
			newPreparedStatement.setLong(2, accountId);
			newPreparedStatement.setInt(3, amount);
			if(newPreparedStatement.executeUpdate()>0) {
				System.out.println("successfully added transactions");
			}
		}catch(SQLException e)
		{
			System.out.println("error is due to this "+ e.getMessage());
		}
	}
	
	public void printAllTransactions(Long userId) {
		query = " select * from transactions where rxId = ? or txId = ?;";
		try {
			newPreparedStatement = dbConnection.prepareStatement(query);
			newPreparedStatement.setLong(1, userId);
			newPreparedStatement.setLong(2, userId);
			ResultSet values = newPreparedStatement.executeQuery();
			while(values.next())
				System.out.println("tId:"+ values.getString("tId")	+" rxId: " + values.getString("rxId")+ " txId " + values.getString("txId")+ " timestamp " + values.getString("time"));
		} catch (SQLException e) {
			
			System.out.println("Error in printing the transactions"+ e.getMessage());
		}
		
	}
}
