package com.mkyong.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {
	
	private int publishYear;
	private String title, author, genre,status;
	private double price;
	
	public Book(String title, String author,String genre,int publishYear, double price) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.publishYear = publishYear;
		this.price = price;
		this.status = "";
	}
	
	public Book() {}
		
	//public int getId() {return id;}
	
	/*@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}*/
	
	public String getTitle() {return title;}
	
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {return author;}
	
	@XmlElement
	public void setAuthor(String author) {
		this.author = author;
	}	
	
	public String getGenre() {return genre;}
	
	@XmlElement
	public void setGenre(String genre) {
		this.genre = genre;
	}	
	
	public int getPublishYear() {return publishYear;}
	
	@XmlAttribute
	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}	
	
	public double getPrice() {return price;}
	
	@XmlElement
	public void setPrice(double price) {
		this.price = price;
	}	
	
	public String getStatus() {return status;}
	
	@XmlElement
	public void setStatus(String status) {
		this.status = status;
	}
}
