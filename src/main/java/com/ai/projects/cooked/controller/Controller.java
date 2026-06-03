package com.ai.projects.cooked.controller;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.projects.cooked.service.ChatService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

}
