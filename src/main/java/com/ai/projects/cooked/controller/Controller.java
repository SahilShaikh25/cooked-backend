package com.ai.projects.cooked.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.projects.cooked.service.ChatService;

@RestController
@CrossOrigin(origins = "https://cooked-io.netlify.app/")
@RequestMapping("/chat")
public class Controller {
	
	private ChatService chatService;
	
	public Controller(ChatService chatService) {
		this.chatService = chatService;
	}
		
	@PostMapping("/prompt")
	public String getResponse(
			@RequestParam("profile") MultipartFile profile,
			@RequestParam("roastLevel") String roastLevel) 
			throws Exception{
		
		PDDocument document = Loader.loadPDF(profile.getBytes());
		PDFTextStripper textStripper = new PDFTextStripper();
		String text = textStripper.getText(document);
		
		text = text.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", "");
        text = text.replaceAll("\\+?\\d[\\d\\s()-]{7,}\\d", "");
        text = text.replaceAll(
                "(https?://)?(www\\.)?linkedin\\.com/[A-Za-z0-9/_-]+",
                ""
        );
        document.close();
		String response= this.chatService.getResponse(text, roastLevel);
		
		return response;
	}
	
	// temp
	
	@RequestMapping("/test")
	public void runTest() {
		try {
            printSecretMessage("https://docs.google.com/document/d/e/2PACX-1vTMOmshQe8YvaRXi6gEPKKlsC6UpFJSMAk4mQjLm_u1gmHdVVTaeh7nBNFBRlui0sTZ-snGwZM4DBCT/pub");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void printSecretMessage(String docUrl) throws IOException {
        // Fetch the published Google Doc page
        Document doc = Jsoup.connect(docUrl).get();

        List<Entry> entries = new ArrayList<>();

        // Read table rows
        Elements rows = doc.select("tr");

        for (Element row : rows) {
            Elements cells = row.select("td, th");

            if (cells.size() != 3) {
                continue;
            }

            try {
                String character = cells.get(0).text();
                int x = Integer.parseInt(cells.get(1).text().trim());
                int y = Integer.parseInt(cells.get(2).text().trim());

                entries.add(new Entry(character, x, y));
            } catch (NumberFormatException e) {
                // Skip header row
            }
        }

        if (entries.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        int maxX = 0;
        int maxY = 0;

        for (Entry entry : entries) {
            maxX = Math.max(maxX, entry.x);
            maxY = Math.max(maxY, entry.y);
        }

        char[][] grid = new char[maxY + 1][maxX + 1];

        // Fill grid with spaces
        for (int i = 0; i <= maxY; i++) {
            Arrays.fill(grid[i], ' ');
        }

        // Place characters
        for (Entry entry : entries) {
            grid[entry.y][entry.x] =
                    entry.character.isEmpty() ? ' ' : entry.character.charAt(0);
        }

        // Print grid
        for (int y = 0; y <= maxY; y++) {
            System.out.println(new String(grid[y]));
        }

        /*
         * If the output appears upside down, use:
         *
         * for (int y = maxY; y >= 0; y--) {
         *     System.out.println(new String(grid[y]));
         * }
         */
    }

    static class Entry {
        String character;
        int x;
        int y;

        Entry(String character, int x, int y) {
            this.character = character;
            this.x = x;
            this.y = y;
        }
    }
    
    

}
