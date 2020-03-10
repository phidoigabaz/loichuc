package com.smile.wish.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.smile.wish.canvas.MultiTouchController;

import java.util.ArrayList;

public class MultiImageTouchView extends RelativeLayout implements
		MultiTouchController.MultiTouchObjectCanvas<MultiImageTouchView.Img> {

	// private static final int[] IMAGES = { R.drawable.m74hubble,
	// R.drawable.catarina, R.drawable.tahiti, R.drawable.sunset,
	// R.drawable.lake };

	public static ArrayList<Img> mImages = new ArrayList<Img>();

	// --

	private MultiTouchController<Img> multiTouchController = new MultiTouchController<Img>(
			this);
	
	// --

	private MultiTouchController.PointInfo currTouchPoint = new MultiTouchController.PointInfo();

	private boolean mShowDebugInfo = false;

	private static final int UI_MODE_ROTATE = 1, UI_MODE_ANISOTROPIC_SCALE = 2;

	private int mUIMode = UI_MODE_ROTATE;

	private float displayWidth, displayHeight;

	private float minScale = 0.2f, maxScale = 6f;

	// --
	

	
	private Paint mLinePaintTouchPointCircle = new Paint();

	// ---------------------------------------------------------------------------------------------------

	public MultiImageTouchView(Context context) {
		this(context, null);
		//super(context);
	}
	
	public MultiImageTouchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MultiImageTouchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		//onCreateView();
	}

	private void init() {// Context context) {
		// Resources res = context.getResources();
		// for (int i = 0; i < IMAGES.length; i++)
		// mImages.add(new Img(IMAGES[i], res));

		mLinePaintTouchPointCircle.setColor(Color.YELLOW);
		mLinePaintTouchPointCircle.setStrokeWidth(5);
		mLinePaintTouchPointCircle.setStyle(Style.STROKE);
		mLinePaintTouchPointCircle.setAntiAlias(true);
		setBackgroundColor(Color.TRANSPARENT);
		
	}

	private void setContentView(int resource) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (inflater != null) {
			inflater.inflate(resource, this);
		}
	}
	/** Called by activity's onResume() method to load the images */
	public void loadImages(Drawable drawable) {// Context context) {
		mImages.add(new Img(drawable));
		if (displayWidth > 0 && displayHeight > 0) {
			int i = mImages.size() - 1;
			mImages.get(i).load();
		}
		invalidate();
	}

	/**
	 * Called by activity's onPause() method to free memory used for loading the
	 * images
	 */
	public void unloadImages() {
		mImages.clear();
//		int n = mImages.size();
//		for (int i = 0; i < n; i++)
//			mImages.get(i).unload();
	}

	// ---------------------------------------------------------------------------------------------------

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		Log.e("TUNT", "onDraw ");
		int n = mImages.size();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				mImages.get(i).draw(canvas);
			}
			if (mShowDebugInfo) {
				drawMultitouchDebugMarks(canvas);
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------

	public void trackballClicked() {
		mUIMode = (mUIMode + 1) % 3;
		invalidate();
	}

	private void drawMultitouchDebugMarks(Canvas canvas) {
		if (currTouchPoint.isDown()) {
			float[] xs = currTouchPoint.getXs();
			float[] ys = currTouchPoint.getYs();
			float[] pressures = currTouchPoint.getPressures();
			int numPoints = Math.min(currTouchPoint.getNumTouchPoints(), 2);
			for (int i = 0; i < numPoints; i++)
				canvas.drawCircle(xs[i], ys[i], 50 + pressures[i] * 80,
						mLinePaintTouchPointCircle);
			if (numPoints == 2)
				canvas.drawLine(xs[0], ys[0], xs[1], ys[1],
						mLinePaintTouchPointCircle);
		}
	}

	// ---------------------------------------------------------------------------------------------------

	/** Pass touch events to the MT controller */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return multiTouchController.onTouchEvent(event);	
	}

	/**
	 * Get the image that is under the single-touch point, or return null
	 * (canceling the drag op) if none
	 */
	public Img getDraggableObjectAtPoint(MultiTouchController.PointInfo pt) {
		float x = pt.getX(), y = pt.getY();
		int n = mImages.size();
		for (int i = n - 1; i >= 0; i--) {
			Img im = mImages.get(i);
			if (im.containsPoint(x, y)){
				//Img img_tg = im;
				//img_tg.drawable=img.drawable;
				/*if(multiTouchController.flagadjust){
				Drawable drawabledrawable = new BitmapDrawable(changepicture.doColorFilter(im.bitmap_img(),1, 0, 0));
				img_tg.drawable=drawabledrawable;
			
				}*/
				
				return im;
			}
				
		}
		return null;
	}
  /*  public void initadjust(){
    	
    }*/
    /*public void picture_adjust(Bitmap bitmap_hue) {
    	
    	//onCreateView();
    	bitmapPicture = new ArrayList<Bitmap>();
    	HomeActivity.mLayout_adjust.setVisibility(View.VISIBLE);
    	
        
        
		setLayout();
		selectpositioneffect=-1;
		mLayout_adjust.setVisibility(View.VISIBLE);
		Imageview_crop.setImageBitmap(bitmap_crop);
		//ImageView_picture.setEnabled(true);
    	View = mInflater.inflate(
				R.layout.item_hoizont_theme, null);
    	View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.adjust, null);
    	view.setVisibility(View.VISIBLE);
    	
    	bitmapPicture.add( changepicture.doColorFilter(bitmap_hue, 1, 1, 1));		
		bitmapPicture.add( changepicture.doColorFilter(bitmap_hue, 1, 0, 0));		
		bitmapPicture.add( changepicture.doColorFilter(bitmap_hue,  0, 1, 0));		
		bitmapPicture.add( changepicture.doColorFilter(bitmap_hue, 0, 0, 1));	
		bitmapPicture.add( changepicture.doColorFilter(bitmap_hue, 0.5, 0.5, 0.5));
		bitmapPicture.add( changepicture.doColorFilter(bitmap_hue, 1.5, 1.5, 1.5));
		bitmapPicture.add( changepicture.doGreyscale(bitmap_hue));
		Button Adjust_cancel = (Button) findViewById(R.id.adjust_Cancel);
		Button Adjust_save = (Button) findViewById(R.id.adjust_Save);
		Adjust_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ImageView_picture.setImageBitmap(bitmapall);
				//BitmapDrawable Drawable_theme = new BitmapDrawable(bitmap_theme);// hue anh nen o day
				//ImageView_theme.setBackgroundDrawable(Drawable_theme);
				Drawable drawable = new BitmapDrawable(bitmap_crop);
				mImageUserPicked.loadImages(drawable);	
				mLayout_adjust.setVisibility(View.GONE);
				HomeActivity.mLayout_adjust.setVisibility(View.INVISIBLE);
			}
			
		});
		Adjust_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				HomeActivity.mLayout_adjust.setVisibility(View.INVISIBLE);
				
				// TODO Auto-generated method stub
			if(picture_hue != null)
					bitmap_crop=picture_hue;			
				Imageview_crop.setEnabled(false);
				Drawable drawable = new BitmapDrawable(bitmap_crop);
				mImageUserPicked.loadImages(drawable);
				mLayout_adjust.setVisibility(View.GONE);
			}
			
		});
		imageAdapter_hue = new HorizontalImageAdapterEffect(_activity, bitmapPicture);	
		myPager_hue = (HorizontalListView) findViewById(R.id.panel_hue);
		myPager_hue.setAdapter(imageAdapter_hue);
		myPager_hue.setOnItemClickListener(new OnItemClickListener() {//truot chon nhanh o day
			@Override
			public void onItemClick(AdapterView<?> adapter,
					View convertView, int position, long id) {
				
				
				
				if(bitmap_crop != null){
					selectpositioneffect=position;

					if (selectHolderEffect != null) {
						selectHolderEffect.item_check_effect.setVisibility(View.INVISIBLE);					}	
					selectpositiontheme=position;
					final HorizontallmageAdapterEffect.ViewHolder holderkk = (HorizontallmageAdapterEffect.ViewHolder) convertView
							.getTag();	
					holderkk.item_check_effect.setVisibility(View.VISIBLE);
					selectHolderEffect = holderkk;	
					
					picture_hue=changepropertypicture(bitmap_crop,position);						
					Imageview_crop.setImageBitmap(picture_hue);
				}
					
			}			
		});	
	
	}*/
	/**
	 * Select an object for dragging. Called whenever an object is found to be
	 * under the point (non-null is returned by getDraggableObjectAtPoint()) and
	 * a drag operation is starting. Called with null when drag op ends.
	 */
