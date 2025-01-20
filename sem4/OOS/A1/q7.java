import java.util.ArrayList;
import java.util.List;

abstract class Publication {
    private int noOfPages;
    private double price;
    private String publisherName;

    public Publication(int noOfPages, double price, String publisherName) {
        this.noOfPages = noOfPages;
        this.price = price;
        this.publisherName = publisherName;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public abstract void printDetails();
}

class Book extends Publication {
    private String author;

    public Book(int noOfPages, double price, String publisherName, String author) {
        super(noOfPages, price, publisherName);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void printDetails() {
        System.out.println("Book:");
        System.out.println("  Pages: " + getNoOfPages());
        System.out.println("  Price: $" + getPrice());
        System.out.println("  Publisher: " + getPublisherName());
        System.out.println("  Author: " + author);
    }
}

class Journal extends Publication {
    private String issue;

    public Journal(int noOfPages, double price, String publisherName, String issue) {
        super(noOfPages, price, publisherName);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void printDetails() {
        System.out.println("Journal:");
        System.out.println("  Pages: " + getNoOfPages());
        System.out.println("  Price: $" + getPrice());
        System.out.println("  Publisher: " + getPublisherName());
        System.out.println("  Issue: " + issue);
    }
}

class Library {
    private List<Publication> publications;

    public Library() {
        publications = new ArrayList<>();
    }

    public void addPublication(Publication publication) {
        publications.add(publication);
    }

    public void printAllPublications() {
        for (Publication publication : publications) {
            publication.printDetails();
            System.out.println();
        }
    }
}

class Demo {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book(300, 19.99, "Penguin Books", "Author A");
        Book book2 = new Book(450, 25.50, "HarperCollins", "Author B");
        Book book3 = new Book(150, 10.75, "Random House", "Author C");

        Journal journal1 = new Journal(100, 5.99, "Science Direct", "Issue 1");
        Journal journal2 = new Journal(200, 7.99, "Nature", "Issue 2");

        library.addPublication(book1);
        library.addPublication(book2);
        library.addPublication(book3);
        library.addPublication(journal1);
        library.addPublication(journal2);

        library.printAllPublications();
    }
}
