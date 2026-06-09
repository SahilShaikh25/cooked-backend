package com.ai.projects.cooked.model.geminidto.request;

import java.util.List;

public record GeminiRequest(
	List<GeminiContent> contents
) {}
