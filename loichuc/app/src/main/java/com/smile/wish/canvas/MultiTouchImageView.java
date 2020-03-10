package com.smile.wish.canvas;/*package com.vn.igrap.viewpage;
import com.vn.igrap.viewpage.MultiTouchController;
import com.vn.igrap.viewpage.MultiTouchController.MultiTouchObjectCanvas;
import com.vn.igrap.viewpage.MultiTouchController.PointInfo;
import com.vn.igrap.viewpage.MultiTouchController.PositionAndScale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MultiTouchImageView extends ImageView implements
		MultiTouchObjectCanvas<Object> {

	Matrix matrix = new Matrix();

	private MultiTouchController<Object> multiTouchController = new MultiTouchController<Object>(
			this);

	// --

	private PointInfo currTouchPoint = new PointInfo();

	private boolean mShowDebugInfo = false;

	private static final int UI_MODE_ROTATE = 1, UI_MODE_ANISOTROPIC_SCALE = 2;

	private int mUIMode = UI_MODE_ROTATE;

	// --

	private float displayWidth, displayHeight, width, height;

	private float matrixX, matrixY;

	private float minScale = 0.2f, maxScale = 3f;

	private float centerX, centerY, scaleX, scaleY, angle = 0.0f, angdeg;

	private float minX, maxX, minY, maxY;

	private static final float SCREEN_MARGIN = 100;

	// --

	private Paint mLinePaintTouchPointCircle = new Paint();

	private Paint mLinePaintCrossHairs = new Paint();

	private Paint mLinePaintCoords = new Paint();

	private Paint mRectPaintCoords = new Paint();

	private Paint mAngLabelPaint = new Paint();

	// ---------------------------------------------------------------------------------------------------

	public MultiTouchImageView(Context context) {
		this(context, null);
	}

	public MultiTouchImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MultiTouchImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		matrix.setTranslate(1f, 1f);
		setImageMatrix(matrix);
		setScaleType(ScaleType.MATRIX);

		mLinePaintCoords.setStyle(Style.STROKE);
		mLinePaintCoords.setAntiAlias(true);

		mLinePaintTouchPointCircle.setColor(Color.YELLOW);
		mLinePaintTouchPointCircle.setStrokeWidth(5);
		mLinePaintTouchPointCircle.setStyle(Style.STROKE);
		mLinePaintTouchPointCircle.setAntiAlias(true);
		mLinePaintCrossHairs.setColor(Color.RED);
		mLinePaintCrossHairs.setStyle(Style.STROKE);
		mLinePaintCrossHairs.setAntiAlias(true);
		mRectPaintCoords.setColor(Color.BLUE);
		mRectPaintCoords.setStyle(Style.STROKE);
		mRectPaintCoords.setAntiAlias(true);
		mAngLabelPaint.setTextSize(24);
		mAngLabelPaint.setTypeface(Typeface.SANS_SERIF);
		mAngLabelPaint.setColor(Color.BLUE);
		mAngLabelPaint.setTextAlign(Align.CENTER);
		mAngLabelPaint.setAntiAlias(true);
	}

	// ---------------------------------------------------------------------------------------------------

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return multiTouchController.onTouchEvent(event);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		width = bm.getWidth();
		height = bm.getHeight();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		displayWidth = MeasureSpec.getSize(widthMeasureSpec);
		displayHeight = MeasureSpec.getSize(heightMeasureSpec);

		float cx, cy, sx, sy;
		float sc = Math.min(displayWidth / width, displayHeight / height);
		cx = displayWidth / 2;
		cy = displayHeight / 2;
		sx = sy = sc;
		matrix.setScale(sc, sc);
		setImageMatrix(matrix);

		double a = centerX - minX;
		double b = centerY - minY;
		double h = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		double cos = a / h;
		angdeg = (float) (180 + Math.toDegrees(Math.acos(cos)));
		
		setPos(cx, cy, sx, sy, 0.0f);
		setImageMatrix(matrix);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (currTouchPoint.isDown()) {
			setImageMatrix(matrix);
		}
		drawMultitouchDebugMarks(canvas);
	}

	private void calcTranslate() {
		float r = (float) Math.sqrt(Math.pow(centerX - minX, 2)
				+ Math.pow(centerY - minY, 2));
		double degrees = Math.toRadians(Math.toDegrees(angle) + angdeg);
		matrixX = (float) (centerX + r * Math.cos(degrees));
		matrixY = (float) (centerY + r * Math.sin(degrees));
	}

	// ---------------------------------------------------------------------------------------------------

	public void trackballClicked() {
		mUIMode = (mUIMode + 1) % 3;
		invalidate();
	}

	private void drawMultitouchDebugMarks(Canvas canvas) {
		if (mShowDebugInfo) {
			if (currTouchPoint.isDown()) {
				float[] xs = currTouchPoint.getXs();
				float[] ys = currTouchPoint.getYs();
				float[] pressures = currTouchPoint.getPressures();
				int numPoints = Math.min(currTouchPoint.getNumTouchPoints(), 2);
				for (int i = 0; i < numPoints; i++) {
					canvas.drawCircle(xs[i], ys[i], 50 + pressures[i] * 80,
							mLinePaintTouchPointCircle);
				}
				if (numPoints == 2) {
					canvas.drawLine(xs[0], ys[0], xs[1], ys[1],
							mLinePaintTouchPointCircle);
				}
			}
			float dx = (maxX + minX) / 2;
			float dy = (maxY + minY) / 2;
			canvas.save();
			calcTranslate();
			canvas.drawLine(0, centerY, displayWidth, centerY, mLinePaintCoords);
			canvas.drawLine(centerX, 0, centerX, displayHeight,
					mLinePaintCoords);
			canvas.translate(dx, dy);
			canvas.rotate(angle * 180.0f / (float) Math.PI);
			canvas.translate(-dx, -dy);

			canvas.drawLine(0, centerY, displayWidth, centerY,
					mLinePaintCrossHairs);
			canvas.drawLine(centerX, 0, centerX, displayHeight,
					mLinePaintCrossHairs);

			canvas.drawLine(minX, minY, maxX, maxY, mLinePaintCoords);
			canvas.drawLine(minX, maxY, maxX, minY, mLinePaintCoords);

			canvas.drawRect(minX, minY, maxX, maxY, mRectPaintCoords);

			canvas.drawText(
					"left top (" + (int) minX + ", " + (int) minY + ")", minX,
					minY, mAngLabelPaint);
			canvas.drawText("right top (" + (int) maxX + ", " + (int) minY
					+ ")", maxX, minY, mAngLabelPaint);
			canvas.drawText("left bottom (" + (int) minX + ", " + (int) maxY
					+ ")", minX, maxY, mAngLabelPaint);
			canvas.drawText("right bottom (" + (int) maxX + ", " + (int) maxY
					+ ")", maxX, maxY, mAngLabelPaint);

			double aX = maxX - minX, bY = maxY - minY;
			float c = (float) Math.sqrt(Math.pow(aX, 2f) + Math.pow(bY, 2f));

			canvas.drawCircle(centerX, centerY, c / 2, mLinePaintCoords);
			canvas.restore();
		}
	}

	// ---------------------------------------------------------------------------------------------------

	*//**
	 * Get the image that is under the single-touch point, or return null
	 * (canceling the drag op) if none
	 *//*
	@Override
	public Object getDraggableObjectAtPoint(PointInfo touchPoint) {
		return this;
	}

	*//**
	 * Get the current position and scale of the selected image. Called whenever
	 * a drag starts or is reset.
	 *//*
	@Override
	public void getPositionAndScale(Object obj,
			PositionAndScale objPosAndScaleOut) {
		objPosAndScaleOut.set(centerX, centerY,
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
				(scaleX + scaleY) / 2,
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0, scaleX, scaleY,
				(mUIMode & UI_MODE_ROTATE) != 0, angle);
	}

	@Override
	public boolean setPositionAndScale(Object obj,
			PositionAndScale newObjPosAndScale, PointInfo touchPoint) {
		currTouchPoint.set(touchPoint);
		boolean ok = setPos(newObjPosAndScale);
		if (ok)
			invalidate();
		return ok;
	}

	*//**
	 * Select an object for dragging. Called whenever an object is found to be
	 * under the point (non-null is returned by getDraggableObjectAtPoint()) and
	 * a drag operation is starting. Called with null when drag op ends.
	 *//*
	@Override
	public void selectObject(Object obj, PointInfo touchPoint) {
		touchPointChanged(touchPoint);
	}

	*//**
	 * Called when the touch point info changes, causes a redraw.
	 * 
	 * @param touchPoint
	 *//*
	private void touchPointChanged(PointInfo touchPoint) {
		// Take a snapshot of touch point info, the touch point is volatile
		currTouchPoint.set(touchPoint);
		invalidate();
	}

	// ----------------------------------------------------------------------------------------------

	*//** Set the position and scale of an image in screen coordinates *//*
	public boolean setPos(PositionAndScale newImgPosAndScale) {
		return setPos(
				newImgPosAndScale.getXOff(),
				newImgPosAndScale.getYOff(),
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
						.getScaleX() : newImgPosAndScale.getScale(),
				(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0 ? newImgPosAndScale
						.getScaleY() : newImgPosAndScale.getScale(),
				newImgPosAndScale.getAngle());
	}

	*//** Set the position and scale of an image in screen coordinates *//*
	private boolean setPos(float centerX, float centerY, float scaleX,
			float scaleY, float angle) {
		float ws = (width / 2) * scaleX, hs = (height / 2) * scaleY, ms = Math
				.min(scaleX, scaleY);
		float newMinX = centerX - ws, newMinY = centerY - hs, newMaxX = centerX
				+ ws, newMaxY = centerY + hs;
		if (newMinX > displayWidth - SCREEN_MARGIN || newMaxX < SCREEN_MARGIN
				|| newMinY > displayHeight - SCREEN_MARGIN
				|| newMaxY < SCREEN_MARGIN || ms < minScale || ms > maxScale)
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

		float degrees = (float) Math.toDegrees(angle);
		matrix.setScale(scaleX, scaleY);
		matrix.postRotate(degrees);
		calcTranslate();
		matrix.postTranslate(matrixX, matrixY);
		return true;
	}

	*//** Return whether or not the given screen coords are inside this image *//*
	public boolean containsPoint(float scrnX, float scrnY) {
		// FIXME: need to correctly account for image rotation
		return (scrnX >= minX && scrnX <= maxX && scrnY >= minY && scrnY <= maxY);
	}

	// ----------------------------------------------------------------------------------------------

	public Bitmap getShowingBitmap() {
		BitmapDrawable drawable = (BitmapDrawable) getDrawable();
		Bitmap bm = drawable.getBitmap();
		Bitmap croppedBm = Bitmap.createBitmap((int) displayWidth,
				(int) displayHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(croppedBm);
		canvas.drawBitmap(bm, matrix, null);
		return croppedBm;
	}
	
	public Bitmap getShowingBitmap(int width, int height) {
		float scale = (float) width / displayWidth;
		Matrix m = new Matrix();
		float degrees = (float) Math.toDegrees(angle);
		float sX = scaleX * scale, sY = scaleY * scale;
		m.setScale(sX, sY);
		m.postRotate(degrees);
		float r = (float) Math.sqrt(Math.pow(centerX - minX, 2)
				+ Math.pow(centerY - minY, 2));
		double degrees1 = Math.toRadians(Math.toDegrees(angle) + angdeg);
		float mX = (float) (centerX + r * Math.cos(degrees1)) * scale;
		float mY = (float) (centerY + r * Math.sin(degrees1)) * scale;
		m.postTranslate(mX, mY);
		
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setFilterBitmap(true);
		
		BitmapDrawable drawable = (BitmapDrawable) getDrawable();
		Bitmap bm = drawable.getBitmap();
		Bitmap croppedBm = Bitmap.createBitmap(width,
				height, Config.ARGB_8888);
		Canvas canvas = new Canvas(croppedBm);
		canvas.drawBitmap(bm, m, p);
		
		return croppedBm;
	}
}
*/