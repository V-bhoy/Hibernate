package com.util;

import com.entity.Author;
import com.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputUtil {
        private InputUtil() {
        }

        public static int menuOptions(Scanner sc){
            System.out.println("\nPLEASE CHOOSE NUMBER FROM 1 TO 6: ");
            System.out.println("1. Register Author Details");
            System.out.println("2. Register Book Details");
            System.out.println("3. Update Author Details");
            System.out.println("4. Update Book Details");
            System.out.println("5. Delete Author Details");
            System.out.println("6. Delete Book Details");
            System.out.println("7. Fetch Author Details By Author Id");
            System.out.println("8. Fetch Book Details By Author Id");
            System.out.println("9. Fetch Author Details By Book Id");
            System.out.println("10. Fetch Book Details By Book Id");
            System.out.println("11. Fetch All Details By Author Id");
            System.out.println("12. Fetch All Details By Book Id");
            if(sc.hasNextInt()) {
                int option = sc.nextInt();
                sc.nextLine();
                if(option <= 0){
                    System.out.println("Invalid option!");
                    return menuOptions(sc);
                }else{
                    return option;
                }
            }
            else {
                System.out.println("Invalid option!");
                sc.next();
                return menuOptions(sc);
            }
        }

        public static boolean wantToContinue(Scanner sc){
            System.out.println("\nPress  Y to continue and N to exit: ");
            char choice = sc.nextLine().toUpperCase().charAt(0);
            return choice == 'Y';
        }

        public static Author acceptAuthorDetailsToSaveOrUpdate(Scanner sc){
            System.out.println("Enter author name: ");
            String name;
            do{
                name = sc.nextLine().trim();
                if(name.isEmpty()){
                    System.out.println("Name cannot be empty!");
                }
            }while (name.isEmpty());
            try {
               Author author = new Author();
               author.setName(name);
               return author;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return acceptAuthorDetailsToSaveOrUpdate(sc);
            }
        }


        public static List<Book> acceptBookDetailsToSave(Scanner sc){
            System.out.println("Enter number of books to save or press 0 to skip: ");
            int numberOfBooks = 0;
            List<Book> books = new ArrayList<Book>();
            while (true){
                if(sc.hasNextInt()){
                   numberOfBooks = sc.nextInt();
                    sc.nextLine();
                    if(numberOfBooks < 0){
                        System.out.println("Please enter a valid number of books: ");
                    }else{
                        break;
                    }
                }
                else{
                    System.out.println("Invalid number of books! Please enter a valid number: ");
                }
            }
            for(int i=0; i<numberOfBooks; i++ ){
                try{
                    Book book = new Book();
                    System.out.println("Enter your book title: ");
                    String title;
                    do{
                        title = sc.nextLine().trim();
                        if(title.isEmpty()){
                            System.out.println("The title of the book cannot be empty!");
                        }
                    }while (title.isEmpty());
                    book.setTitle(title);
                    books.add(book);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    return acceptBookDetailsToSave(sc);
                }
            }
            return books;
        }

        public static Book acceptBookDetailsToUpdate(Scanner sc){
            System.out.println("Enter book title to update or press enter to skip: ");
            String book = sc.nextLine().trim();
            try{
                Book b = new Book();
                if(!book.isEmpty()){
                    b.setTitle(book);
                }else{
                    b.setTitle(null);
                }
                return b;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return acceptBookDetailsToUpdate(sc);
            }
        }

    public static List<Book> acceptBookIdsToDelete(Scanner sc, List<Book> books){
        List<Book> booksToUpdate = new ArrayList<>();
        try{
           for(Book book : books){
               System.out.println("Enter y to delete or enter n to skip: ");
               char ch = sc.nextLine().toUpperCase().charAt(0);
               if(ch == 'N'){
                   booksToUpdate.add(book);
               }
           }
           if(booksToUpdate.isEmpty()){
               System.out.println("Are you sure you want to delete all the books? Enter y to continue or n to exit: ");
               char ch = sc.nextLine().toUpperCase().charAt(0);
               if(ch == 'N'){
                   return books;
               }
           }
           return booksToUpdate;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return acceptBookIdsToDelete(sc, books);
        }
    }

        public static int acceptAuthorId(Scanner sc){
            int authorId;
            while (true){
                System.out.println("Enter your author Id: ");
                if(sc.hasNextInt()){
                    authorId = sc.nextInt();
                    sc.nextLine();
                    if(authorId <= 0){
                        System.out.println("Author Id is not valid!");
                    }else{
                        break;
                    }
                }else{
                    sc.next();
                    System.out.println("Invalid Author Id!");
                }
            }
            return authorId;
        }

        public static int acceptBookId(Scanner sc, List<Book> books){
            int bookId;
            while (true){
                System.out.println("Enter Book Id: ");
                if(sc.hasNextInt()){
                    bookId = sc.nextInt();
                    final int bookIdToCheck = bookId;
                    sc.nextLine();
                    if(bookId<= 0){
                        System.out.println("Book Id is not valid!");
                    }else if(!books.isEmpty() && books.stream().noneMatch(book -> book.getId() == bookIdToCheck)){
                        System.out.println("Book not found in the author book list! ");
                    }
                    else{
                        break;
                    }
                }else{
                    sc.next();
                    System.out.println("Invalid Book Id!");
                }
            }
            return bookId;
        }

}
