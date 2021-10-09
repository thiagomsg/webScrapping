package br.com.ucsal.web_socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class Application implements Runnable {

	private String latestNews = "";
	
	public static void main(String[] args) throws Exception {

		Runnable runnable = new Application();
		Thread thread = new Thread(runnable);
		thread.start();

	}

	public void run() {
		try {
			webScrapping();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void webScrapping() throws Exception {
		while (true) {
			URL url = new URL("https://adrenaline.com.br/noticias");

			BufferedReader is = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = "";
			while ((line = is.readLine()) != null) {
				if (line.contains("<h2 class=\"post-h__content-title\">")) {

					int initialLeftover = "title\">".length();
					int initialPos = line.lastIndexOf("title\">") + initialLeftover;

					int finalLeftover = "</h2>".length();
					int finalPos = line.length() - finalLeftover;
					
					String news = line.substring(initialPos, finalPos);
					
					if(!news.equals(latestNews)) {
						System.out.println(news);
						latestNews = news;
					}
					

					break;
				}

			}
			
			Thread.sleep(1000*60*60);
		}
	}
}
