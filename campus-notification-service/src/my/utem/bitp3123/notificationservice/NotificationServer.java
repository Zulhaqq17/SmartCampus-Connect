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
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)){
			while(true) {
				// Listen for incoming connection
				Socket clientSocket = serverSocket.accept();
				System.out.println("[Connection] Service connected from: " + clientSocket.getInetAddress().getHostAddress());
				
				// Submit connection handler to thread
				connectionPool.submit(new ClientHandler(clientSocket));
			}
		} catch (IOException e) {
			System.err.println("Server exception encounters: " + e.getMessage());
		} finally {
			connectionPool.shutdown();
		}
	}
	
	//  Reads text strings from individual sockets and updates the queue
	private class ClientHandler implements Runnable{
		private final Socket socket;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
				String alertMessage;
				// keep reading lines until stream closes
				while ((alertMessage = reader.readLine()) != null) {
                    System.out.println("[Received Payload] Staging in queue: " + alertMessage);
                    // Thread-safe insertion; blocks automatically if the queue fills up
                    alertQueue.put(alertMessage); 
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("[Connection Log] Service stream closed down cleanly.");
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
	}
	
	
	private class NotificationConsumer implements Runnable{
		@Override
		public void run() {
			try {
				while (true) {
					
					String textAlert = alertQueue.take();
					
					Thread.sleep(1000);
					
					System.out.println("\n--------------------------------------------------");
                    System.out.println("[ALERT DISPATCHED] " + textAlert);
                    System.out.println("--------------------------------------------------\n");
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public static void main(String[] args) {
		new NotificationServer().start();
	}
}
