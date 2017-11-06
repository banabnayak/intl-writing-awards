package com.scholastic.intl.writingawards.form;

import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class UploadForm {

	public UploadForm() {
	}
	private String saveAs;
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	@FormParam("file")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}

	public String getSaveAs() {
		return saveAs;
	}

	@FormParam("saveAs")
	public void setSaveAs(String saveAs) {
		this.saveAs = saveAs;
	}	
}