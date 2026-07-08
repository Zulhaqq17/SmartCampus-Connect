package my.utem.bitp3123.notificationservice;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class NotificationServer {
	private static final int PORT = 9999; // notification channel port
	
	// Shared queue with capacity of 100 alerts
	private final BlockingQueue<String> alertQueue = new LinkedBlockingQueue<>(100);

	private final ExecutorService connectionPool = Executors.newFixedThreadPool(5);
	
	public void start() {
		System.out.println("-- Campus Notification Service Starting on Port " + PORT  + " ---");
		
		// process messages out of queue
		Thread consumerThread = new Thread(new NotificationConsumer());
		consumerThread.setDaemon(true);
		consumerThread.start();
		
