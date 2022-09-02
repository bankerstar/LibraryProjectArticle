package maktab82.wh5;

import maktab82.wh5.entity.Article;
import maktab82.wh5.entity.Users;
import maktab82.wh5.repository.ArticleRepository;
import maktab82.wh5.repository.UserRepository;
import maktab82.wh5.util.ApplicationConstant;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = ApplicationConstant.getScanner();
    private static UserRepository userRepository = new UserRepository();
    private static ArticleRepository articleRepository = new ArticleRepository();
    private static Users user;

    enum MenuOptionOne {
        LOGIN, REGISTER, ARTICLES, SIGN_OUT_USER, EXIT;

    }

    enum MenuOptionTwo {
        CREATE_ARTICLE, EDIT_ARTICLE, DELETE_ARTICLE, SHOW_ARTICLE, PASSWORD, MENU_ONE, EXIT;
    }

    enum MenuOptionEdit {
        TITLE, CREATE_DATE, PUBLISH;
    }

    enum MenuOptionDelete {
        ID_OR_NAME;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("\n------ Welcome To Article Web Site ------");
        MenuOne();
    }

    public static void MenuOne() throws SQLException {
        if (user != null)
            System.out.println("\n------- You are Signed In as " + user.getUsername() + "----------" + "");
        else
            System.out.println("-------------------WEB SITE LIBRARY ARTICLES--------------------");
        System.out.println("Press 1 >> login");
        System.out.println("Press 2 >> register as a new user");
        System.out.println("Press 3 >> show the Published Articles");
        System.out.println("Press 4 >> Sign Out user");
        System.out.println("Press 5 >> Exit");
        System.out.println("------------------------------------------------------------------");

        int value = Integer.parseInt(scanner.nextLine()) - 1;
        MenuOptionOne nextStep = MenuOptionOne.values()[value];
        switch (nextStep) {
            case LOGIN:
                if (login()) MenuTwo();
                else
                    MenuOne();
                break;
            case REGISTER:
                if (register())
                    MenuTwo();
                else
                    MenuOne();
                break;
            case ARTICLES:
                showPublishedArticles();
                findArticleById();
                MenuOne();
                break;
            case SIGN_OUT_USER:
                signOut();
                MenuOne();
                break;
            case EXIT:
                System.out.println("Are you sure you want to Exit? yes/no");
                if (scanner.nextLine().equals("no"))
                    MenuOne();
                else
                    System.out.println("Hope to see you soon ;)");
                ApplicationConstant.closeConnection();
                break;
            default:
                ApplicationConstant.closeConnection();
                break;
        }
    }

    public static void MenuTwo() throws SQLException {
        System.out.println("\n------ You are login In as " + user.getUsername() + "---------");
        System.out.println("Press number 1 >>* Add a New Article");
        System.out.println("Press number 2 >>* Edit your Articles");
        System.out.println("Press number 3 >>* Delete your Articles ");
        System.out.println("Press number 4 >>* Show All your Articles ");
        System.out.println("Press number 5 >>* Change Password ");
        System.out.println("Press number 6 >>* Previous Menu");
        System.out.println("Press number 7 >>* Exit Program");
        System.out.println("---------------------------------------");

        int value = Integer.parseInt(scanner.nextLine()) - 1;
        MenuOptionTwo nextStep = MenuOptionTwo.values()[value];

        switch (nextStep) {
            case CREATE_ARTICLE:
                createArticle();
                MenuTwo();
                break;
            case EDIT_ARTICLE:
                editArticleByUserID();
                MenuTwo();
                break;
            case DELETE_ARTICLE:
                deleteArticleByUserID();
                MenuTwo();
                break;
            case SHOW_ARTICLE:
                articlesByUserID();
                MenuTwo();
                break;
            case PASSWORD:
                changePassword();
                MenuTwo();
                break;
            case MENU_ONE:
                MenuOne();
                break;
            case EXIT:
                System.out.println("Are you sure you want to Exit? yes/no");
                if (scanner.nextLine().equals("no"))
                    MenuOne();
                else {
                    System.out.println("Try again !");
                    ApplicationConstant.closeConnection();
                }
                break;
            default:
                ApplicationConstant.closeConnection();
                break;
        }
    }

    public static void createArticle() throws SQLException {
        System.out.println("------------ Create Article -----------");
        System.out.print("Enter Article Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Brief: ");
        String brief = scanner.nextLine();

        System.out.print("Enter Quit");
        String QuitChecker = scanner.nextLine();
        String content = "";
        while (!QuitChecker.equals("Quit")) {
            content = content.concat(QuitChecker);
            QuitChecker = scanner.nextLine();
        }

        System.out.print("Enter Create Date ");
        Date newDate = Date.valueOf(scanner.next());
        scanner.nextLine();

        System.out.println("Would you like to Publish your Article yes/no?");
        String answer = scanner.nextLine();
        boolean isPublished = answer.equals("yes");

        Article article = new Article(title, brief, content, newDate, isPublished, user.getId());
        int articleID = articleRepository.createNewArticle(article);
        article.setId(articleID);
        System.out.println("Article ID " + article.getId() + "Add Successful");
        System.out.println("---------------------------------------------");
    }

    public static void editArticleByUserID() throws SQLException {
        System.out.println("------------------Editing Article ---------------");
        System.out.println("edit Article id :");
        int articleID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.findArticleById(articleID);
        if (article != null && article.getUser_id() == user.getId()) {
            String name = userRepository.findUserByID(article.getUser_id());
            System.out.println(article.getId() + "and" + article.getTitle());
            System.out.println("  Author: " + name + " Date: " + article.getCreateDate());
            System.out.println("  Brief: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());

            System.out.println("Press number 1 >>* Edit Title");
            System.out.println("Press number 2 >>* Edit Create Date");
            System.out.println("Press number 3 >>* Edit Publish Status");

            int value = Integer.parseInt(scanner.nextLine()) - 1;
            MenuOptionEdit editVal = MenuOptionEdit.values()[value];

            switch (editVal) {
                case TITLE: {
                    System.out.println("Enter the new Title :");
                    String newTitle = scanner.nextLine();
                    articleRepository.editTitle(article.getId(), newTitle);
                    System.out.println("Title Article edited successfully");
                }
                case CREATE_DATE: {
                    System.out.println("Enter the Create Date :");
                    Date newDate = Date.valueOf(scanner.nextLine());
                    articleRepository.editCreateDate(article.getId(), newDate);
                    System.out.println("Create Date edited successfully");
                }
                case PUBLISH: {
                    System.out.println("press number 1 to Publish,press number 0 to un publish the article : ");
                    boolean isPublished = Integer.parseInt(scanner.nextLine()) == 1;
                    articleRepository.editPublishStatus(article.getId(), isPublished);
                    System.out.println("Publish updated successfully");
                }
                default:
                    System.out.println("nothing edited");
            }
        } else
            System.out.println("Incorrect ID");

        System.out.println("----------------------------------------------------------");

    }

    public static void deleteArticleByUserID() throws SQLException {
        System.out.println("------------------Deleted Article ---------------");
        System.out.println("delete Article id :");
        int articleID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.findArticleById(articleID);
        if (article != null && article.getUser_id() == user.getId()) {
            String name = userRepository.findUserByID(article.getUser_id());
            System.out.println(article.getId() + "and" + article.getTitle());
            System.out.println("  Author: " + name + " Date: " + article.getCreateDate());
            System.out.println("  Brief: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());

            System.out.println("Press number 1 >>* Delete Article By Id or name ");

            int value = Integer.parseInt(scanner.nextLine()) - 1;
            MenuOptionDelete deleteVal = MenuOptionDelete.values()[value];

            switch (deleteVal) {
                case ID_OR_NAME: {
                    System.out.println("Enter the Article  Id  or name:");
                    String newTitle = scanner.nextLine();
                    articleRepository.deleteArticle(article.getId(), newTitle);
                    System.out.println(" Article deleted successfully");
                }
                default:
                    System.out.println("nothing deleted");
            }
        } else
            System.out.println("Incorrect ID");

        System.out.println("----------------------------------------------------------");

    }

    public static void articlesByUserID() throws SQLException {
        System.out.println("-----------Show All of Your Articles --------");
        List<Article> currentUserArticleList;
        currentUserArticleList = articleRepository.articlesByUserID(user.getId());
        if (currentUserArticleList.size() == 0)
            System.out.println("You haven't published any Articles yet");
        for (Article article : currentUserArticleList) {
            System.out.println("-----------------------------------------");
            System.out.println("Article ID " + article.getId());
            System.out.println("  Title: " + article.getTitle());
            System.out.println("  Brief: " + article.getBrief());
            System.out.println("-----------------------------------------");
        }
        System.out.println("------------------------------------------");
    }

    public static void findArticleById() throws SQLException {
        System.out.println("--------- View Article -------");
        System.out.println("Enter Article ID to show the Content: ");
        int ID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.findArticleById(ID);
        if (article != null && article.isPublished()) {
            String name = userRepository.findUserByID(article.getUser_id());
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + name + " Date: " + article.getCreateDate());
            System.out.println("  Brief: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());
        } else
            System.out.println("Incorrect Article ID");
        System.out.println("-------------------------------");
    }

    public static void showPublishedArticles() throws SQLException {
        System.out.println("----- Published Articles -----");
        List<Article> articleList;
        articleList = articleRepository.allArticles();
        if (articleList.size() == 0)
            System.out.println("There is no Articles yet");
        for (Article article : articleList) {
            if (article.isPublished()) {
                System.out.println("-----------------------------------------------");
                System.out.println("Article ID " + article.getId());
                System.out.println("  Title: " + article.getTitle());
                System.out.println("  Brief: " + article.getBrief());
                System.out.println("-----------------------------------------------");
            }
        }
        System.out.println("-----------------------------------------------");
    }

    public static boolean changePassword() throws SQLException {
        System.out.println("---------- Change Password -----");
        System.out.println("Enter your current Password");
        String oldPass = scanner.nextLine();
        if (userRepository.checkedPassword(user.getId(), oldPass)) {
            System.out.println("Enter new Password :");
            String newPass = scanner.nextLine();
            if (userRepository.changedPassword(user.getId(), newPass)) {
                user.setPassword(newPass);
                System.out.println("Password changed successfully!");
                System.out.println("------------------------------");
                return true;
            }
            System.out.println("password is not correct , try again later");
            System.out.println("-----------------------------------");
            return false;
        }
        System.out.println("Password Is Wrong !");
        System.out.println("---------------------------------------");
        return false;
    }

    public static boolean register() throws SQLException {
        System.out.println("------------ Register User ------------");
        if (user == null) {
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter National Code: ");
            String nationalCode = scanner.next();
            user = userRepository.RegisterUserPassword(username, nationalCode);
            if (user != null) {
                System.out.println("Register Is complete");
                System.out.println(user.getUsername() + " Your Password changed to your National Code");
                System.out.println("---------------------------------------");
                return true;
            }
            System.out.println("This Username already exists");
            System.out.println("---------------------------------------");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign Up as another User? Sign Out first");
        System.out.println("---------------------------------------------");
        return false;
    }

    public static boolean login() throws SQLException {
        System.out.println("------------ Login User -------------------");
        if (user == null) {
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter Password: ");
            String password = scanner.next();
            scanner.nextLine();
            user = userRepository.loginByUserPassword(username, password);
            if (user != null) {
                System.out.println("You have been successfully login in site !");
                System.out.println("-------------------------------------");
                return true;
            }
            System.out.println("-------------------Sign in Failed-----------------");
            System.out.println("Your Username or Password is incorrect, try again ....");
            System.out.println("--------------------------------------------------");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign in as another User? Sign Out first");
        System.out.println("------------------------------------------------------");
        return false;
    }

    public static void signOut() {
        user = null;
        System.out.println("------------ Sign Out  User---------");
        System.out.println("Successfully signed out");
        System.out.println("------------------------------------------");
    }
}