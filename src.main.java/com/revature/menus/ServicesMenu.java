package com.revature.menus;

import org.apache.log4j.Logger;

public class ServicesMenu {

	private static Logger log = Logger.getRootLogger();

	public static void showServicesMenu() {
		log.info("                                                 ");
		log.info("                                                 ");
		log.info("    	 *****************************************");
		log.info("        *            Revature  Bank             *");
		log.info("        *                                       *");
		log.info("                     Welcome back!               ");
		log.info("                 Please make a selection         ");
		log.info("    	 *****************************************");
		log.info("        *                                       *");
		log.info("        *                                       *");
		log.info("        *     1. DEPOSIT MONEY                  *");
		log.info("        *     2. WITHDRAW MONEY                 *");
		log.info("        *     3. VIEW BALANCE                   *");
		log.info("        *     4. LOGOUT                         *");
		log.info("        *****************************************");

	}
}
