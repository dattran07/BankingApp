package com.revature.main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.revature.menus.BalanceMenu;
import com.revature.menus.DepositMenu;
import com.revature.menus.LoginMenu;
import com.revature.menus.LogoutMenu;
import com.revature.menus.ServicesMenu;
import com.revature.menus.StartMenu;
import com.revature.menus.WithdrawalMenu;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utility.Handler;

public class ConsoleScript {

	private Handler handler;
	private static Scanner scan = new Scanner(System.in);
	private User currentUser = null;
	private boolean isLoggedIn = false;
	private boolean exit = false;
	private static Logger log = Logger.getRootLogger();
	private static StartMenu startM = new StartMenu();
	private static LoginMenu startLM = new LoginMenu();
	@SuppressWarnings("unused")
	private static ServicesMenu startSM = new ServicesMenu();
	@SuppressWarnings("unused")
	private static DepositMenu startDM = new DepositMenu();
	@SuppressWarnings("unused")
	private static WithdrawalMenu startWM = new WithdrawalMenu();
	@SuppressWarnings("unused")
	private static BalanceMenu startSB = new BalanceMenu();
	@SuppressWarnings("unused")
	private static LogoutMenu startLO = new LogoutMenu();

	public ConsoleScript(Handler handler) {
		this.handler = handler;
	}

	public void start() {
		while (!exit) {

			notLoggedIn();

			if (isLoggedIn)
				currentlyLoggedIn();
		}
	}

	private void notLoggedIn() {
		while (!isLoggedIn) {
			startM.showMenu();

			String input = scan.nextLine().toLowerCase().replaceAll("\\s+", "");

			// Registration
			clearScreen();
			if (input.equals("1")) {
				log.info("Please enter your new username:");
				String username = scan.nextLine();

				while (handler.userExists(username)) {
					log.info("\nUsername already exist, please try again.");
					log.info("Please enter a new username:");
					username = scan.nextLine();
				}

				log.info("Please enter new password:");
				String pass = scan.nextLine();

				log.info("Please enter your new email:");
				String email = scan.nextLine();

				while ((handler.emailExists(email) || !validate(email))) {
					log.info("\nEmail already exist or invalid email format.");
					log.info("Please enter a new email:");
					email = scan.nextLine();
				}

				while (pass.length() > 25) {
					log.info("\nPassword must be less than 25 characters.");
					log.info("Please enter a new password:");
					pass = scan.nextLine();
				}

				log.info("Please specify the account type:");
				log.info("Checking/Savings");
				String type = scan.nextLine().toLowerCase();

				while ((!type.equals("checking") && !type.equals("savings"))) {
					log.info("\nInvalid account type.");
					log.info("Please specify the account type:");
					log.info("Checking or Savings?");
					type = scan.nextLine().toLowerCase();
				}

				log.info("Please enter name for new account: ");
				String accountName = scan.nextLine();

				while ((handler.accountExists(accountName))) {
					log.info("\nAccount already exists, please try again.");
					log.info("Please enter name for new account:");
					accountName = scan.nextLine();
				}

				User newUser = new User(username, pass, email, handler);
				Account newAccount = new Account(accountName, type, handler);
				handler.createNewUser(newUser, newAccount);

				clearScreen();
				log.info("You have successfully open a bank account with Revature Banking!\n");
			}

			// Login
			else if (input.equals("2")) {
				startLM.showLoginMenu();
				log.info("Please enter your username");
				String username = scan.nextLine();

				while ((!handler.userExists(username))) {
					log.info("\nEmail does not exist or email input not valid.");
					log.info("Please enter email:");
					username = scan.nextLine();
				}

				log.info("Please enter password:");
				String pass = scan.nextLine();

				while (!handler.passwordValidation(username, pass)) {
					log.info("\nPassword does not match.");
					log.info("Please enter password:");
					pass = scan.nextLine();
				}

				currentUser = handler.grabUserByUsername(username);
				isLoggedIn = true;
				log.info("User Successfully Logged In.\n");

			}

			else if (input.equals("3")) {
				LogoutMenu.showLogoutMessage();
				exit = true;
				break;

			} else {
				log.info("Invalid input, please try again\n");
			}

		}

	}

