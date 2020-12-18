package com.mkyong.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server {

	public static void main(String args[]) throws IOException {

		ServerSocket server = new ServerSocket(2555);
		while (true) {
			System.out.println("Waiting...");
			Socket server1 = server.accept();
			Socket server2 = server.accept();
			Thread t = new ServerThread(server1, server2);
			t.start();
		}
	}
}

class ServerThread extends Thread {
	public final static int port_to_connect = 2555;
	public final static String FILE_TO_SEND = "C:\\Users\\ASUS-PC\\eclipse-workspace\\BookManagement\\book.xml";
	public final static String FILE_TO_RECEIVE = "C:\\Users\\ASUS-PC\\eclipse-workspace\\BookManagement\\book.xml";
	public final static int FILE_SIZE = 6022386;

	Socket server1;
	Socket server2;

	public ServerThread(Socket s, Socket s2) {
		this.server1 = s;
		this.server2 = s2;
	}
	
	@Override
	public void run() {
		int bytesRead;
		int current = 0;

		Scanner in = new Scanner(System.in);

		FileInputStream fis = null;
		BufferedInputStream bis = null;

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		OutputStream os = null;
		//System.out.println("Testing");

		try {
			System.out.println("Accepted Connection : " + server1);
			
			byte[] bytearray = new byte[FILE_SIZE];
			InputStream is = server1.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVE);
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(bytearray, 0, bytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(bytearray, current, (bytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			}

			while (bytesRead > -1);

			bos.write(bytearray, 0, current);
			bos.flush();

			File file2 = new File("C:\\Users\\ASUS-PC\\eclipse-workspace\\BookManagement\\book.xml");

			JAXBContext jaxbContext = JAXBContext.newInstance(Books.class);
			System.out.println("File :" + FILE_TO_RECEIVE + "Download (" + current + "bytes Read)");

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Books book = (Books) jaxbUnmarshaller.unmarshal(file2);

			int bookCount = book.getBooks().size();

			while (bookCount > 0) {
				Book b = book.getBooks().get(bookCount - 1);

				if (b.getStatus().equals("")) {
					System.out.println("Enter book status :");
					String status = in.nextLine();

					book.getBooks().get(bookCount - 1).setStatus(status);

				}
				bookCount--;
			}

			File file3 = new File("C:\\Users\\ASUS-PC\\eclipse-workspace\\BookManagement\\book.xml");

			JAXBContext jaxbContext3 = JAXBContext.newInstance(Books.class);

			Marshaller jaxbMarshaller = jaxbContext3.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(book, file3);

			int countGenre1 = 0;
			int countGenre2 = 0;
			int countGenre3 = 0;
			
			int bookCount2 = book.getBooks().size();

			for (int x = 0; x < bookCount2; x++) {
				if (book.getBooks().get(x).getGenre().equalsIgnoreCase("Fantasy")) {
					countGenre1++;
				} else if (book.getBooks().get(x).getGenre().equalsIgnoreCase("Horror")) {
					countGenre2++;
				}
				 else if (book.getBooks().get(x).getGenre().equalsIgnoreCase("Romance")) {
						countGenre3++;
					}
			}

			System.out.println("Number of genre Fantasy is : " + countGenre1);
			System.out.println("Number of genre Horror is : " + countGenre2);
			System.out.println("Number of genre Romance is : " + countGenre3);

			bos.close();
			server1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		catch (JAXBException e) {
			e.printStackTrace();
		}

		try {
			File myFile = new File(FILE_TO_SEND);
			byte[] bytearray = new byte[(int) myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(bytearray, 0, bytearray.length);

			os = server2.getOutputStream();
			System.out.println("Sending " + FILE_TO_SEND + "(" + bytearray.length + "byte)");

			os.write(bytearray, 0, bytearray.length);
			os.flush();
			System.out.println("Done");

			bis.close();
			bos.close();
			os.close();
			server2.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
