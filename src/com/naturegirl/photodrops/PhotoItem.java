package com.naturegirl.photodrops;

public class PhotoItem {

	private String sdcardPath;
	public boolean isSeleted = false;

	public PhotoItem (String path) {
		sdcardPath = path;
	}
	
	public String getPath() {
		return sdcardPath;
	}
}
