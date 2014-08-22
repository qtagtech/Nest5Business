package com.nest5.businessClient;

import com.bixolon.printer.BixolonPrinter;

public class PrinterInformation {
	private String type;
	private String url;
	private String port;
	private boolean connected;
	private BixolonPrinter bixolonPrinter;
	
	public PrinterInformation(String type, String url, String port){
		this.type = type;
		this.url = url;
		this.port = port;
		this.connected = false;
	}
	
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public String getPort() {
		return port;
	}
	public boolean isConnected(){
		return connected;
	}
	
	public void setConnected(boolean connected){
		this.connected = connected;
	}

	public BixolonPrinter getBixolonPrinter() {
		return bixolonPrinter;
	}

	public void setBixolonPrinter(BixolonPrinter bixolonPrinter) {
		this.bixolonPrinter = bixolonPrinter;
	}
	
}
