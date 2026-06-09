package com.ai.projects.cooked.model.geminidto.response;

import java.util.List;

public record GeminiContentResponse(
	List<GeminiTextPart> parts
) {}
