package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.cs122b.fablix.dao.CustomerDao;
import com.cs122b.fablix.entity.pojo.Customer;

// connect to database to fetch information for users
public class CustomerDaoImpl implements CustomerDao {

	/**
	 * @Description check the email before the password check
	 * @return return 0 is not valid, return other value if the email is valid
	 */
	@Override
	public int checkEmail(String email) {
		int validEmail = 0;
		try {
			// connect to db via JNDI Datasource
			// https://www.codejava.net/servers/tomcat/configuring-jndi-datasource-for-database-connection-pooling-in-tomcat
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			String checkEmailQuery = "select count(1) from customers where email = ?";
			PreparedStatement emailCheck = dbcon.prepareStatement(checkEmailQuery);
			emailCheck.setString(1, email);
			ResultSet validEmailSet = emailCheck.executeQuery();
			while (validEmailSet.next()) {
				validEmail = validEmailSet.getInt(1);
			}
			validEmailSet.close();
			emailCheck.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return validEmail;
	}

	@Override
	public Customer verifyLogin(String email, String password) {
		Customer customer = new Customer();
		// set a invalid id first, if it is not 0 afterwards, it means the login is valid
		customer.setId(-1);
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			String loginCheckQuery = String.format("SELECT * from customers where email='%s'", email);
			PreparedStatement loginCheck = dbcon.prepareStatement(loginCheckQuery);
			
			ResultSet resultSet = loginCheck.executeQuery();
			boolean success = false;
			if (resultSet.next()) {
			    // get the encrypted password from the database
				String encryptedPassword = resultSet.getString("password");
				
				// use the same encryptor to compare the user input password with encrypted password stored in DB
				success = new StrongPasswordEncryptor().checkPassword(password, encryptedPassword);
				
				if (success) {
					customer.setId(resultSet.getInt(1));
					customer.setFirstName(resultSet.getString(2));
					customer.setLastName(resultSet.getString(3));
					customer.setCcId(resultSet.getString(4));
					customer.setAddress(resultSet.getString(5));
					customer.setEmail(resultSet.getString(6));
				}
			}
			resultSet.close();
			loginCheck.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

}
