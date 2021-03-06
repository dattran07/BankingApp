package com.revature.models;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.utility.Handler;

public class User implements Serializable {

	private static final long serialVersionUID = -3811351239130265652L;

	private String username;
	private String pass;
	private String email;
	private ArrayList<Account> accounts = new ArrayList<Account>();

	private Handler handler;

	public User() {
		super();
	}

	public User(Handler handler) {
		super();
		email = "";
		username = "";
		pass = "";
		accounts = new ArrayList<Account>();
		this.handler = handler;
	}

	public User(String username, String pass, String email, Handler handler) {
		super();
		this.email = email;
		this.username = username;
		this.pass = pass;
		this.accounts = new ArrayList<Account>();
		this.handler = handler;
	}

	public User(String username, String pass, String email, ArrayList<Account> accounts, Handler handler) {
		super();
		this.email = email;
		this.username = username;
		this.pass = pass;
		this.accounts = accounts;
		this.handler = handler;
	}

	public User(String userName, String userPassword, String userEmail) {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		handler.updateUser(this);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		handler.updateUser(this);
	}

	public String getpass() {
		return pass;
	}

	public void setpass(String pass) {
		this.pass = pass;
		handler.updateUser(this);
	}

	public Account getAccountByName(String name) {
		Account tempAccount;
		for (int i = 0; i < accounts.size(); i++) {
			tempAccount = accounts.get(i);
			if (tempAccount.getName().equals(name))
				return tempAccount;
		}
		return null;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public boolean removeAccount(Account account) {
		return this.accounts.remove(account);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", pass=" + pass + ", accounts=" + accounts
				+ ", handler=" + handler + "]";
	}

}