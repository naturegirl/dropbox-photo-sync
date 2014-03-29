package com.naturegirl.photodrops;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxException.Unauthorized;


public class DbManager {
	
	private static DbManager manager = null;
	
    private final static String appKey = "9ru8m7tezw2bg1t";
    private final static String appSecret = "1o5tgy64sn9c91p";
    
    private final static String directory = "Photos/PhotoDrops/";
    
    private Context context;
    private Activity activity;
	
    private DbxAccountManager mDbxAcctMgr;
    
	private DbManager(Activity act, Context ctx) {
		this.context = ctx;
		this.activity = act;
		mDbxAcctMgr = DbxAccountManager.getInstance(context, appKey, appSecret);
	}
	
	public static DbManager getInstance(Activity act, Context ctx) {
		if (manager == null) {
			manager = new DbManager(act, ctx);
		}
		return manager;
	}
	
	public void linkToDropbox() {
		if (!mDbxAcctMgr.hasLinkedAccount()) {
			mDbxAcctMgr.startLink(activity, PhotoDropsMainActivity.LINK_DB_REQUEST);
		}
	}
	
	public void syncFiles(String photoPaths[]) {
		// move to init?
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
			int cnt = 1;		
			for (String imgPath : photoPaths) {
				String filename = "img"+cnt+".jpg";
				DbxPath dbPath = new DbxPath(DbxPath.ROOT, directory+filename);
				DbxFile dbFile;
				
				File imgFile = new File(imgPath);
				
				if (dbxFs.exists(dbPath))
					dbFile = dbxFs.open(dbPath);
				else
					dbFile = dbxFs.create(dbPath);
				dbFile.writeFromExistingFile(imgFile, false);
				dbFile.close();
				cnt++;
			}
		} catch (DbxException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void testing() {
        try {
            final String TEST_DATA = "Goodbye Dropbox";
            final String TEST_FILE_NAME = "blablabla.txt";
            
            DbxPath testPath = new DbxPath(DbxPath.ROOT, "Public/Piqular/"+TEST_FILE_NAME);

            // Create DbxFileSystem for synchronized file access.
            DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());

            // Create new file or overwrite existing file
            DbxFile testFile;
            if (dbxFs.exists(testPath)) {
                testFile = dbxFs.open(testPath);
            } else {
            	testFile = dbxFs.create(testPath);
            }
            testFile.writeString(TEST_DATA);
            testFile.close();
            Log.w("swifflet", "\nCreated new file '" + testPath + "'.\n");            	
        } catch (DbxException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
