package de.hechler.urlmonitor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class UrlMonitor {

	private static String httpUrl = "?";
	private static String logfileName = "urlmonitor.log";
	private static int loopDelayMillis = 15000;
	private static int timeoutMillis = 15000;
	private static String regex = "STATUS=200";

	private Path logPath;
	private StringBuilder line = new StringBuilder();
	
	public static void main(String[] args) {
		if (args.length > 0) {
			httpUrl = args[0];
		}
		if (!httpUrl.contains(":")) {
			usage("invalid http-url");
			return;
		}
		if (args.length > 1) {
			logfileName = args[1];
		}
		if (args.length > 2) {
			try {
				loopDelayMillis = Integer.parseInt(args[2])*1000;
				if (loopDelayMillis < 1000) {
					usage("loop daly must be at least 1 second");
					return;
				}
			} catch (NumberFormatException e) {
				usage("invalid int '"+args[2]+"' for loop delay seconds");
				return;
			}
		}
		if (args.length > 3) {
			try {
				timeoutMillis = Integer.parseInt(args[3])*1000;
			} catch (NumberFormatException e) {
				usage("invalid int '"+args[3]+"' for timeout seconds");
				return;
			}
		}
		if (args.length > 4) {
			regex = args[4];
			try {
				"".matches(regex);
			}
			catch (PatternSyntaxException e) {
				usage("invalid regular expression '"+regex+"'");
			}
		}
		UrlMonitor app = new UrlMonitor();
		app.start();
	}

	private static void usage(String msg) {
		System.out.println();
		System.out.println("ERROR: "+msg);
		System.out.println();
		System.out.println("usage: urlMonitor <http-url> [<logfilename> <loop-delay-seconds> <timeout-seconds> <regex-check>]");
		System.out.println("  http-url: the url you want to monitor. Mandatory,");
		System.out.println("            must have the scheme 'http://...' or 'https://...'");
		System.out.println("  logfilename: name of the logfile to write to.");
		System.out.println("            Default is 'usermonitor.log'");
		System.out.println("  loop-delay-seconds: repeat interval of url check ni seconds.");
		System.out.println("            Default is 15");
		System.out.println("  timeout-seconds: timeout for establishing connection or reading content.");
		System.out.println("            Default is 15");
		System.out.println("  timeout-seconds: timeout for establishing connection or reading content.");
		System.out.println("            Default is 15");
		System.out.println("  regex-check: regular expression, to search the received status/html for.");
		System.out.println("            Default is 'STATUS=200'.");
		System.out.println("            The keyword 'DUMP' logs out the received status/html.");
		System.out.println();
	}

	private void start() {
		logPath = Paths.get(logfileName);

		logln();
		logln("UrlMonitor");
		logln("==========");
		logln("URL:     "+httpUrl);
		logln("Log:     "+logfileName);
		logln("delay:   "+(loopDelayMillis/1000)+"s");
		logln("timeout: "+(timeoutMillis/1000)+"s");
		logln("RegEx:   "+regex);
		logln();
		loglnWT();

		try {
			boolean lastError = false;
			int col = 0;
			UrlChecker checker = new UrlChecker(httpUrl, timeoutMillis);
			while (true) {
				if (col >= 60) {
					col = 0;
					loglnWT();
				}
				col += 1;
				if (checker.check()) {
					if (regex.equals("DUMP")) {
						loglnWT(checker.getResult());
					}
					else if (checker.getResult().matches(".*"+regex+".*")) {
						log(".");
					}
					else {
						log("?");
					}
					lastError = false;
				}
				else {
					if (checker.getLastContent().contains("java.net.SocketTimeoutException")) {
						if (lastError) {
							log("T");
						}
						else {
							log("T[");
							log(now());
							log("]");
						}
						
					}
					else if (checker.getLastContent().contains("java.net.UnknownHostException")) {
						if (lastError) {
							log("H");
						}
						else {
							log("H[");
							log(now());
							log("]");
						}
					}
					else if (checker.getLastContent().contains("Network is unreachable: connect")) {
						if (lastError) {
							log("X");
						}
						else {
							log("X[");
							log(now());
							log("]");
						}
					}
					else {
						loglnWT("*");
						loglnWT(now() + ": [" +checker.getHttpUrl()+"] - "+checker.getLastStatus()+":"+checker.getLastContent());
					}
					lastError = true;
				}
				sleep(loopDelayMillis);
			}
		}
		catch (Exception e) {
			loglnWT();
			loglnWT(e.toString());
			e.printStackTrace();
		}
	}

	private void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted");
		}
	}

	private String now() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
	}
	
	private void log(String msg) {
		System.out.print(msg);
		line.append(msg);
	}
	
	private void logln() {
		System.out.println();
		line.append(System.lineSeparator());
		String outLine = line.toString();
	    try {
			Files.write(logPath,  outLine.getBytes(StandardCharsets.ISO_8859_1), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		line.setLength(0);
	}
	
	private void logln(String msg) {
		log(msg);
		logln();
	}
	
	private void loglnWT() {
		logln();
		log(now()+": ");
	}
	
	private void loglnWT(String msg) {
		log(msg);
		loglnWT();
	}
	
	
}
