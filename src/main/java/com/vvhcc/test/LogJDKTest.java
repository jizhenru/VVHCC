package com.vvhcc.test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogJDKTest {
	public static Logger log = Logger.getLogger(LogJDKTest.class.getName());
	public static void main(String[] args) {
		log.setLevel(Level.INFO);
		log.fine("fine");
		log.info("info");
		log.warning("warning");
		log.severe("severe");
	}
}
