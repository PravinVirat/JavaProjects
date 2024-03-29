import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LibraryManagement {
  public static void main(String[] args) {
    try {
      File BooksInfo=new File("Book.txt");
      File UsersInfo=new File("Users.txt");
      File BorrowedBooks=new File("BorrowedBooks.txt");
      File PenaltyDetails=new File("Penalty.txt");
      Scanner BookInfo=new Scanner(BooksInfo);
      Scanner UsersDetails=new Scanner(UsersInfo);
      Scanner BorrowDetails=new Scanner(BorrowedBooks);
      Scanner PenaltyCheck=new Scanner(PenaltyDetails);

      Library library=new Library();
      Book book=null;
      while(BookInfo.hasNextLine()){
        String[] eachBook=(BookInfo.nextLine()).split("`");
        book=new Book(eachBook[0], eachBook[1], Integer.parseInt(eachBook[2]),eachBook[3]);
        library.addBook(book);
      }
      BookInfo.close();

      Librarian librarian=new Librarian("Pravin", 17, "12345");
      LibraryUsers libraryUsers=null;
      HashMap<String,LocalDateTime> penaltyCalc=new HashMap<String,LocalDateTime>();
      while(UsersDetails.hasNextLine()){
        String[] eachUser=(UsersDetails.nextLine()).split("`");
        libraryUsers=new LibraryUsers(eachUser[0],Integer.parseInt(eachUser[1]),eachUser[2]);
        String[] borrowed=(BorrowDetails.nextLine()).split("`");
        String[] penaltyDetails=((PenaltyCheck.nextLine()).split("`"));
        for(int i=1;i<borrowed.length;i++){
          libraryUsers.getBorrowedBooks().add(library.searchBook(Integer.parseInt(borrowed[i])));
          penaltyCalc.put("TakenDate", LocalDateTime.now());
          penaltyCalc.put("DueDate", LocalDateTime.parse(penaltyDetails[i]));
          libraryUsers.getBorrowedBooksDueDateAndPenaltyCalc().add(penaltyCalc);
        }
        library.addUser(libraryUsers);
      }
      UsersDetails.close();
      BorrowDetails.close();
      PenaltyCheck.close();

      int passLastDigit=library.getUsers().size();
      LibraryUsers currentUser=null;
      LoginPage: while(true){
        Scanner userInput=new Scanner(System.in);
        System.out.println("\n" + //
            "█░█░█ █▀▀ █░░ █▀▀ █▀█ █▀▄▀█ █▀▀   ▀█▀ █▀█   ▀█ █░░ █ █▄▄\n" + //
            "▀▄▀▄▀ ██▄ █▄▄ █▄▄ █▄█ █░▀░█ ██▄   ░█░ █▄█   █▄ █▄▄ █ █▄█"+"\n\n");
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║             Main Menu              ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Sign Up                         ║");
            System.out.println("║ 2. Login                           ║");
            System.out.println("║ 3. Librarian Login                 ║");
            System.out.println("║ 4. Exit                            ║");
            System.out.println("╚════════════════════════════════════╝");            
        int userChoice=userInput.nextInt();
        switch (userChoice) {
          case 1:
            System.out.println("Enter your name: ");
            userInput.nextLine();
            String name=userInput.nextLine();
            System.out.println("Enter your age: ");
            int age=userInput.nextInt();
            if(name.length()<3||name.contains("`")||age>150||age<=15){
              System.out.println("Name should be greater than 3 letters, not include (`) and age should be less than 150 and greater than 15.");
              break;
            }
            String id=age+name.split("")[0]+name.split("")[2]+passLastDigit;
            passLastDigit++;
            libraryUsers=new LibraryUsers(name, age, id);
            if(library.searchUser(id)==null){
              library.addUser(libraryUsers);
              currentUser=libraryUsers;
              currentUser.viewUserInfo();
              Login: while (true) {
                System.out.println("╔════════════════════════════════════╗");
                System.out.println("║           User Menu                ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║ 1. Display Books                   ║");
                System.out.println("║ 2. Search Book                     ║");
                System.out.println("║ 3. Get Book                        ║");
                System.out.println("║ 4. Return Book                     ║");
                System.out.println("║ 5. User Info                       ║");
                System.out.println("║ 6. Penalty Info                    ║");
                System.out.println("║ 7. Back to Login                   ║");
                System.out.println("╚════════════════════════════════════╝");            

                userChoice=userInput.nextInt();
                switch (userChoice) {
                  case 1:
                    DisplayBooks: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║          Library Menu              ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display All Books               ║");
                      System.out.println("║ 2. Display Borrowed Books          ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");

                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          library.displayBooks();
                          break;
                        
                        case 2:
                          currentUser.displayBorrowedBooks();
                          break;

                        case 3:
                          break DisplayBooks;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 2:
                    Search: while(true){
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            SEARCH BY               ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. Genre                           ║");
                      System.out.println("║ 5. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");

                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                        
                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          System.out.println("Enter the genre of the book:");
                          userInput.nextLine();
                          String genre=userInput.nextLine();
                          String genreList="ClassicDystopianFictionFantasyNon-fictionMysteryMemoirScience FictionMagical RealismHorrorThrillerHistorical FictionPsychologySelf-helpCrime";
                          if(genreList.contains(genre)){
                            book=library.searchBook("genre", genre);
                          }
                          else{
                            System.out.println("Apologies, but we don't have books in that genre.");
                          }
                          break;

                        case 5:
                          break Search;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }   
                    }
                    break;

                  case 3:
                    Get: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            GET BOOK BY             ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. GO Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;

                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          break Get;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 4:
                    Return: while (true) {
                      System.out.println("\nBooks you've borrowed: \n");
                      currentUser.displayBorrowedBooks();
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║           RETURN BOOK BY           ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. GO Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          break Return;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 5:
                    UserInfo: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            USER OPTIONS            ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display Info                    ║");
                      System.out.println("║ 2. Change Info                     ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          currentUser.viewUserInfo();
                          break;

                        case 2:
                          ChangeInfo: while (true) {
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║         CHANGE USER INFO           ║");
                            System.out.println("╠════════════════════════════════════╣");
                            System.out.println("║ 1. Change Name                     ║");
                            System.out.println("║ 2. Change Age                      ║");
                            System.out.println("║ 3. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                System.out.println("Enter the new username: ");
                                userInput.nextLine();
                                String newName=userInput.nextLine();
                                currentUser.changeName(newName);
                                break;

                              case 2:
                                System.out.println("Enter the correct age: ");
                                int newAge=userInput.nextInt();
                                currentUser.changeAge(newAge);
                                break;
                              
                              case 3:
                                break ChangeInfo;
                            
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          
                        case 3:
                          break UserInfo;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;
                  
                  case 6:
                    PenaltyInfo: while(true){
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║        PENALTY OPTIONS             ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display Penalty Details         ║");
                      System.out.println("║ 2. Pay Penalty                     ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          currentUser.calcPenalty();
                          break;
                        
                        case 2:
                          currentUser.payPenalty();
                          break;

                        case 3:
                          break PenaltyInfo;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;
                    
                  case 7:
                    break Login;

                  default:
                    System.out.println("Please enter the valid input");
                    break;
                }
              }
            }
            else{
              System.out.println("Glad! You already have an account, please log in");
            }
            break;

          case 2:
            System.out.println("Enter your passID: ");
            String userId=userInput.next();
            if((library.searchUser(userId)!=null)){
              currentUser=library.searchUser(userId);
              Login: while (true) {
                System.out.println("╔════════════════════════════════════╗");
                System.out.println("║           User Menu                ║");
                System.out.println("╠════════════════════════════════════╣");
                System.out.println("║ 1. Display Books                   ║");
                System.out.println("║ 2. Search Book                     ║");
                System.out.println("║ 3. Get Book                        ║");
                System.out.println("║ 4. Return Book                     ║");
                System.out.println("║ 5. User Info                       ║");
                System.out.println("║ 6. Penalty Info                    ║");
                System.out.println("║ 7. Back to Login                   ║");
                System.out.println("╚════════════════════════════════════╝");            

                userChoice=userInput.nextInt();
                switch (userChoice) {
                  case 1:
                    DisplayBooks: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║          Library Menu              ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display All Books               ║");
                      System.out.println("║ 2. Display Borrowed Books          ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");

                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          library.displayBooks();
                          break;
                        
                        case 2:
                          currentUser.displayBorrowedBooks();
                          break;

                        case 3:
                          break DisplayBooks;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 2:
                    Search: while(true){
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            SEARCH BY               ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. Genre                           ║");
                      System.out.println("║ 5. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");

                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                        
                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            book.displayBookInfo();
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║ 1. Get that Book                   ║");
                            System.out.println("║ 2. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                currentUser.borowBook(book);
                                break;
                              
                              case 2:
                                break;
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          System.out.println("Enter the genre of the book:");
                          userInput.nextLine();
                          String genre=userInput.nextLine();
                          String genreList="ClassicDystopianFictionFantasyNon-fictionMysteryMemoirScience FictionMagical RealismHorrorThrillerHistorical FictionPsychologySelf-helpCrime";
                          if(genreList.contains(genre)){
                            book=library.searchBook("genre", genre);
                          }
                          else{
                            System.out.println("Apologies, but we don't have books in that genre.");
                          }
                          break;

                        case 5:
                          break Search;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }   
                    }
                    break;

                  case 3:
                    Get: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            GET BOOK BY             ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. GO Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't find the requested book. You might be interested in this alternative.\n");
                          }
                          break;

                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            currentUser.borowBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          break Get;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 4:
                    Return: while (true) {
                      System.out.println("\nBooks you've borrowed: \n");
                      currentUser.displayBorrowedBooks();
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║           RETURN BOOK BY           ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Title                           ║");
                      System.out.println("║ 2. Author                          ║");
                      System.out.println("║ 3. ISBN                            ║");
                      System.out.println("║ 4. GO Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          System.out.println("Enter the title of the book: ");
                          userInput.nextLine();
                          String bookName=userInput.nextLine();
                          book=library.searchBook("title", bookName);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;
                          
                        case 2:
                          System.out.println("Enter the author of the book: ");
                          userInput.nextLine();
                          String authorName=userInput.nextLine();
                          book=library.searchBook("author", authorName);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 3:
                          System.out.println("Enter the ISBN of the book:");
                          int ISBN=userInput.nextInt();
                          book=library.searchBook(ISBN);
                          if(book!=null){
                            currentUser.returnBook(book);
                          }
                          else{
                            System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                          }
                          break;

                        case 4:
                          break Return;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 5:
                    UserInfo: while (true) {
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║            USER OPTIONS            ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display Info                    ║");
                      System.out.println("║ 2. Change Info                     ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          currentUser.viewUserInfo();
                          break;

                        case 2:
                          ChangeInfo: while (true) {
                            System.out.println("╔════════════════════════════════════╗");
                            System.out.println("║         CHANGE USER INFO           ║");
                            System.out.println("╠════════════════════════════════════╣");
                            System.out.println("║ 1. Change Name                     ║");
                            System.out.println("║ 2. Change Age                      ║");
                            System.out.println("║ 3. Go Back                         ║");
                            System.out.println("╚════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                System.out.println("Enter the new username: ");
                                userInput.nextLine();
                                String newName=userInput.nextLine();
                                currentUser.changeName(newName);
                                break;

                              case 2:
                                System.out.println("Enter the correct age: ");
                                int newAge=userInput.nextInt();
                                currentUser.changeAge(newAge);
                                break;
                              
                              case 3:
                                break ChangeInfo;
                            
                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          
                        case 3:
                          break UserInfo;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;
                  
                  case 6:
                    PenaltyInfo: while(true){
                      System.out.println("╔════════════════════════════════════╗");
                      System.out.println("║        PENALTY OPTIONS             ║");
                      System.out.println("╠════════════════════════════════════╣");
                      System.out.println("║ 1. Display Penalty Details         ║");
                      System.out.println("║ 2. Pay Penalty                     ║");
                      System.out.println("║ 3. Go Back                         ║");
                      System.out.println("╚════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          currentUser.calcPenalty();
                          break;
                        
                        case 2:
                          currentUser.payPenalty();
                          break;

                        case 3:
                          break PenaltyInfo;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;
                    
                  case 7:
                    break Login;

                  default:
                    System.out.println("Please enter the valid input");
                    break;
                }
              }
            }
            else{
              System.out.println("Sorry! you don't have an account, please login");
            }
            break;
          
          case 3:
            System.out.println("Enter your name: ");
            userInput.nextLine();
            String librarianName=userInput.nextLine();
            System.out.println("Enter your age: ");
            int librarianAge=userInput.nextInt();
            System.out.println("Enter the pass ID: ");
            String passID=userInput.next();
            if((librarian.getName().equals(librarianName))&&(librarian.getAge()==librarianAge)&&(librarian.getUserId().equals(passID))){
              Admin: while (true) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║          Library Management Menu     ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ 1. Stocks Check                      ║");
                System.out.println("║ 2. Restock                           ║");
                System.out.println("║ 3. Add Book                          ║");
                System.out.println("║ 4. Members' details                  ║");
                System.out.println("║ 5. Remove User                       ║");
                System.out.println("║ 6. Back to Login                     ║");
                System.out.println("╚══════════════════════════════════════╝");
                userChoice=userInput.nextInt();
                switch (userChoice) {
                  case 1:
                    librarian.stockCheck(library);
                    break;

                  case 2:
                    Restock: while (true) {
                      System.out.println("╔══════════════════════════════════════╗");
                      System.out.println("║           RESTOCK OPTIONS            ║");
                      System.out.println("╠══════════════════════════════════════╣");
                      System.out.println("║ 1. Restock All Books                 ║");
                      System.out.println("║ 2. Restock Specific Book             ║");
                      System.out.println("║ 3. Go Back                           ║");
                      System.out.println("╚══════════════════════════════════════╝");

                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          librarian.restockBooks(library);
                          break;

                        case 2:
                          RestockBy: while (true) {
                            System.out.println("╔══════════════════════════════════════╗");
                            System.out.println("║          RESTOCK BOOK BY             ║");
                            System.out.println("╠══════════════════════════════════════╣");
                            System.out.println("║ 1. Title                             ║");
                            System.out.println("║ 2. Author                            ║");
                            System.out.println("║ 3. ISBN                              ║");
                            System.out.println("║ 4. GO Back                           ║");
                            System.out.println("╚══════════════════════════════════════╝");
                            userChoice=userInput.nextInt();
                            switch (userChoice) {
                              case 1:
                                System.out.println("Enter the title of the book: ");
                                userInput.nextLine();
                                String bookName=userInput.nextLine();
                                book=library.searchBook("title", bookName);
                                if(book!=null){
                                  librarian.restockBooks(book, library);
                                }
                                else{
                                  System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                                }
                                break;
                                
                              case 2:
                                System.out.println("Enter the author of the book: ");
                                userInput.nextLine();
                                String authorName=userInput.nextLine();
                                book=library.searchBook("author", authorName);
                                if(book!=null){
                                  librarian.restockBooks(book, library);
                                }
                                else{
                                  System.out.println("\nI apologize, but we couldn't locate the requested book.\n");
                                }
                                break;

                              case 3:
                                System.out.println("Enter the ISBN of the book:");
                                int ISBN=userInput.nextInt();
                                book=library.searchBook(ISBN);
                                if(book!=null){
                                  librarian.restockBooks(book, library);
                                }
                                else{
                                  System.out.println("\nSorry! No such book is found\n");
                                }
                                break;

                              case 4:
                                break RestockBy;

                              default:
                                System.out.println("Please enter the valid input");
                                break;
                            }
                          }
                          break;

                        case 3:
                          break Restock;
                      
                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 3:
                    book=askBookDetails();
                    librarian.addBookToLibrary(book, library);
                    break;

                  case 4:
                    MemberDetails: while (true) {
                      System.out.println("╔══════════════════════════════════════╗");
                      System.out.println("║       MEMBER DETAILS OPTIONS         ║");
                      System.out.println("╠══════════════════════════════════════╣");
                      System.out.println("║ 1. Display All Members Details       ║");
                      System.out.println("║ 2. Display Specific Member Details   ║");
                      System.out.println("║ 3. Go Back                           ║");
                      System.out.println("╚══════════════════════════════════════╝");
                      userChoice=userInput.nextInt();
                      switch (userChoice) {
                        case 1:
                          librarian.displayUserDetails(library);
                          break;
                        
                        case 2:
                          System.out.println("Enter the ID of that member");
                          String searchID=userInput.next();
                          librarian.displayUserDetails(searchID, library);
                          break;
                        
                        case 3:
                          break MemberDetails;

                        default:
                          System.out.println("Please enter the valid input");
                          break;
                      }
                    }
                    break;

                  case 5:
                    System.out.println("Enter the ID of the user");
                    String removeID=userInput.next();
                    librarian.removeLibraryUsers(removeID, library);
                    break;

                  case 6:
                    break Admin;
                
                  default:
                    System.out.println("Please enter the valid input");
                    break;
                }
              }
            }
            else{
              System.out.println("Sorry! the details you entered is wrong.");
            }
            break;
          
          case 4:
            try {
              String storeStr="";
              FileWriter Books=new FileWriter("Book.txt");
              FileWriter Users=new FileWriter("Users.txt");
              FileWriter Borrowed=new FileWriter("BorrowedBooks.txt");
              FileWriter Penalty=new FileWriter("Penalty.txt");
              for(int i=0;i<library.getBookList().size();i++){
                storeStr+=library.getBookList().get(i).getTitle()+"`"+library.getBookList().get(i).getAuthor()+"`"+library.getBookList().get(i).getAvailability()+"`"+library.getBookList().get(i).getGenre()+"\n";
              }
              Books.write(storeStr);
              Books.close();
              storeStr="";
              String storeBorrowed="";
              String storePenalty="";
              for(int i=0;i<library.getUsers().size();i++){
                storeBorrowed+=library.getUsers().get(i).getUserId();
                storePenalty+=library.getUsers().get(i).getUserId();
                storeStr+=library.getUsers().get(i).getName()+"`"+library.getUsers().get(i).getAge()+"`"+library.getUsers().get(i).getUserId()+"\n";
                for(int j=0;j<library.getUsers().get(i).getBorrowedBooks().size();j++){
                  storeBorrowed+="`"+library.getUsers().get(i).getBorrowedBooks().get(j).getISBN();
                  storePenalty+="`"+library.getUsers().get(i).getBorrowedBooksDueDateAndPenaltyCalc().get(j).get("DueDate");
                }
                storeBorrowed+="\n";
                storePenalty+="\n";
              }
              Penalty.write(storePenalty);
              Penalty.close();
              Borrowed.write(storeBorrowed);
              Borrowed.close();
              Users.write(storeStr);
              Users.close();
            } catch (Exception e) {
              System.out.println("File not found");
            }
            System.out.println("Thank you visit again!");
            break LoginPage;

          default:
            System.out.println("Please enter the valid input");
            break;
        }
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
  }

  //Asking book details
  static Book askBookDetails(){
    Scanner userInput=new Scanner(System.in);
    String title,author,genre;
    int availability;
    System.out.println("Enter the Title of the book:");
    title=userInput.nextLine();
    System.out.println("Enter the Author name:");
    author=userInput.nextLine();
    System.out.println("Enter the no. of copies to add:");
    availability=userInput.nextInt();
    System.out.println("Enter the genre of the book: ");
    userInput.nextLine();
    genre=userInput.nextLine();
    Book book=new Book(title, author, availability,genre);
    return book;
  }
}

class Book{
  private String title,author;
  private static int bookNum;
  private int ISBN;
  private int availability;
  private String genre;
  //Adding book info
  public Book(String title, String author,int availability,String genre){
    this.title=title;
    this.author=author;
    bookNum++;
    ISBN=bookNum;
    this.availability=availability;
    this.genre=genre;
  }
  //Displaying book info
  public void displayAllBooks(){
    System.out.printf("║ %-40s %-40s %-30s %-15s %-15s ║%n",title,author,genre,ISBN,availability);
  }
  //Display all books
  public void displayBookInfo(){
    System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
    System.out.printf("║ %-25s : %-45s ║%n", "Title", title);
    System.out.printf("║ %-25s : %-45s ║%n", "Author", author);
    System.out.printf("║ %-25s : %-45s ║%n", "ISBN", ISBN);
    System.out.printf("║ %-25s : %-45s ║%n", "Availability", availability+" units");
    System.out.printf("║ %-25s : %-45s ║%n", "Genre", genre);
    System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
  }
  //updatingAvailability
  public void updateAvailability(int availability){
    this.availability+=availability;
  }
  //getter methods
  public String getTitle() {
    return title;
  }
  public String getAuthor() {
    return author;
  }
  public int getISBN() {
    return ISBN;
  }
  public int getAvailability(){
    return availability;
  }
  public String getGenre(){
    return genre;
  }
}


class Library{
  private ArrayList<Book> bookList=new ArrayList<Book>();
  private ArrayList<LibraryUsers> users=new ArrayList<LibraryUsers>();

  //Adding user
  public void addUser(LibraryUsers libraryUsers){
    users.add(libraryUsers);
  }
  //Adding book
  public void addBook(Book book){
    bookList.add(book);
  }
  //Display allBooks
  public void displayBooks(){
    System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
    System.out.printf("║ %-72s %-71s ║%n","", "Books in Zlib");
    System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
    System.out.printf("║ %-40s %-40s %-30s %-15s %-15s ║%n", "Title", "Author","Genre", "ISBN", "Availability");
    System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
    for(Book book:bookList){
      book.displayAllBooks();
    }
    System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
  }
  //Getter methods
  public ArrayList<Book> getBookList(){
    return bookList;
  }
  public ArrayList<LibraryUsers> getUsers(){
    return users;
  }
  //Restocking all books
  public void restockBooks(){
    for(Book book: bookList){
      if(book.getAvailability()<10){
        book.updateAvailability(10);
      }
    }
  }
  //Restocking specific book
  public boolean restockBooks(Book book){
    if(book.getAvailability()<10){
      book.updateAvailability(10);
      return true;
    }
    return false;
  }

  //SEARCH BOOKS
  //By title && author && Genre
  public Book searchBook(String searchBy,String userValue){
    if("title".equals(searchBy)){
      for(Book book: bookList){
        if((book.getTitle()).equals(userValue)){
          return book;
        }
      }
      for(Book book: bookList){
        if((book.getTitle().toLowerCase().contains(userValue.toLowerCase()))){
          book.displayBookInfo();
        }
      }
      return null;
    }
    if("author".equals(searchBy)){
      for(Book book: bookList){
        if((book.getAuthor()).equals(userValue)){
          return book;
        }
      }
      for(Book book: bookList){
        if((book.getAuthor().toLowerCase().contains(userValue.toLowerCase()))){
          book.displayBookInfo();
        }
      }
      return null;
    }
    if("genre".equals(searchBy)){
      for(Book book: bookList){
        if (book.getGenre().toLowerCase().contains(userValue.toLowerCase())) {
          book.displayBookInfo();
        }
      }
    }
    return null;
  }
  //By ISBN 
  public Book searchBook(int ISBN){
    for(Book book: bookList){
      if(book.getISBN()==ISBN){
        return book;
      }
    }
    return null;
  }

  //Search user
  public LibraryUsers searchUser(String id){
    for(LibraryUsers user:users){
      if(user.getUserId().equals(id)){
        return user;
      }
    }
    return null;
  }
  //Display all users
  public void userDetails(){
    for(LibraryUsers libraryUsers: users){
      libraryUsers.viewUserInfo();
    }
  }
  //Display specific users
  public void userDetails(String id){
    if(searchUser(id)!=null){
      searchUser(id).viewUserInfo();
    }
    else{
      System.out.println("Sorry! No such user is found");
    }

  }
  //Remove user
  public boolean removeUser(String id){
    if(searchUser(id)!=null){
      users.remove(searchUser(id));
      return true;
    }
    return false;
  }
}

class User{
  private String name;
  private int age;
  private String id;
  //Initializing userDetails
  public User(String name,int age,String id){
    this.name=name;
    this.age=age;
    this.id=id;
  }
  //userInfo
  public void viewUserInfo(){
    System.out.println("\n-----------------------------------\nName: "+name+"\nAge: "+age+"\nThis is your PASS ID: "+id+"\nDon't forget it!\n-----------------------------------\n");
  }
  //Getter methods
  public String getUserId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public int getAge(){
    return age;
  }
  //CHANGING INFO
  //change name
  public void changeName(String name){
    this.name=name;
    System.out.println("Name changed successfully!");
    id=age+name.split("")[0]+name.split("")[2]+id.split("")[id.length()-1];
    System.out.println("Your pass ID has been changed for our security purpose\nNEW PASS ID: "+id);
  }
  //change age
  public void changeAge(int age){
    this.age=age;
    System.out.println("Age updated successfully!");
    id=age+name.split("")[0]+name.split("")[2]+id.split("")[id.length()-1];
    System.out.println("Your pass ID has been changed for our security purpose\nPASS ID: "+id);
  }
}

class Librarian extends User {
  //Initializing librarian details
  public Librarian(String librarianName,int age,String id) {
    super(librarianName,age,id);
  }
  //adding book to library
  public void addBookToLibrary(Book book, Library library) {
    library.addBook(book);
    System.out.println("Book added successfully!");
  }
  //stockCheck
  public void stockCheck(Library library) {
    library.displayBooks();
  }
  //Restock all
  public void restockBooks(Library library){
    library.restockBooks();
    System.out.println("\nAll books have been restocked\n");
  }
  //Restock specific book
  public void restockBooks(Book book,Library library){
    if(library.restockBooks(book)==true){
      library.restockBooks(book);
      System.out.println("\nThe given book is restocked\n");
    }
    else{
      System.out.println("\nThe given book is already in required stock\n");
    }
  }
  //Display all users
  public void displayUserDetails(Library library){
    library.userDetails();
  }
  //Display specific user details
  public void displayUserDetails(String id,Library library){
    library.userDetails(id);
  }
  //Remove user
  public void removeLibraryUsers(String id,Library library){
    if(library.removeUser(id)==true){
      library.removeUser(id);
      System.out.println("User removed successfully!");
    }
    else{
      System.out.println("Sorry! No such user is found");
    }
  }
}

class LibraryUsers extends User{
  private ArrayList<Book> borrowedBooks=new ArrayList<Book>();
  private ArrayList<HashMap<String,LocalDateTime>> borrowedBooksDueDateAndPenaltyCalc=new ArrayList<HashMap<String,LocalDateTime>>();
  private HashMap<String,LocalDateTime> penaltyCalc;
  private LocalDateTime currTime;
  private LocalDateTime dueDate;
  private int penalty=0;

  public LibraryUsers(String username,int age,String id){
    super(username, age, id);
  }

  //Borrow book
  public void borowBook(Book book){
    if((book.getAvailability()>0)&&(borrowedBooks.size()<=10)&&(!borrowedBooks.contains(book))&& (borrowedBooksDueDateAndPenaltyCalc.isEmpty()==true? true: !(borrowedBooksDueDateAndPenaltyCalc.get(0).get("TakenDate")).isAfter(borrowedBooksDueDateAndPenaltyCalc.get(0).get("DueDate")))){
      penaltyCalc=new HashMap<>();
      currTime=LocalDateTime.now();
      dueDate=currTime.plusDays(15);
      penaltyCalc.put("TakenDate",currTime);
      penaltyCalc.put("DueDate",dueDate);
      borrowedBooksDueDateAndPenaltyCalc.add(penaltyCalc);
      book.updateAvailability(-1);
      borrowedBooks.add(book);
      System.out.println("\nTitle: "+book.getTitle()+"\nAuthor: "+book.getAuthor()+"\nISBN: "+book.getISBN()+"\nDue Date: "+dueDate+"\n");
      System.out.println("Book borrowed successfully!");
    }
    else if (borrowedBooks.contains(book)) {
      System.out.println("You have already borrowed that book.");
    }
    else if(borrowedBooks.size()>10){
      System.out.println("Apologies, limit is 10 books.");
    }
    else if(book.getAvailability()<=0){
      System.out.println("Sorry! Out of stock");
    }
    else{
      System.out.println("Sorry! you have penalty, please pay that first");
    }
  }
  //return book
  public void returnBook(Book book){
    if(borrowedBooks.contains(book)&&(!(borrowedBooksDueDateAndPenaltyCalc.get(0).get("TakenDate")).isAfter(borrowedBooksDueDateAndPenaltyCalc.get(0).get("DueDate")))){
      book.updateAvailability(1);
      borrowedBooksDueDateAndPenaltyCalc.remove(borrowedBooks.indexOf(book));
      borrowedBooks.remove(book);
      System.out.println("\nTitle: "+book.getTitle()+"\nAuthor: "+book.getAuthor()+"\nISBN: "+book.getISBN()+"\n");
      System.out.println("Book returned successfully!");
    }
    else if(!borrowedBooks.contains(book)){
      System.out.println("Sorry! You don't have that book.");
    }
    else{
      System.out.println("Apologies, but there's a penalty, kindly settle the amount.");
    }
  }
  //Getter methods
  public ArrayList<Book> getBorrowedBooks(){
    return borrowedBooks;
  }
  public ArrayList<HashMap<String,LocalDateTime>> getBorrowedBooksDueDateAndPenaltyCalc(){
    return borrowedBooksDueDateAndPenaltyCalc;
  }
  //Display borrowed books
  public void displayBorrowedBooks(){
    if(borrowedBooks.isEmpty()){
      System.out.println("You haven't borrowed any book");
    }
    else{
      for(Book book:borrowedBooks){
        System.out.println("\nTitle: "+book.getTitle()+"\nAuthor: "+book.getAuthor()+"\nISBN: "+book.getISBN()+"\n-------------------------------\n");
      }
    }
  }
  //Calculate penalty
  public void calcPenalty(){
    if(penalty==0){
      System.out.println("Glad! you don't have any penalty");
    }
    else{
      for(int i=0;i<borrowedBooksDueDateAndPenaltyCalc.size();i++){
        currTime=borrowedBooksDueDateAndPenaltyCalc.get(i).get("TakenDate");
        dueDate=borrowedBooksDueDateAndPenaltyCalc.get(i).get("DueDate");
        if(currTime.isAfter(dueDate)){
          penalty+=currTime.compareTo(dueDate)*10;
        }
      }
      System.out.println("You had a penalty of "+penalty+" rupees.");
    }
  }
  //Pay penalty
  public void payPenalty(){
    if(penalty==0){
      System.out.println("Glad! you don't have any penalty");
    }
    else{
      penalty=0;
      for(int i=0;i<borrowedBooksDueDateAndPenaltyCalc.size();i++){
        currTime=borrowedBooksDueDateAndPenaltyCalc.get(i).get("TakenDate");
        dueDate=borrowedBooksDueDateAndPenaltyCalc.get(i).get("DueDate");
        if(currTime.isAfter(dueDate)){
          dueDate=currTime;
        }
      }
      System.out.println("Glad! your penalty has been paid");
    }
  } 
}
