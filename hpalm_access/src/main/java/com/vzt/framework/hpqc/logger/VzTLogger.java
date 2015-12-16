package com.vzt.framework.hpqc.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VzTLogger {

	private static Logger logger = null;

	private static Logger getLogger() {
		if (logger == null) {
			// LoggerContext context = (LoggerContext) LoggerFactory
			// .getILoggerFactory();
			// File file = new File(System.getProperty("user.dir") +
			// "\\src\\main\\resources\\config\\logback.xml");
			// try {
			// JoranConfigurator configurator = new JoranConfigurator();
			// configurator.setContext(context);
			// context.reset();
			// configurator.doConfigure(file);
			logger = LoggerFactory.getLogger(VzTLogger.class);
			// } catch (JoranException je) {
			// StatusPrinter.printInCaseOfErrorsOrWarnings(context);
			// } catch (Exception e) {
			// StatusPrinter.printInCaseOfErrorsOrWarnings(context);
			// }
		}
		return logger;
	}

	/**
	 * Log errors to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - Array of objects that need to be logged
	 */
	public static void e(String tag, Object[] args) {
		getLogger().error(tag, args);
	}

	/**
	 * Log errors to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - additional log messages
	 */
	public static void e(String tag, String args) {
		getLogger().error(tag, args);
	}

	/**
	 * Log Warning messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - Array of objects that need to be logged
	 */
	public static void w(String tag, Object[] args) {
		getLogger().warn(tag, args);
	}

	/**
	 * Log Warning messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - additional log messages
	 */
	public static void w(String tag, String args) {
		getLogger().warn(tag + " {}", args);
	}

	/**
	 * Log Debug messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - Array of objects that need to be logged
	 */
	public static void d(String tag, Object[] args) {
		getLogger().debug(tag, args);
	}

	/**
	 * Log Debug messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - additional log messages
	 */
	public static void d(String tag, String args) {
		getLogger().debug(tag + " {}", args);
	}

	/**
	 * Log Assertion messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - Array of objects that need to be logged
	 */
	public static void a(String tag, Object[] args) {
		getLogger().trace(tag, args);
	}

	/**
	 * Log Assertion messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - additional log messages
	 */
	public static void a(String tag, String args) {
		getLogger().trace(tag + " {}", args);
	}

	/**
	 * Log Info messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - additional log messages
	 */
	public static void i(String tag, String arg) {
		getLogger().info(tag + " {}", arg);
	}

	/**
	 * Log Info messages to the log file
	 * 
	 * @param tag
	 * @param args
	 *            - Array of objects that need to be logged
	 */
	public static void i(String tag, Object[] args) {
		getLogger().info(tag, args);
	}

}