//	public static Drawable drawabledrawable ;
	public void selectObject(Img img, MultiTouchController.PointInfo touchPoint) {
		currTouchPoint.set(touchPoint);
		if (img != null) {
			// Move image to the top of the stack when selected
			mImages.remove(img);
			mImages.add(img);

		} else {
			// Called with img == null when drag stops.
		}
		invalidate();
	}
	/*class ShowImageEffectAsyncTask extends AsyncTask<Img, Void, Void> {
		Img img_tg,img;
		
		@Override
		protected void onPreExecute() {
			//HomeActivity.this.showProgressDialog();
		}

		@Override
		protected Void doInBackground(Img... params) {
			img=params[0];
			HomeActivity.picture_adjust(img.bitmap_img());	
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			//imageView_Picture.setImageBitmap(bm);

			img_tg=img;
			Drawable drawabledrawable = new BitmapDrawable(HomeActivity.bitmap_efffect);
			img_tg.drawable=drawabledrawable;						
			mImages.remove(img);
			mImages.add(img_tg);
		}

	}	
	*/
	/**
	 * Get the current position and scale of the selected image. Called whenever
	 * a drag starts or is reset.
	 */
	public void getPositionAndScale(Img img, MultiTouchController.PositionAndScale objPosAndScaleOut) {
		// FIXME affine-izem (and fix the fact that the anisotropic_scale part
		// requires averaging the two scale factors)
		objPosAndScaleOut.set(img.getCenterX(), img.getCenterY(),
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
				(img.getScaleX() + img.getScaleY()) / 2,
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0, img.getScaleX(),
				img.getScaleY(), (mUIMode & UI_MODE_ROTATE) != 0,
				img.getAngle());
	}

	/** Set the position and scale of the dragged/stretched image. */
	public boolean setPositionAndScale(Img img,
									   MultiTouchController.PositionAndScale newImgPosAndScale, MultiTouchController.PointInfo touchPoint) {
		currTouchPoint.set(touchPoint);
		boolean ok = img.setPos(newImgPosAndScale);
		if (ok)
			invalidate();
		return ok;
	}

	// ----------------------------------------------------------------------------------------------

	public class  Img {
		// private int resId;

		public Drawable drawable;

		private boolean firstLoad;

		private int width, height;// , displayWidth, displayHeight;

		private float centerX, centerY, scaleX, scaleY, angle;

		private float minX, maxX, minY, maxY;

		private static final float SCREEN_MARGIN = 100;

		public Img(Drawable drawable) {
			// this.resId = resId;
			this.drawable = drawable;
			this.firstLoad = true;
			// getMetrics();//res);
		}
        ///Drawable Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		public Bitmap bitmap_img() {
			// this.resId = resId;
			//this.drawable = drawable;
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			//this.firstLoad = true;			
			// getMetrics();//res);
			return bitmap;
		}
		
		// private void getMetrics(Resources res) {
		// DisplayMetrics metrics = res.getDisplayMetrics();
		// // The DisplayMetrics don't seem to always be updated on screen
		// rotate, so we hard code a portrait
		// // screen orientation for the non-rotated screen here...
		// // this.displayWidth = metrics.widthPixels;
		// // this.displayHeight = metrics.heightPixels;
		// this.displayWidth = res.getConfiguration().orientation ==
		// Configuration.ORIENTATION_LANDSCAPE ? Math.max(metrics.widthPixels,
		// metrics.heightPixels) : Math.min(metrics.widthPixels,
		// metrics.heightPixels);
		// this.displayHeight = res.getConfiguration().orientation ==
		// Configuration.ORIENTATION_LANDSCAPE ? Math.min(metrics.widthPixels,
		// metrics.heightPixels) : Math.max(metrics.widthPixels,
		// metrics.heightPixels);
		// }

		/** Called by activity's onResume() method to load the images */
		public void load() {// Resources res) {
			// getMetrics(res);
			// this.drawable = res.getDrawable(resId);
			this.width = drawable.getIntrinsicWidth();
			this.height = drawable.getIntrinsicHeight();
			float cx, cy, sx, sy;
			if (firstLoad) {
				float sc = Math.min(displayWidth / width, displayHeight
						/ height);
				cx = displayWidth / 2;
				cy = displayHeight / 2;
				// cx = SCREEN_MARGIN
				// + (float) (Math.random() * (displayWidth - 2 *
				// SCREEN_MARGIN));
				// cy = SCREEN_MARGIN
				// + (float) (Math.random() * (displayHeight - 2 *
				// SCREEN_MARGIN));
				// float sc = (float) (Math.max(displayWidth, displayHeight)
				// / (float) Math.max(width, height) * Math.random() * 0.3 +
				// 0.2);
				sx = sy = sc;
				//setPos(cx, cy, sx, sy, 0.0f);
				firstLoad = false;
	} else {
//				// Reuse position and scale information if it is available
//				// FIXME this doesn't actually work because the whole activity
//				// is torn down and re-created on rotate
		//Dieu chinh vi tri anh o day
		float sc = Math.min(displayWidth / width, displayHeight
				/ height);
		//cx = displayWidth / 2;
		//cy = displayHeight / 2;
				cx = -this.centerX+displayWidth / 2;
			    cy = -this.centerY+displayWidth / 2;
				sx = this.scaleX;
				sy = this.scaleY;
				// Make sure the image is not off the screen after a screen
			/*if (this.maxX < SCREEN_MARGIN)
					cx = SCREEN_MARGIN;
				else if (this.minX > displayWidth - SCREEN_MARGIN)
					cx = displayWidth - SCREEN_MARGIN;
				if (this.maxY > SCREEN_MARGIN)
					cy = SCREEN_MARGIN;
				else if (this.minY > displayHeight - SCREEN_MARGIN)
					cy = displayHeight - SCREEN_MARGIN;*/
			}
			setPos(cx, cy, sx, sy, 0.0f);
		}

		/**
		 * Called by activity's onPause() method to free memory used for loading
		 * the images
		 */
