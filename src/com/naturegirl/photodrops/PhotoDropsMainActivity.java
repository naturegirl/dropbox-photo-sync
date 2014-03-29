package com.naturegirl.photodrops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class PhotoDropsMainActivity extends ActionBarActivity {

	private DbManager dbManager;
	private String photoPaths[];
	
	public static int LINK_DB_REQUEST = 100;
	private static int SELECT_PHOTO_REQUEST = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Button connectButton = (Button) findViewById(R.id.connect_db_button);
        Button selectPicsButton = (Button) findViewById(R.id.select_photos_button);
        Button syncButton = (Button) findViewById(R.id.sync_db_button);
        
        Button testButton = (Button) findViewById(R.id.test_button);
        
        connectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	onClickLinkToDropbox();
            }
        });
        
        selectPicsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onClickStartPhotoSelect();
			}
        });
        
        syncButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		onClickSyncDropbox();
        	}
        });
        
        testButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	testing();
            }        	
        });
		dbManager = DbManager.getInstance(this, getApplicationContext());
	}
	
    private void onClickLinkToDropbox() {
    	dbManager.linkToDropbox();
    	Toast.makeText(getApplicationContext(), "linked with dropbox!", 
                Toast.LENGTH_SHORT).show();
    }
    
    private void onClickStartPhotoSelect() {
    	Intent intent = new Intent(this, PhotoSelectActivity.class);
    	startActivityForResult(intent, SELECT_PHOTO_REQUEST);
    }
    
    private void onClickSyncDropbox() {
    	if (photoPaths == null || photoPaths.length == 0) {
    		String msg = "please select photos first.";
        	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        	return;
    	}
    	dbManager.syncFiles(photoPaths);
    }
    
    private void testing() {
    	dbManager.testing();
    }
    
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == SELECT_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
			photoPaths = data.getStringArrayExtra("photo_paths");
			for (String path : photoPaths) {
				Log.w("swifflet", path);
			}
		}
		
		if (requestCode == LINK_DB_REQUEST && resultCode != Activity.RESULT_OK) {
			String msg = "Link to Dropbox failed or was cancelled";
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
