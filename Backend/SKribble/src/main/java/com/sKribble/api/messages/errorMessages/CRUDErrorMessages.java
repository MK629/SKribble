package com.sKribble.api.messages.errorMessages;

public class CRUDErrorMessages {
	
	public static final String REGISTER_FAILED = "User registration failed.";

	public static final String DUPLICATE_CHAPTER = "This chapter number already exists.";

	public static final String DUPLICATE_CHARACTER = "Another character with the same name already exists.";

	public static final String ILLOGICAL_NULL_ERROR = "Illogical error. Processing result returned null. Please contact the developer.";

	public static final String PERSISTENCE_FAILED = "An error occured while processing.";

	public static final String ASSET_NOT_OWNED = "Incorrect ownership for the selected project.";

	public static final String PROJECT_NOT_FOUND = "Project not found. It is either deleted or non-existent.";

	public static final String CHAPTER_NOT_FOUND = "Chapter does not exist.";
}
