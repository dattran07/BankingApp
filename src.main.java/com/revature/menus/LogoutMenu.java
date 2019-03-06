package com.revature.menus;

import org.apache.log4j.Logger;

public class LogoutMenu {

	private static Logger log = Logger.getRootLogger();
	
	public static void showLogoutMessage() {
		log.info("                                                 ");
		log.info("                                                 ");
		log.info("    	*******************************************");
		log.info("       *             Reavture  BANKING           *");
		log.info("       *                                         *");
		log.info("       *   Thank you for using our application   *");
		log.info("    	*******************************************");
		log.info("                                                 ");
	}
	
}
