package com.smile.wish.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.appcompat.content.res.AppCompatResources;

import com.smile.wish.Common;
import com.smile.wish.MainActivity;
import com.smile.wish.R;
import com.smile.wish.canvas.utils.Constantwish;

import java.util.ArrayList;
import java.util.List;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int ADD_DONE = 0;
    public static final int ADD_SINGLE_IMAGE = 1;
    public static final int ADD_MULTIPLE_IMAGE = 2;

    private ImageDrawThread _thread;
    private List<Graphic> _graphics = new ArrayList<Graphic>();
    private List<Graphic> _multipleGraphics = new ArrayList<Graphic>();
    private List<List<Graphic>> _drawGraphics = new ArrayList<List<Graphic>>();
    private List<Integer> _drawStack = new ArrayList<Integer>();
    private Graphic _currentGraphic = null;
    private Bitmap _currentBitmap = null;
    private List<Bitmap> _currentBitmaps = null;
    private boolean _isSelectedCurrentItem = false;
    private int _imageMode;
    private int _rotate;
    private boolean _isDraw = true;

    public Panel(Context context) {
        super(context);
    }

    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPanel();
    }

    private void initPanel() {
        getHolder().addCallback(this);
        setZOrderOnTop(true); // necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        _thread = new ImageDrawThread(getHolder(), this);
        setFocusable(true);
    }

    int i = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Graphic graphic = null;
        if (_imageMode == ADD_DONE
                && event.getAction() == MotionEvent.ACTION_DOWN
                && _graphics.size() > 0) {
            synchronized (_graphics) {
                for (Graphic item : _graphics) {
                    graphic = item;
                    Bitmap bitmap = item.getBitmap();
                    Graphic.Coordinates coords = item.getCoordinates();
                    if (event.getX() >= coords.getX()
                            && event.getX() <= coords.getX()
                            + bitmap.getWidth()
                            && event.getY() >= coords.getY()
                            && event.getY() <= coords.getY()
                            + bitmap.getHeight()) {
//						i++;
//						Handler handler = new Handler();
//						Runnable r = new Runnable() {
//
//							@Override
//							public void run() {
//
//								i = 0;
//							}
//						};
//						if (i == 1) {
//							handler.postDelayed(r, 250);
//						} else if (i == 2) {
//							i = 0;
//						setCurrentBitmap(ADD_SINGLE_IMAGE);
//						}
                        break;
                    }
                }
            }
        }
        if (graphic != null) {
            setCurrentBitmap(ADD_SINGLE_IMAGE);
            _isDraw = true;
        }
        if (_isDraw) {
            // int action = event.getAction();
            // if (!((action & MotionEvent.ACTION_MASK) ==
            // MotionEvent.ACTION_POINTER_DOWN)) {
            if (_imageMode == ADD_SINGLE_IMAGE) {
                // if (_currentBitmap != null) {
                return drawSingle(event);
                // }
            } else if (_imageMode == ADD_MULTIPLE_IMAGE) {
                // if (_currentBitmaps != null) {
                return drawMultiple(event);
                // }
            }
        } else {
            setCurrentBitmap(ADD_DONE);
        }
        return false;
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Determine the space between the two Event
     */
    private float spacing(MotionEvent oldEvent, MotionEvent newEvent) {
        float x = oldEvent.getX() - newEvent.getX();
        float y = oldEvent.getY() - newEvent.getY();
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, false);
        original.recycle();
        return rotatedBitmap;
    }

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int ZOOMONEfINGER = 3;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 5f;
    private float oldDistTemp = 5f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;
    MotionEvent oldEvent = null;

    private boolean drawSingle(MotionEvent event) {
        try {
            int x = (int) event.getX();
            int y = (int) event.getY();

            synchronized (_thread.getSurfaceHolder()) {
                Graphic graphic = null;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        for (Graphic item : _graphics) {

                            Bitmap bitmap = null;
                            if (item.getResizeBitmap() == null) {
                                bitmap = item.getBitmap();
                            } else {
                                bitmap = item.getResizeBitmap();
                            }
                            Graphic.Coordinates coords = item.getCoordinates();
                            int left = coords.getX();
                            int right = coords.getX() + bitmap.getWidth();
                            int top = coords.getY();
                            int bottom = coords.getY() + bitmap.getHeight();

                            if (item.isSelect() && x > left - 80 && x < left + 80 && y > top - 80 && y < top + 80) {
                                _graphics.remove(item);
                            } else if (x > left && x < right + 40 && y > top && y < bottom + 40) {
                                graphic = item;
                                _isSelectedCurrentItem = true;
                                item.setSelect(true);
                                start.set(event.getX(), event.getY());
                                mode = DRAG;
                                lastEvent = null;
                                oldEvent = event;
                            } else {
                                item.setSelect(false);
                            }
                        }
                      /*  if (graphic == null && _isDraw) {
                            graphic = new Graphic(_currentBitmap);
                            graphic.getCoordinates().setX(
                                    x - graphic.getBitmap().getWidth() / 2);
                            graphic.getCoordinates().setY(
                                    y - graphic.getBitmap().getHeight() / 2);
                        }*/
                        _currentGraphic = graphic;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        oldDistTemp = oldDist;
                        if (_currentGraphic != null) {

                            if (oldDist > 2f) {
                                midPoint(mid, event);
                                mode = ZOOM;
                            }
                            lastEvent = new float[4];
                            lastEvent[0] = event.getX(0);
                            lastEvent[1] = event.getX(1);
                            lastEvent[2] = event.getY(0);
                            lastEvent[3] = event.getY(1);
                            d = rotation(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (_currentGraphic != null) {
                            float dx = event.getX() - start.x;
                            float dy = event.getY() - start.y;
                            start.set(event.getX(), event.getY());
                            if (mode == DRAG) {
                                Graphic.Coordinates coords = _currentGraphic.getCoordinates();
                                Bitmap bitmap;
                                if (_currentGraphic.getResizeBitmap() == null) {
                                    bitmap = _currentGraphic.getBitmap();
                                } else {
                                    bitmap = _currentGraphic.getResizeBitmap();
                                }
                                int left = coords.getX();
                                int right = coords.getX() + bitmap.getWidth();
                                int top = coords.getY();
                                int bottom = coords.getY() + bitmap.getHeight();


                                if (x > right - 40 && x < right + 40 && y > bottom - 40 && y < bottom + 40) {

                                    float scale = (float) x / right;
                                    //oldDist = newDist;
                                    //oldEvent=event;
                                    Bitmap bitmapTemp = _currentGraphic.getResizeBitmap();
                                    float scaleTemp = 1;
                                    if (bitmapTemp != null) {
                                        try {
                                            scaleTemp = (float) bitmapTemp.getWidth() / _currentGraphic.getBitmap().getWidth();
                                        } catch (Exception e) {
                                            scaleTemp = 1;
                                            e.printStackTrace();
                                        }
                                    }
                                    if (scaleTemp == 0) {
                                        scaleTemp = 1;
                                    }
                                    Bitmap bitmapResize = resizeBitmap(_currentGraphic.getBitmap(), 1 / (scale * scaleTemp));
                                    if (bitmapResize.getWidth() < 800 && bitmapResize.getWidth() > 200) {
                                        _currentGraphic.setResizeBitmap(bitmapResize);
                                        _currentGraphic.getCoordinates().setX(
                                                _currentGraphic.getCoordinates().getX()+(int)dx);
                                        _currentGraphic.getCoordinates().setY(
                                                _currentGraphic.getCoordinates().getY()+(int) dy);
                                    }
                                } else {
                                    _currentGraphic.getCoordinates().setX(
                                            _currentGraphic.getCoordinates().getX()+(int)dx);
                                    _currentGraphic.getCoordinates().setY(
                                            _currentGraphic.getCoordinates().getY()+(int) dy);
                                }
                                //matrix.postTranslate(dx, dy);
                            } else if (mode == ZOOM && event.getPointerCount() == 2) {
                                float newDist = spacing(event);
                                if (newDist > 10) {
                                    float scale = newDist / oldDist;
                                    oldDist = newDist;
                                    //_currentGraphic.setScale(scale);
                                    Bitmap bitmapTemp = _currentGraphic.getResizeBitmap();
                                    float scaleTemp = 1;
                                    if (bitmapTemp != null) {
                                        try {
                                            scaleTemp = (float) bitmapTemp.getWidth() / _currentGraphic.getBitmap().getWidth();
                                        } catch (Exception e) {
                                            scaleTemp = 1;
                                            e.printStackTrace();
                                        }
                                    }
                                    Bitmap bitmap = resizeBitmap(_currentGraphic.getBitmap(), 1 / (scale * scaleTemp));
                                    if (bitmap.getWidth() < 800 && bitmap.getWidth() > 100) {
                                        _currentGraphic.getCoordinates().setX(
                                                _currentGraphic.getCoordinates().getX()+(int)dx);
                                        _currentGraphic.getCoordinates().setY(
                                                _currentGraphic.getCoordinates().getY()+(int) dy);
                                        _currentGraphic.setResizeBitmap(bitmap);
                                    }
                                    //matrix.postScale(scale, scale, mid.x, mid.y);
                                } else {
                                    _currentGraphic.getCoordinates().setX(
                                            _currentGraphic.getCoordinates().getX()+(int)dx);
                                    _currentGraphic.getCoordinates().setY(
                                            _currentGraphic.getCoordinates().getY()+(int) dy);
                                }
                                if (lastEvent != null) {

                                    /*newRot = rotation(event);
                                    float r = newRot - d;
                                    _currentGraphic.setAngle(_currentGraphic.getAngle()-newRot);
                                    Bitmap bitmapTemp = _currentGraphic.getResizeBitmap();
                                    float scaleTemp = 1;
                                    if (bitmapTemp != null) {
                                        try {
                                            scaleTemp = (float) bitmapTemp.getWidth() / _currentGraphic.getBitmap().getWidth();
                                        } catch (Exception e) {
                                            scaleTemp = 1;
                                            e.printStackTrace();
                                        }
                                    }
                                    if (scaleTemp == 0) {
                                        scaleTemp = 1;
                                    }
                                    Bitmap bitmap = rotateBitmap(resizeBitmap(_currentGraphic.getBitmap(), 1 / scaleTemp), _currentGraphic.getAngle()/10 );
                                    if (bitmap.getWidth() < 800 && bitmap.getWidth() > 200) {
                                        _currentGraphic.setResizeBitmap(bitmap);
                                        _currentGraphic.getCoordinates().setX(
                                                _currentGraphic.getCoordinates().getX()+(int)dx);
                                        _currentGraphic.getCoordinates().setY(
                                                _currentGraphic.getCoordinates().getY()+(int) dy);
                                    }*/
                                    //matrix.postRotate(r, view.getMeasuredWidth()/2, view.getMeasuredHeight()/2);
                                }
                            } else {
                              /*  _currentGraphic.getCoordinates().setX(
                                        _currentGraphic.getCoordinates().getX()+(int)dx);
                                _currentGraphic.getCoordinates().setY(
                                        _currentGraphic.getCoordinates().getY()+(int) dy);*/
                            }
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        //start.set(event.getX(), event.getY());
                        mode = NONE;
                        lastEvent = null;
                        break;
                    case MotionEvent.ACTION_UP:
                        //start.set(event.getX(), event.getY());
                        if (_currentGraphic != null) {
                            // _currentGraphic.setSelect(false);//Thêm xóa bọc ngoài của object
                            if (_isSelectedCurrentItem) {
                                _graphics.remove(_currentGraphic);
                                _isSelectedCurrentItem = false;
                            } else {
                                _isDraw = false;
                                setCurrentBitmap(ADD_DONE);
                            }
                            _graphics.add(_currentGraphic);
                            List<Graphic> drawGraphics = new ArrayList<Graphic>();
                            drawGraphics.add(_currentGraphic);
                            _drawGraphics.add(drawGraphics);
                            _drawStack.add(ADD_SINGLE_IMAGE);
                            _currentGraphic = null;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }

                return true;
            }
        } catch (Exception e) {
            _isDraw = false;
            setCurrentBitmap(ADD_DONE);
            return false;
        }
    }

    private List<Graphic> drawGraphics;

    private boolean drawMultiple(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        synchronized (_thread.getSurfaceHolder()) {
            Graphic graphic = null;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                drawGraphics = new ArrayList<Graphic>();
                _currentBitmap = _currentBitmaps.get(0);
                graphic = new Graphic(_currentBitmap);
                graphic.getCoordinates().setX(
                        x - graphic.getBitmap().getWidth() / 2);
                graphic.getCoordinates().setY(
                        y - graphic.getBitmap().getHeight() / 2);
                _multipleGraphics.add(graphic);
                drawGraphics.add(graphic);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int index = _currentBitmaps.indexOf(_currentBitmap) + 1;
                if (index >= _currentBitmaps.size()) {
                    index = 0;
                }
                _currentBitmap = _currentBitmaps.get(index);
                graphic = new Graphic(_currentBitmap);
                graphic.getCoordinates().setX(
                        x - graphic.getBitmap().getWidth() / 2);
                graphic.getCoordinates().setY(
                        y - graphic.getBitmap().getHeight() / 2);
                _multipleGraphics.add(graphic);
                drawGraphics.add(graphic);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                _drawGraphics.add(drawGraphics);
                _drawStack.add(ADD_MULTIPLE_IMAGE);
                _currentGraphic = null;
                _isDraw = false;
                setCurrentBitmap(ADD_DONE);
            }
            return true;
        }
    }

    Bitmap bitmapDelete = null;
    Bitmap bitmapResize = null;

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
            Bitmap bitmap;
            Graphic.Coordinates coords;
            synchronized (_graphics) {
                for (Graphic graphic : _graphics) {
                    if (graphic.getResizeBitmap() == null) {
                        bitmap = graphic.getBitmap();
                    } else {
                        bitmap = graphic.getResizeBitmap();
                    }
                    //bitmap = resizeBitmap(bitmap, 1 / graphic.getScale());
                    //  graphic.setBitmap(bitmap);
                    //graphic.setScale(1);
                    coords = graphic.getCoordinates();
                    canvas.drawBitmap(bitmap, coords.getX(), coords.getY(),
                            null);
                    if (graphic.isSelect()) {
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.RED);
                        paint.setAntiAlias(true);
                        float left = coords.getX() - 2;
                        float right = coords.getX() + bitmap.getWidth() + 2;
                        float top = coords.getY() + 2;
                        float bottom = coords.getY() + bitmap.getHeight() + 2;
                        canvas.drawRect(left, top, right, bottom, paint);
                        if (bitmapDelete == null) {
                            bitmapDelete = MainActivity.getBitmapFromVectorDrawable(getContext(),R.drawable.ic_cancel_red_24dp);
                        }
                        canvas.drawBitmap(bitmapDelete, left - 80, top - 80,
                                null);
                         /* if (bitmapResize == null) {
                            bitmapResize = BitmapFactory.decodeResource(getResources(), R.drawable.icon_resize);
                        }*/
                       /* canvas.drawBitmap(bitmapResize, right - 40, bottom - 40,
                                null);*/
                    }
                }
            }
            synchronized (_multipleGraphics) {
                for (Graphic graphic : _multipleGraphics) {
                    bitmap = graphic.getBitmap();
                    coords = graphic.getCoordinates();
                    canvas.drawBitmap(bitmap, coords.getX(), coords.getY(),
                            null);
                }
            }

            // draw current graphic at last...
            /*if (_currentGraphic != null) {
                if (_currentGraphic.getResizeBitmap() == null) {
                    bitmap = _currentGraphic.getBitmap();
                } else {
                    bitmap = _currentGraphic.getResizeBitmap();
                }
                //bitmap = _currentGraphic.getBitmap();
                coords = _currentGraphic.getCoordinates();
                canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
            }*/
        }
    }

    private Bitmap resizeBitmap(Bitmap bitmap, float pickSize) {
        int dstWidth = (int) (bitmap.getWidth() / pickSize);
        int dstHeight = (int) (bitmap.getHeight() / pickSize);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth,
                dstHeight, true);
        return scaledBitmap;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        _thread = new ImageDrawThread(getHolder(), this);
        _thread.setRunning(true);
        _thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // simply copied from sample application LunarLander:
        // we have to tell thread to shut down & wait for it to finish, or
        // else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    public void undo() {
        int lastStack = _drawStack.size() - 1;
        if (lastStack >= 0) {
            List<Graphic> lastStackGraphics = _drawGraphics.get(lastStack);
            if (_drawStack.get(lastStack) == ADD_SINGLE_IMAGE) {
                synchronized (_graphics) {
                    _graphics.remove(lastStackGraphics.get(0));
                }
            } else if (_drawStack.get(lastStack) == ADD_MULTIPLE_IMAGE) {
                synchronized (_multipleGraphics) {
                    _multipleGraphics.removeAll(lastStackGraphics);
                }
            }
            _drawGraphics.remove(lastStack);
            _drawStack.remove(lastStack);
        }
    }

    public void clear() {
        synchronized (_graphics) {
            _graphics.clear();
        }
        synchronized (_multipleGraphics) {
            _multipleGraphics.clear();
        }
        _drawGraphics.clear();
        _drawStack.clear();
        _imageMode = ADD_DONE;
    }

    public void setCurrentBitmap(Bitmap currentBitmap, int imageMode) {
        synchronized (_thread.getSurfaceHolder()) {
            if (_rotate > Constantwish.VALUE_ROTATE_DEFAULT) {
                Matrix matrix = new Matrix();
                matrix.setRotate(_rotate);
                _currentBitmap = Bitmap.createBitmap(currentBitmap, 0, 0,
                        currentBitmap.getWidth(), currentBitmap.getHeight(),
                        matrix, true);
            } else {
                _currentBitmap = currentBitmap;
            }

            Graphic graphic = new Graphic(_currentBitmap);
            graphic.getCoordinates().setX(
                    Common.getScreenWidth() / 2 - graphic.getBitmap().getWidth() / 2);
            graphic.getCoordinates().setY(
                    Common.getScreenHeight() / 2 - graphic.getBitmap().getHeight() / 2);
            _currentGraphic = graphic;
            _graphics.add(_currentGraphic);
            List<Graphic> drawGraphics = new ArrayList<Graphic>();
            drawGraphics.add(_currentGraphic);
            _drawGraphics.add(drawGraphics);
            _drawStack.add(ADD_SINGLE_IMAGE);

            _currentBitmaps = null;
            _imageMode = ADD_DONE;
            _isDraw = true;
        }
    }

    public void setCurrentBitmap(List<Bitmap> currentBitmaps, int imageMode) {
        if (_rotate > Constantwish.VALUE_ROTATE_DEFAULT) {
            _currentBitmaps = new ArrayList<Bitmap>();
            Matrix matrix = new Matrix();
            matrix.setRotate(_rotate);
            for (Bitmap currentBitmap : currentBitmaps) {
                _currentBitmaps.add(Bitmap.createBitmap(currentBitmap, 0, 0,
                        currentBitmap.getWidth(), currentBitmap.getHeight(),
                        matrix, true));
            }
        } else {
            _currentBitmaps = currentBitmaps;
        }
        _currentBitmap = null;
        _imageMode = imageMode;
        _isDraw = true;
    }

    public void setCurrentBitmap(int imageMode) {
        _currentBitmap = null;
        _currentBitmaps = null;
        _imageMode = imageMode;
    }


    public List<Graphic> getGraphics() {
        List<Graphic> graphics = new ArrayList<Graphic>();
        if (_graphics != null && _graphics.size() > 0) {
            graphics.addAll(_graphics);
        }
        if (_multipleGraphics != null && _multipleGraphics.size() > 0) {
            graphics.addAll(_multipleGraphics);
        }
        return graphics;
    }
}
