import com.entity.Author;
import com.entity.Book;
import com.repository.AuthorRepoImpl;
import com.repository.BookRepoImpl;
import com.service.AuthorService;
import com.service.AuthorServiceImpl;
import com.service.BookService;
import com.service.BookServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        System.out.println("\nWELCOME TO 1-MANY BIDIRECTIONAL DEMO: ");
        AuthorService as = new AuthorServiceImpl(new AuthorRepoImpl());
        BookService bs = new BookServiceImpl(new BookRepoImpl());
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        Author author = InputUtil.acceptAuthorDetailsToSaveOrUpdate(sc);
                        as.saveAuthor(author);
                        System.out.println("\nRegistered author successfully!");
                    }
                    break;
                    case 2: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            List<Book> books = InputUtil.acceptBookDetailsToSave(sc);
                            if (books.isEmpty()) {
                                System.out.println("\nThere are no books to register!");
                            } else {
                                bs.saveBooks(books, authorId);
                                System.out.println("\nRegistered book successfully!");
                            }
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 3: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            Author updatedAuthor = InputUtil.acceptAuthorDetailsToSaveOrUpdate(sc);
                            as.updateAuthor(updatedAuthor, authorId);
                            System.out.println("\nUpdated author successfully!");
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 4: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            List<Book> books = author.getBooks();
                            if (books.isEmpty()) {
                                System.out.println("\nThere are no books to update!");
                            } else {
                                System.out.println("\nList of books to update: ");
                                for (Book book : books) {
                                    System.out.println(book);
                                }
                                int bookId = InputUtil.acceptBookId(sc, books);
                                Book book = InputUtil.acceptBookDetailsToUpdate(sc);
                                bs.updateBook(book, bookId);
                                System.out.println("\nUpdated Book successfully!");
                            }
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 5: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            as.deleteAuthor(authorId);
                            System.out.println("\nDeleted author successfully!");
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 6: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            List<Book> books = author.getBooks();
                            if (books.isEmpty()) {
                                System.out.println("\nThere are no books to delete!");
                            } else {
                                List<Book> updatedBooks = InputUtil.acceptBookIdsToDelete(sc, books);
                                if (updatedBooks.size() == books.size()) {
                                    System.out.println("\nThere are no books to delete!");
                                } else {
                                    as.removeAuthorBooks(updatedBooks, authorId);
                                    System.out.println("\nDeleted books successfully!");
                                }
                            }
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 7: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            System.out.println(author);
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 8: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            List<Book> books = author.getBooks();
                            if (books.isEmpty()) {
                                System.out.println("\nThere are no books to show!");
                            } else {
                                for (Book book : books) {
                                    System.out.println(book);
                                }
                            }
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 9: {
                        int bookId = InputUtil.acceptBookId(sc, new ArrayList<>());
                        Book book = bs.getBook(bookId);
                        if (book != null) {
                            Author author = book.getAuthor();
                            System.out.println(author);
                        } else {
                            System.out.println("\nFound no book!");
                        }
                    }
                    break;
                    case 10: {
                        int bookId = InputUtil.acceptBookId(sc, new ArrayList<>());
                        Book book = bs.getBook(bookId);
                        if (book != null) {
                            System.out.println(book);
                        } else {
                            System.out.println("\nFound no book!");
                        }
                    }
                    break;
                    case 11: {
                        int authorId = InputUtil.acceptAuthorId(sc);
                        Author author = as.getAuthorById(authorId);
                        if (author != null) {
                            System.out.println(author.toString(true));
                        } else {
                            System.out.println("\nFound no author!");
                        }
                    }
                    break;
                    case 12: {
                        int bookId = InputUtil.acceptBookId(sc, new ArrayList<>());
                        Book book = bs.getBook(bookId);
                        if (book != null) {
                            System.out.println(book.toString(true));
                        } else {
                            System.out.println("\nFound no book!");
                        }
                    }
                    break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        System.out.println("GOODBYE!");
        HibernateUtil.shutDown();
    }
}
