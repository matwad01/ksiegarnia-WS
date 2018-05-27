package mateusz.book.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import mateusz.book.entity.Book;
import mateusz.book.service.IBookService;
import mateusz.wadas.ws.AddBookRequest;
import mateusz.wadas.ws.AddBookResponse;
import mateusz.wadas.ws.BookInfo;
import mateusz.wadas.ws.DeleteBookRequest;
import mateusz.wadas.ws.DeleteBookResponse;
import mateusz.wadas.ws.GetAllBooksResponse;
import mateusz.wadas.ws.GetBookByIdRequest;
import mateusz.wadas.ws.GetBookByIdResponse;
import mateusz.wadas.ws.ServiceStatus;
import mateusz.wadas.ws.UpdateBookRequest;
import mateusz.wadas.ws.UpdateBookResponse;

@Endpoint
public class BookEndpoint {
	private static final String NAMESPACE_URI = "http://mateusz.wadas/ksiegarnia";
	@Autowired
	private IBookService bookService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookByIdRequest")
	@ResponsePayload
	public GetBookByIdResponse getBook(@RequestPayload GetBookByIdRequest request) {
		GetBookByIdResponse response = new GetBookByIdResponse();
		BookInfo bookInfo = new BookInfo();
		BeanUtils.copyProperties(bookService.getBookById(request.getBookId()), bookInfo);
		response.setBookInfo(bookInfo);
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllBooksRequest")
	@ResponsePayload
	public GetAllBooksResponse getAllBooks() {
		GetAllBooksResponse response = new GetAllBooksResponse();
		List<BookInfo> bookInfoList = new ArrayList<>();
		List<Book> bookList = bookService.getAllBooks();
		for (int i = 0; i < bookList.size(); i++) {
			 BookInfo ob = new BookInfo();
		     BeanUtils.copyProperties(bookList.get(i), ob);
		     bookInfoList.add(ob);    
		}
		response.getBookInfo().addAll(bookInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addBookRequest")
	@ResponsePayload
	public AddBookResponse addBook(@RequestPayload AddBookRequest request) {
		AddBookResponse response = new AddBookResponse();		
    	ServiceStatus serviceStatus = new ServiceStatus();		
		Book book = new Book();
		book.setTytul(request.getTytul());
		book.setAutor(request.getAutor());
		book.setRok(request.getRok());		
		book.setCena(request.getCena());		
        boolean flag = bookService.addBook(book);
        if (flag == false) {
        	serviceStatus.setStatusCode("KONFLIKT");
        	serviceStatus.setMessage("Książka już znajduje się w bazie...");
        	response.setServiceStatus(serviceStatus);
        } else {
			BookInfo bookInfo = new BookInfo();
	        BeanUtils.copyProperties(book, bookInfo);
			response.setBookInfo(bookInfo);
        	serviceStatus.setStatusCode("SUKCES");
        	serviceStatus.setMessage("Książka dodana poprawnie...");
        	response.setServiceStatus(serviceStatus);
        }
        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBookRequest")
	@ResponsePayload
	public UpdateBookResponse updateBook(@RequestPayload UpdateBookRequest request) {
		Book book = new Book();
		BeanUtils.copyProperties(request.getBookInfo(), book);
		bookService.updateBook(book);
    	ServiceStatus serviceStatus = new ServiceStatus();
    	serviceStatus.setStatusCode("SUKCES");
    	serviceStatus.setMessage("Książka zaktualizowana poprawnie...");
    	UpdateBookResponse response = new UpdateBookResponse();
    	response.setServiceStatus(serviceStatus);
    	return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
	@ResponsePayload
	public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) {
		Book book = bookService.getBookById(request.getBookId());
    	ServiceStatus serviceStatus = new ServiceStatus();
		if (book == null ) {
	    	serviceStatus.setStatusCode("BŁĄÐ");
	    	serviceStatus.setMessage("Book is not registered, yet...");
		} else {
			bookService.deleteBook(book);
	    	serviceStatus.setStatusCode("SUKCES");
	    	serviceStatus.setMessage("Książka usunięta poprawnie...");
		}
    	DeleteBookResponse response = new DeleteBookResponse();
    	response.setServiceStatus(serviceStatus);
		return response;
	}	

}
