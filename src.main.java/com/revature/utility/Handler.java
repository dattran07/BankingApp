package com.revature.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Account;
import com.revature.models.User;

public class Handler {

	public User grabUserByUsername(String username) {

		User tempUser = new User(this);
		Account tempAccount = null;

		String sql = "SELECT * FROM BankUser WHERE USERNAME = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String userName = rs.getString("UserName");
				String userPassword = rs.getString("UserPassword");
				String userEmail = rs.getString("UserEmail");
				tempUser = new User(userName, userPassword, userEmail, this);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String sql2 = "SELECT a.* FROM BankAccount a, BankUser u, UserAccount ua WHERE a.accountname = ua.accountname AND ua.username = u.username AND u.username = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql2);) {

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String accName = rs.getString("AccountName");
				String accType = rs.getString("AccountType");
				Double accBalance = rs.getDouble("AccountBalance");
				tempAccount = new Account(accName, accType, accBalance, this);
				tempUser.addAccount(tempAccount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < tempUser.getAccounts().size(); i++) {
			setOwnersOfAccount(tempUser.getAccounts().get(i));
		}

		return tempUser;
	}

	private void setOwnersOfAccount(Account account) {

		User tempUser = null;
		String sql = "SELECT u.* FROM BankAccount a, BankUser u, UserAccount ua WHERE a.accountname = ua.accountname AND ua.username = u.username AND a.accountname = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, account.getName());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String userName = rs.getString("UserName");
				String userPassword = rs.getString("UserPassword");
				String userEmail = rs.getString("UserEmail");
				tempUser = new User(userName, userPassword, userEmail, this);
				account.addOwner(tempUser);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean createNewUser(User user, Account account) {
		updateUser(user);
		updateAccount(account);
		connect(user, account);

		return true;
	}

	public int updateUser(User user) {
		int userUpdated = 0;

		boolean exists = accountExists(user.getEmail());

		if (exists) {
			String sql = "UPDATE BankUser SET USERNAME = ?, USERPASSWORD = ?, USEREMAIL = ? WHERE USERNAME = ?";

			try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

				ps.setString(1, user.getUsername());
				ps.setString(2, user.getpass());
				ps.setString(3, user.getEmail());
				ps.setString(4, user.getUsername());
				userUpdated = ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			String sql = "INSERT INTO BankUser VALUES (?, ?, ?)";

			try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

				ps.setString(1, user.getUsername());
				ps.setString(2, user.getpass());
				ps.setString(3, user.getEmail());
				userUpdated = ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return userUpdated;

	}

	public int connect(User user, Account account) {
		int userAccountUpdated = 0;

		String sql = "INSERT INTO UserAccount VALUES (?, ?)";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, user.getUsername());
			ps.setString(2, account.getName());
			userAccountUpdated = ps.executeUpdate();

		} catch (SQLException e) {

		}

		return userAccountUpdated;

	}

	public int updateAccount(Account account) {
		int accountsUpdated = 0;

		boolean exists = accountExists(account.getName());

		if (exists) {
			String sql = "UPDATE BankAccount SET AccountName = ?, AccountType = ?, AccountBalance = ? WHERE AccountName = ?";

			try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

				ps.setString(1, account.getName());
				ps.setString(2, account.getType());
				ps.setDouble(3, account.getBalance());
				ps.setString(4, account.getName());
				accountsUpdated = ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {

			String sql = "INSERT INTO BankAccount VALUES (?, ?, ?)";

			try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

				ps.setString(1, account.getName());
				ps.setString(2, account.getType());
				ps.setDouble(3, account.getBalance());
				accountsUpdated = ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return accountsUpdated;
	}

	public boolean userExists(String username) {
		boolean exists = false;

		String sql = "SELECT * FROM BankUser WHERE USERNAME = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public boolean emailExists(String email) {
		boolean exists = false;

		String sql = "SELECT * FROM BankUser WHERE USEREMAIL = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public boolean passwordValidation(String user, String pass) {
		boolean validate = false;

		String sql = "SELECT * FROM BankUser WHERE USERNAME = ? AND UserPassword = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, user);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				validate = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return validate;
	}

	public boolean accountExists(String accountName) {
		boolean exists = false;

		String sql = "SELECT * FROM BankAccount WHERE AccountName = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, accountName);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	public boolean transferFunds(Account account1, Account account2, double amount) {
		boolean success = false;

		success = account1.withdrawBalance(amount);
		if (success) {
			account2.depositBalance(amount);
		}

		return success;
	}

	public Account grabAccountByName(String name) {
		Account tempAccount = null;

		String sql = "SELECT * FROM BankAccount WHERE AccountName = ?";

		try (Connection con = ConnectionUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String accName = rs.getString("AccountName");
				String accType = rs.getString("AccountType");
				Double accBalance = rs.getDouble("AccountBalance");
				tempAccount = new Account(accName, accType, accBalance, this);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempAccount;
	}

}
