package com.ai.projects.cooked.model.geminidto.response;

import java.util.List;

public record GeminiResponse(
	List<Candidate> candidates
) {}
