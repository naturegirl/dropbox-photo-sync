package com.naturegirl.photodrops;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class PhotoSelectActivity extends Activity {
	
	private GalleryAdapter adapter;
	private Handler handler;
	private ImageLoader imageLoader;
	
	private OnItemClickListener mItemMulClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		mItemMulClickListener = new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				adapter.changeSelection(v, position);
			}
		};
		
		Button okButton = (Button) findViewById(R.id.gallery_ok_button);
        okButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	okButtonClicked();
            }
        });

        initImageLoader();
		init();

	}	
	
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
	
    private void init() {
		handler = new Handler();
		GridView gridGallery = (GridView) findViewById(R.id.gridGallery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new GalleryAdapter(getApplicationContext(), imageLoader);

		findViewById(R.id.llBottomContainer).setVisibility(View.VISIBLE);
		gridGallery.setOnItemClickListener(mItemMulClickListener);
		adapter.setMultiplePick(true);
		
		gridGallery.setAdapter(adapter);


		new Thread() {
			public void run() {
				Looper.prepare();
				handler.post(new Runnable() {
					public void run() {
						adapter.addAll(getGalleryPhotos());
					}
				});
				Looper.loop();
			};
		}.start();
    }
    
    private void okButtonClicked() {
    	ArrayList<PhotoItem> selected = adapter.getSelected();
		String[] photoPaths = new String[selected.size()];
		for (int i = 0; i < photoPaths.length; i++) {
			photoPaths[i] = selected.get(i).getPath();
		}
		Intent data = new Intent().putExtra("photo_paths", photoPaths);
		setResult(RESULT_OK, data);		
		finish();
    }
    
	private ArrayList<PhotoItem> getGalleryPhotos() {
		ArrayList<PhotoItem> galleryList = new ArrayList<PhotoItem>();

		try {
			final String[] columns = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID };
			final String orderBy = MediaStore.Images.Media._ID;

			@SuppressWarnings("deprecation")
			Cursor imagecursor = managedQuery(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
					null, null, orderBy);
			if (imagecursor != null && imagecursor.getCount() > 0) {

				while (imagecursor.moveToNext()) {
					
					int dataColumnIndex = imagecursor
							.getColumnIndex(MediaStore.Images.Media.DATA);

					String sdcardPath = imagecursor.getString(dataColumnIndex);
					PhotoItem item = new PhotoItem(sdcardPath);

					galleryList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        // show newest photo at beginning of the list
		Collections.reverse(galleryList);
        return galleryList;
	}    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
