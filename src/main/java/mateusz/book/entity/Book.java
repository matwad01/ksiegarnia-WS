package mateusz.book.entity;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="books")
public class Book implements Serializable { 
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id")
    private long bookId;  
	@Column(name="tytul")
    private String tytul;
	@Column(name="autor")
    private String autor;
	@Column(name="rok")
    private Integer rok;
	@Column(name="cena")	
	private BigDecimal cena;
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public String getTytul() {
		return tytul;
	}
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Integer getRok() {
		return rok;
	}
	public void setRok(Integer integer) {
		this.rok = integer;
	}
	
	public BigDecimal getCena() {
		return cena;
	}
	public void setCena(BigDecimal bigDecimal) {
		this.cena = bigDecimal;
	}
} 