	private void currentlyLoggedIn() {
		boolean confirmed = false;

		while (isLoggedIn) {
			clearScreen();
			ServicesMenu.showServicesMenu();

			String input = scan.nextLine().toLowerCase().replaceAll("\\s+", "");

			// Action: Logout
			if (input.equals("4")) {
				clearScreen();
				LogoutMenu.showLogoutMessage();;
				System.exit(0);

				// Action: Deposit
			} else if (input.equals("1")) {
				clearScreen();
				DepositMenu.showDepositMenu();
				log.info("\nChoose Account By Name ");
				for (int i = 0; i < currentUser.getAccounts().size(); i++) {
					log.info("Account type: " + currentUser.getAccounts().get(i).getType() + "\n" + " Account name: "
							+ currentUser.getAccounts().get(i).getName() + "\n" + " Account balance: $"
							+ currentUser.getAccounts().get(i).getBalance());
				}
				log.info(" ");

				String accountName = scan.nextLine();

				while (currentUser.getAccountByName(accountName) == null) {
					log.info("\nAccount does not exist.");
					log.info("\nChoose Account By Name:");
					for (int i = 0; i < currentUser.getAccounts().size(); i++) {
						log.info("Account type: " + currentUser.getAccounts().get(i).getType() + "\n"
								+ " Account name: " + currentUser.getAccounts().get(i).getName() + "\n"
								+ " Account balance: $" + currentUser.getAccounts().get(i).getBalance());
					}
					accountName = scan.nextLine();
				}
				log.info("How much would you like to deposit?");
				String stramount = scan.nextLine().toLowerCase().replaceAll("\\s+", "");

				try {
					double amount = Double.valueOf(stramount);
					if (amount < 0) {
						throw new Exception();
					}
					confirmed = currentUser.getAccountByName(accountName).depositBalance(amount);
					if (confirmed)
						log.info(" ");
					log.info("$" + amount + " has been deposited.");
				} catch (Exception e) {
					log.info("Transaction Failed: Invalid Numeric Input");
				}

				// Action: Withdraw
			} else if (input.equals("2")) {
				clearScreen();
				WithdrawalMenu.showWithdrawalMenu();
				log.info("\nChoose Account By Name");
				for (int i = 0; i < currentUser.getAccounts().size(); i++) {
					log.info("Account type: " + currentUser.getAccounts().get(i).getType() + "\n" + " Account name: "
							+ currentUser.getAccounts().get(i).getName() + "\n" + " Account balance: $"
							+ currentUser.getAccounts().get(i).getBalance());
				}

				log.info(" ");

				String accountName = scan.nextLine();

				while (currentUser.getAccountByName(accountName) == null) {
					log.info("\nAccount does not exist.");
					log.info("\nChoose Account By Name:");
					for (int i = 0; i < currentUser.getAccounts().size(); i++) {
						log.info("Account type: " + currentUser.getAccounts().get(i).getType() + "\n"
								+ " Account name: " + currentUser.getAccounts().get(i).getName() + "\n"
								+ " Account balance: $" + currentUser.getAccounts().get(i).getBalance());
					}
					accountName = scan.nextLine();
				}
				log.info("How much to withdraw?");
				String stramount = scan.nextLine().toLowerCase().replaceAll("\\s+", "");

				try {
					double amount = Double.valueOf(stramount);
					if (amount < 0) {
						throw new Exception();
					}
					confirmed = currentUser.getAccountByName(accountName).withdrawBalance(amount);
					if (confirmed)
						log.info("$" + amount + " has been withdrawn from "
								+ currentUser.getAccountByName(accountName).getName());
					else
						log.info("Insufficient Funds. Please try again");

				} catch (Exception e) {
					log.info("Transaction Failed: Invalid Numeric Input");
				}
			}

			else if (input.equals("3")) {
				clearScreen();
				BalanceMenu.showBalance();

				for (int i = 0; i < currentUser.getAccounts().size(); i++) {
					log.info("\t" + " Account type: " + currentUser.getAccounts().get(i).getType() + "\n"
							+ "\t Account name: " + currentUser.getAccounts().get(i).getName() + "\n\t"
							+ " Account balance: $" + currentUser.getAccounts().get(i).getBalance());
					;
				}
				log.info(" ");

				log.info("Would you like to go back to the main menu?");
				String input2 = scan.nextLine().toLowerCase().replaceAll("\\s+", "");
				log.info(" ");

				if (input2.equals("yes")) {
					break;
				}

			} else {
				log.info("Invalid Action");
			}
		}
	}

	private void clearScreen() {
		for (int i = 0; i < 75; i++) {
			log.info(" ");
		}
	}

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}