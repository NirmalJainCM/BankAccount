package com.example.projectaccountmanagement.AccountUI;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.Statement;
import java.util.Scanner;

import com.example.projectaccountmanagement.Entity.Accounts;
import com.example.projectaccountmanagement.Entity.TransactionsTable;
import com.example.projectaccountmanagement.transactions.Transactions;


public class UserInterface 
{
	Connection dbConnection;
	PreparedStatement newPreparedStatement;
	Statement newStatement;
	public static final String URLCONNECT = "jdbc:mysql://localhost:3306/day_3_demodb";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "Killua$123";
	
	
	
//	UserInterface() {
//		try {
//			//create a connection with the database
//			dbConnection = DriverManager.getConnection(URLCONNECT, USERNAME, PASSWORD);
//			newStatement = dbConnection.createStatement();
//			
//			System.out.println("we are able to connect to the db");
//		}catch(SQLException e) {
//			System.out.println("the db cant be connected to: "+e.getMessage());
//		}
//	}
//	
	public static void main(String[] args) {
		Integer balance = 0;
		Transactions trans1 = new Transactions();
		Integer flag1= 0;
		while(true) {
			System.out.println("Choose an option:\n"
					+ "1. Create new bank account\n"
					+ "2. Login\n"
					+ "default. exit the UI");
			Scanner sc = new Scanner(System.in);
			Integer cases = sc.nextInt();
			sc.nextLine();
			switch(cases) {
			case 1:{
				
				// create an account	
				
				System.out.println("enter the name, phone, address and password of the account");
				Accounts acc = new Accounts(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
				trans1.addBankAccounts(acc);
				
				break;
			}
			case 2:{
				// login into a user
				System.out.println("Enter the username and password to login in as a user");
				long userName = sc.nextLong();
				sc.nextLine();
				String passWord = sc.nextLine();
				
				String passwordCheck = trans1.details(userName);				
				if(passwordCheck.equals(passWord)) {
					while(true) {
						Integer flag= 0;
						System.out.println("\nChoose the options\n"
								+ "1. Check for low balance\n"
								+ "2. deposit\n"
								+ "3. withdraw\n"
								+ "4. funds transfer\n"
								+ "5. print all trancsactions\n"
								+ "default: exit the loop");
						Integer types = sc.nextInt();
						sc.nextLine();
						switch(types) {					
						case 1:{ // check low balance
							System.out.println("Checking for balance");
							trans1.lowBalanceCheck(userName);
							break;
						}
						case 2: // deposit
							balance = trans1.lowBalanceCheck(userName);
							//System.out.println(balance);
							System.out.println("enter the deposit amount");
							Integer depositAmount = sc.nextInt();
							sc.nextLine();
							trans1.deposit(depositAmount, userName, balance);
							break;
							
						case 3: 
							//withdraw
							balance = trans1.lowBalanceCheck(userName);
							//System.out.println(balance);
							System.out.println("enter the withdraw amount");
							Integer withdrawAmount = sc.nextInt();
							sc.nextLine();
							trans1.withdraw(withdrawAmount, userName, balance);
							break;
							
						case 4 : //funds transfer
							balance = trans1.lowBalanceCheck(userName);
							//System.out.println(balance);
							System.out.println("enter the accountId(long) and transfer amount");
							long touser = sc.nextLong();
							Integer transferAmount = sc.nextInt();
							sc.nextLine();
							trans1.fundstransfer(transferAmount, userName, balance, touser);
							break;
							
						case 5: // print all transactions
							trans1.printAllTransactions(userName);
							break;
							
						default:
							System.out.println("Logging out");
							flag=1;
							break;
						}
						if(flag==1)
							break;
					}
				}
				else {
					System.out.println("enter the username/password properly");
				}
				break;
					
			}
			default:
			System.out.println("exit the user interface	");
			flag1 = 1;
			break;	
			}
			if(flag1==1)
				break;
		}
		
	}
}	
//	void addBankAccounts(String name, String address, String password, String phone) {
//		//System.out.println(address+ " "+phone+" "+" "+name+" ");
//		//sc.nextLine();
//		query = "insert into bankaccount(name, phone, address, password) values(?,?, ?, ?)";
//		//query = "insert into details(name, addr, previousCompany, salary) values(?, ?, ?, ?)";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setString(1, name);
//			newPreparedStatement.setString(2, phone);
//			newPreparedStatement.setString(3, address);
//			newPreparedStatement.setString(4,  password);
//			if(newPreparedStatement.executeUpdate()>0) {
//				System.out.println("the details are added successfully");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("the details could not be added because: "+e.getMessage()+" "+e.getErrorCode());
//		}
//	}
//	
//	int lowBalanceCheck(Long accountId) {
//		query = "select balance from bankaccount where accountId = ?";
//		Integer amountBalance = 0;
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, accountId);
//			
//			ResultSet values = newPreparedStatement.executeQuery();
//			if(values.next()) {
//				if(values.getInt("balance") > minBalance) {
//					amountBalance = values.getInt("balance");
//					System.out.println("the balance in the account is: "+ amountBalance);
//					
//					}
//				
//				
//				else {
//					System.out.println("the balance in the account is low please add some more money to avoid penalties");
//					return 0;}
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("could not execute the low balance check query: "+ e.getMessage());
//		}
//		//return 0;
//		//System.out.println(amountBalance);
//		return amountBalance;
//	}
//	
//	
//	String details(Long accountId) {
//		String password = new String();
//		//System.out.println(accountId);
//		query = "select password from bankaccount where accountId = ?";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, accountId);
//			ResultSet value = newPreparedStatement.executeQuery();
//			if(value.next()) {
//				password = value.getString("password");
//				System.out.println("successful check for password");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("the error is in fetching the details: "+e.getMessage());
//		}
//		return password;
//	}
//	
//	
//	void deposit(Integer amount, Long accountId, Integer balance) {
//		query = "update bankaccount set balance = ? where accountId = ?;";
//		//query = "update bankaccount set balance = ? where accountId = ?;";
//		
//		try {
//			
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			amount = amount + balance;
//			//System.out.println(balance);
//			newPreparedStatement.setInt(1, amount);
//			newPreparedStatement.setLong(2, accountId);
//			if(newPreparedStatement.executeUpdate()>0) {
//				System.out.println("Successfully deposited");
//
//			}
//		}catch(SQLException e) {
//			System.out.println("the error is in depositing the amount to the bank:"+e.getMessage());
//		}
//		
//
//		
//		query = "insert into transactions(rxId, txId, amount, time) values(?, 1, ?, CURRENT_TIMESTAMP);";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, accountId);
//			newPreparedStatement.setInt(2, amount);
//			if(newPreparedStatement.executeUpdate()>0) {
//				System.out.println("successfully added transactions");
//			}
//		}catch(SQLException e)
//		{
//			System.out.println("error is due to this "+ e.getMessage());
//		}
//	}
//	
//	void withdraw(Integer amount, Long accountId, Integer balance) {
//		query = "update bankaccount set balance = ? where accountId = ?;";
//		//query = "update bankaccount set balance = ? where accountId = ?;";
//		
//		try {
//			
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			amount =  balance-amount;
//			//System.out.println(balance);
//			if(amount > minBalance) {
//				newPreparedStatement.setInt(1, amount);
//				newPreparedStatement.setLong(2, accountId);
//				if(newPreparedStatement.executeUpdate()>0) {
//					System.out.println("Successfully withdrawn");
//				}
//			}
//			
//		}catch(SQLException e) {
//			System.out.println("the error is:"+e.getMessage());
//		}
//		query = "insert into transactions(rxId, txId, amount, time) values(1, ?, ?, CURRENT_TIMESTAMP);";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, accountId);
//
//			newPreparedStatement.setInt(2, amount);
//			if(newPreparedStatement.executeUpdate()>0) {
//				System.out.println("successfully added transactions");
//			}
//		}catch(SQLException e)
//		{
//			System.out.println("error in withdrawing the amount from bank"+ e.getMessage());
//		}
//	}
//	
//	void fundstransfer(Integer amount, Long accountId, Integer balance, Long touser) {
//		query = "update bankaccount set balance = ? where accountId = ?;";
//		
//		//sender
//		try {
//			
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			
//			
//			if(amount > minBalance) {
//				newPreparedStatement.setInt(1, balance-amount);
//				newPreparedStatement.setLong(2, accountId);
//				if(newPreparedStatement.executeUpdate()>0) {
//					System.out.println("Successfully withdrawn");
//				}
//			}
//			
//		}catch(SQLException e) {
//			System.out.println("the error is in withdrawal:"+e.getMessage());
//		}
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			
//			
//			
//				newPreparedStatement.setInt(1, balance+amount);
//				newPreparedStatement.setLong(2, touser);
//				if(newPreparedStatement.executeUpdate()>0) {
//					System.out.println("Successfully deposited");
//	
//			}
//			
//		}catch(SQLException e) {
//			System.out.println("the error is in depositing:"+e.getMessage());
//		}
//		
//		
//		query = "insert into transactions(rxId, txId, amount, time) values(?, ?, ?, CURRENT_TIMESTAMP);";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, touser);
//			newPreparedStatement.setLong(2, accountId);
//			newPreparedStatement.setInt(3, amount);
//			if(newPreparedStatement.executeUpdate()>0) {
//				System.out.println("successfully added transactions");
//			}
//		}catch(SQLException e)
//		{
//			System.out.println("error is due to this "+ e.getMessage());
//		}
//	}
//	
//	void printAllTransactions(Long userId) {
//		query = " select * from transactions where rxId = ? or txId = ?;";
//		try {
//			newPreparedStatement = dbConnection.prepareStatement(query);
//			newPreparedStatement.setLong(1, userId);
//			newPreparedStatement.setLong(2, userId);
//			ResultSet values = newPreparedStatement.executeQuery();
//			while(values.next())
//				System.out.println("tId:"+ values.getString("tId")	+" rxId: " + values.getString("rxId")+ " txId " + values.getString("txId")+ " timestamp " + values.getString("time"));
//		} catch (SQLException e) {
//			
//			System.out.println("Error in printing the transactions"+ e.getMessage());
//		}
//		
//	}
//}
