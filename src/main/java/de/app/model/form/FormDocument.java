package de.app.model.form;

public class FormDocument {

	private String documentOwner;
	private String documentOwnerId;
	
	private String documentName;
	private String documentId;
	public String getDocumentOwner() {
		return documentOwner;
	}
	public void setDocumentOwner(String documentOwner) {
		this.documentOwner = documentOwner;
	}
	public String getDocumentOwnerId() {
		return documentOwnerId;
	}
	public void setDocumentOwnerId(String documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public String toString() {
		return "FormDocument [documentOwner=" + documentOwner + ", documentOwnerId=" + documentOwnerId
				+ ", documentName=" + documentName + ", documentId=" + documentId + "]";
	}

}
