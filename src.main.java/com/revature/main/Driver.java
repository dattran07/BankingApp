package com.revature.main;


import org.apache.log4j.Logger;

import com.revature.utility.Handler;

public class Driver {

	private static Driver d;
	private static Logger log = Logger.getRootLogger();
	private Handler handler;
	private ConsoleScript cs;

	private Driver() {

		handler = new Handler();
		cs = new ConsoleScript(handler);

		cs.start();
	}

	private static Driver getInstance() {
		if (d == null) {
			d = new Driver();
		}
		return d;
	}

	public static void main(String[] args) {
		log.info("Revature Bank is online!");
		@SuppressWarnings("unused")
		Driver bank = Driver.getInstance();
	}

}