package pw.vodes.styx.util.multios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.threads.ProcessThread;

public class CommandLineUtil {

	public static void runCommand(ArrayList<String> commands) {
		Process process;
		ArrayList<String> commandList = new ArrayList<>();
		if (getOS() == OS.Windows) {
			commandList.add("cmd");
			commandList.add("/c");
			commandList.add("start");
		} else if (getOS() == OS.Linux) {
			commandList.add("bash");
			commandList.add("-c");
		} else if (getOS() == OS.Mac) {
			commandList.add("/bin/bash");
			commandList.add("-c");
		} else {
			return;
		}
		commandList.addAll(commands);
		new ProcessThread(new ProcessBuilder(commandList)).start();
	}
	
	public static boolean isProcessRunning(String process) {
		for(String s : getRunningProcesses()) {
			if(StringUtils.containsIgnoreCase(s, process)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> getRunningProcesses() {
		ArrayList<String> processes = new ArrayList<>();
		String line;
		try {
			Process p = null;
			if (getOS() == OS.Windows) {
				p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			} else if (getOS() == OS.Linux) {
				p = Runtime.getRuntime().exec("ps -e");
			} else if (getOS() == OS.Mac) {
				p = Runtime.getRuntime().exec("ps -e");
			}
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				processes.add(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return processes;
	}

	public static OS getOS() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return OS.Windows;
		} else if (SystemUtils.IS_OS_LINUX) {
			return OS.Linux;
		} else {
			return OS.Mac;
		}
	}

}