//		public void unload() {
//			this.drawable = null;
//		}

		/** Set the position and scale of an image in screen coordinates */
		public boolean setPos(MultiTouchController.PositionAndScale newImgPosAndScale) {
			return setPos(
					newImgPosAndScale.getXOff(),
					newImgPosAndScale.getYOff(),
					(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
							.getScaleX() : newImgPosAndScale.getScale(),
					(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
							.getScaleY() : newImgPosAndScale.getScale(),
					newImgPosAndScale.getAngle());
			// FIXME: anisotropic scaling jumps when axis-snapping
			// FIXME: affine-ize
			// return setPos(newImgPosAndScale.getXOff(),
			// newImgPosAndScale.getYOff(),
			// newImgPosAndScale.getScaleAnisotropicX(),
			// newImgPosAndScale.getScaleAnisotropicY(), 0.0f);
		}

		/** Set the position and scale of an image in screen coordinates */
		private boolean setPos(float centerX, float centerY, float scaleX,
				float scaleY, float angle) {
			// Log.e("TUNT", "centerX " + centerX + " | centerY " + centerY);
			// Log.e("TUNT", "scaleX " + scaleX + " | scaleY " + scaleY +
			// " | angle " + angle);
			float ws = (width / 2) * scaleX, hs = (height / 2) * scaleY, ms = Math
					.min(scaleX, scaleY);
			float newMinX = centerX - ws, newMinY = centerY - hs, newMaxX = centerX
					+ ws, newMaxY = centerY + hs;
			// Log.e("TUNT", "ws " + ws + " | hs " + hs);
			// Log.e("TUNT", "newMinX " + newMinX + " | newMinY " + newMinY);
			// Log.e("TUNT", "newMaxX " + newMaxX + " | newMaxY " + newMaxY);
			if (newMinX > displayWidth - SCREEN_MARGIN
					|| newMaxX < SCREEN_MARGIN
					|| newMinY > displayHeight - SCREEN_MARGIN
					|| newMaxY < SCREEN_MARGIN || ms < minScale
					|| ms > maxScale)
				return false;
			this.centerX = centerX;
			this.centerY = centerY;
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.angle = angle;
			this.minX = newMinX;
			this.minY = newMinY;
			this.maxX = newMaxX;
			this.maxY = newMaxY;
			// Log.e("TUNT", "centerX " + centerX + " | centerY " + centerY);
			// Log.e("TUNT", "scaleX " + scaleX + " | scaleY " + scaleY);
			// Log.e("TUNT", "minX " + minX + " | minY " + minY);
			// Log.e("TUNT", "maxX " + maxX + " | maxY " + maxY);
			// Log.e("TUNT", "angle " + angle);
			return true;
		}

		/** Return whether or not the given screen coords are inside this image */
		public boolean containsPoint(float scrnX, float scrnY) {
			// FIXME: need to correctly account for image rotation
			return (scrnX >= minX && scrnX <= maxX && scrnY >= minY && scrnY <= maxY);
		}

		public void draw(Canvas canvas) {
			if(firstLoad) {
				load();
			}
			canvas.save();
			float dx = (maxX + minX) / 2;
			float dy = (maxY + minY) / 2;
			drawable.setBounds((int) minX, (int) minY, (int) maxX, (int) maxY);
			canvas.translate(dx, dy);
			canvas.rotate(angle * 180.0f / (float) Math.PI);
			canvas.translate(-dx, -dy);
			drawable.draw(canvas);
			canvas.restore();
		}

		public void draw(Canvas canvas, float sX, float sY, float scale) {
			canvas.save();
			float newCenterX = centerX * scale, newCenterY = centerY * scale;
			float ws = (width / 2) * sX, hs = (height / 2) * sY;
			float newMinX = newCenterX - ws, newMinY = newCenterY - hs, newMaxX = newCenterX
					+ ws, newMaxY = newCenterY + hs;
			float dx = (newMaxX + newMinX) / 2;
			float dy = (newMaxY + newMinY) / 2;
			drawable.setBounds((int) newMinX, (int) newMinY, (int) newMaxX,
					(int) newMaxY);
			canvas.translate(dx, dy);
			canvas.rotate(angle * 180.0f / (float) Math.PI);
			canvas.translate(-dx, -dy);
			drawable.draw(canvas);
			canvas.restore();
		}

		public Drawable getDrawable() {
			return drawable;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public float getCenterX() {
			return centerX;
		}

		public float getCenterY() {
			return centerY;
		}

		public float getScaleX() {
			return scaleX;
		}

		public float getScaleY() {
			return scaleY;
		}

		public float getAngle() {
			return angle;
			
		}

		// FIXME: these need to be updated for rotation
		public float getMinX() {
			return minX;
		}

		public float getMaxX() {
			return maxX;
		}

		public float getMinY() {
			return minY;
		}

		public float getMaxY() {
			return maxY;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		displayWidth = MeasureSpec.getSize(widthMeasureSpec);
		displayHeight = MeasureSpec.getSize(heightMeasureSpec);

		if (displayWidth > 0 && displayHeight > 0) {
			int n = mImages.size();
			for (int i = 0; i < n; i++) {
				mImages.get(i).load();
			}
		}
	}

	public Bitmap[] getBitmaps() {
		Bitmap[] bitmaps = new Bitmap[mImages.size()];
		Img img;
		int n = mImages.size();
		for (int i = 0; i < n; i++) {
			img = mImages.get(i);
			bitmaps[i] = ((BitmapDrawable) img.drawable).getBitmap();
		}
		return bitmaps;
	}

	public Bitmap getShowingBitmap() {
		Bitmap bitmap = Bitmap.createBitmap((int) displayWidth,
				(int) displayHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		int n = mImages.size();
		for (int i = 0; i < n; i++) {
			mImages.get(i).draw(canvas);
		}
		return bitmap;
	}

	public Bitmap getShowingBitmap(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		float scale, sX, sY;
		Img img;
		int n = mImages.size();
		for (int i = 0; i < n; i++) {
			img = mImages.get(i);
			scale = (float) width / displayWidth;
			// degrees = (float) Math.toDegrees(img.angle);
			sX = img.scaleX * scale;
			sY = img.scaleY * scale;
			img.draw(canvas, sX, sY, scale);
		}
		return bitmap;
	}

	
}