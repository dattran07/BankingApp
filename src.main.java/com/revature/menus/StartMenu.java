package com.revature.menus;

import org.apache.log4j.Logger;

public class StartMenu {
	private static Logger log = Logger.getRootLogger();

	
	public void showMenu() {
		log.info(" ");
		log.info("      *******************************************");
		log.info("      *              Revature BANK              *");
		log.info("      *                                         *");
		log.info("      *       Welcome to Revature Banking!      *");
		log.info("      *                                         *");
		log.info("      *     1. OPEN NEW USER ACCOUNT            *");
		log.info("      *     2. LOGIN TO EXISTING ACCOUNT        *");
		log.info("      *     3. EXIT BANKING APPLICATION         *");
		log.info("      *                                         *");
		log.info("      *******************************************");
	}

}
