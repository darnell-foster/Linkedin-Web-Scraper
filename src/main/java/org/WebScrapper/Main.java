package org.WebScrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // creates a constructor of the Scanner class having System.inM as an argument. It means it is going to read from the standard input stream of the program.
        Scanner sc = new Scanner(System.in);

        String location = null;
        String keyWords = null;

        //Takes in user input for KeyWords and Location, then replaces all char in string like spaces and commas with the Hexidecimal ASCII value
        do{
            System.out.print("Enter a location(or \"none\"): ");
            location = sc.nextLine();//reads string
        }while(location == null || location == " " && location.equalsIgnoreCase("none") == false);
        if (location != "none") {
            location = location.replaceAll(" ", "%20");
            location = location.replaceAll(",", "%2C");
            System.out.println("User input: " + location);
        }
        do{
            System.out.print("Enter a keyWords(or \"none\"): ");
            keyWords = sc.nextLine();//reads string
        }while(keyWords == null || keyWords == " " && keyWords.equalsIgnoreCase("none") == false);
        if (location != "none") {
            keyWords = keyWords.replaceAll(" ", "%20");
            keyWords = keyWords.replaceAll(",", "%2C");
            System.out.println("User input: " + keyWords);
        }
        //Do until user quits
        try {

            //Default search is the job location and keywords, but if you entered none on any parameters it changes the search
            String webPageLink = ("https://www.linkedin.com/jobs/search/?keywords=" + keyWords + "&location=" + location + "&geoId=&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0");
            if (location.equalsIgnoreCase("none") == true && keyWords.equalsIgnoreCase("none") == true) {
                webPageLink = ("https://ca.linkedin.com/jobs/linkedin-jobs?position=1&pageNum=0");
            } else if (location.equalsIgnoreCase("none") == true) {
                webPageLink = ("https://www.linkedin.com/jobs/search/?keywords=" + keyWords + "&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0");
            } else if (keyWords.equalsIgnoreCase("none") == true) {
                webPageLink = ("https://www.linkedin.com/jobs/search/?&location=" + location + "&geoId=&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0");
            }
            
            // Here we create a document object and use JSoup to fetch the website
            //parsing for a website you use ".connect" from a string and for a document use ".parse"
            Document doc = Jsoup.connect(webPageLink).get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());
            System.out.println("Displaying Page 1 (for more pages click webapage link)");
            System.out.println(webPageLink);
            // Get the list of repositories

            // creates an element object which is a list of other elements
            //Creates an Element list object by Selecting the html element that is a tag with the class name "base-serp-page__content"
            Elements div = doc.select("div.base-serp-page__content");

            //Creates an Element list object by selecting the html tabs section, ul, li from the object div
            Elements jobsList = div.select("section ul li");

            //System.out.println(jobsList.size());
            System.out.println("--------------------JOBS LIST------------------");
            //for each Element(the jobs) inside the Element list object do the following
            for (int i = 0; i < jobsList.size(); i++) {
                //grab element(job) at position i from object element list(job_list), from that grab text from the element inside class ___
                String company = jobsList.get(i).getElementsByClass("base-search-card__subtitle").text();
                String jobTitle = jobsList.get(i).getElementsByClass("base-search-card__title").text();

                //To get the value of an attribute ours is "href" for links, we use "element".attr("the attribute you want")
                String url = jobsList.get(i).getElementsByClass("base-card__full-link").attr("href");
                String local = jobsList.get(i).getElementsByClass("job-search-card__location").text();

                System.out.println("Company: " + company);
                System.out.println("JobTitle: " + jobTitle);
                System.out.println("Location: " + local);
                System.out.println("Apply Here: " + url);
                System.out.println();
            }

            // In case of any IO errors, we want the messages written to the console
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}