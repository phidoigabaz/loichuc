package com.smile.wish;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sa90.materialarcmenu.ArcMenu;
import com.smile.wish.Object.Category;
import com.smile.wish.Object.Frame;
import com.smile.wish.adapter.AdapterRecyclerChoiceCategory;
import com.smile.wish.adapter.AdapterRecyclerViewFrame;
import com.smile.wish.adapter.AdapterRecyclerViewIcon;
import com.smile.wish.canvas.Graphic;
import com.smile.wish.canvas.MultiImageTouchView;
import com.smile.wish.canvas.Panel;
import com.smile.wish.canvas.Utilsphoto;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private Panel mPanelPaint;// ,mPanelpicture
    public static MultiImageTouchView mImageUserPicked;
    private RecyclerView recyclerViewFrame;
    /* Select and crop picture */
    private Bitmap BitmapFarm;
    private ImageView imageViewFrame;
    private AdapterRecyclerViewFrame adapterRecyclerViewFrame;
    Frame framesave;
    private AdapterRecyclerViewIcon adapterRecyclerViewIcon;
    private FloatingActionButton floatingActionButtonSave;
    private FloatingActionButton floatingActionButtonShare;
    private FloatingActionButton floatingActionButtonDelete;
    private FloatingActionButton floatingActionButtonAdd;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    ArrayList<Frame> lisFrame = new ArrayList<>();
    int[] listIcon;
    int iDCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        mPanelPaint = findViewById(R.id.panel_add_image_component);
        mImageUserPicked = findViewById(R.id.image_view_user_pick);
        recyclerViewFrame = findViewById(R.id.recycleViewFrame);
        imageViewFrame = findViewById(R.id.imageViewFrame);
        floatingActionButtonSave = findViewById(R.id.fab_arc_save);
        floatingActionButtonShare = findViewById(R.id.fab_arc_share);
        floatingActionButtonDelete = findViewById(R.id.fab_arc_clear);
        floatingActionButtonAdd = findViewById(R.id.fab_arc_add_frame);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFrame.setLayoutManager(layoutManager);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        111);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        SharedPreferences sharedPref = getSharedPreferences(
                Common.NAME_SHAPERENT, Context.MODE_PRIVATE);

        iDCategory = sharedPref.getInt(Common.KEY_ID_CATEGORY, Common.valueByEvent());
        int drawables[] = new int[0];
        if (iDCategory == 1) {
            drawables = Common.getResourceFrameLunarNewYear();
        } else if (iDCategory == 2) {
            drawables = Common.getResourceFrameHappyBirthday();
        } else if (iDCategory == 3) {
            drawables = Common.getResourceFrameValentine();
        }else {
            drawables = Common.getResourceFrameLunarNewYear();
        }
        lisFrame.clear();
        for (int temp : drawables) {
            Frame frame = new Frame();
            frame.setId(temp);
            lisFrame.add(frame);
        }

        adapterRecyclerViewFrame = new AdapterRecyclerViewFrame(lisFrame, getApplicationContext());
        recyclerViewFrame.setAdapter(adapterRecyclerViewFrame);
        adapterRecyclerViewFrame.setOnItemClickedListener(new AdapterRecyclerViewFrame.OnItemClickedListener() {
            @Override
            public void onItemClick(final Frame frame) {
                framesave = frame;

                Glide.with(MainActivity.this)
                        .asBitmap()
                        .load(frame.getId())
                        /*  .skipMemoryCache(true)*/
                        .into(new CustomTarget<Bitmap>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                //BitmapFarm = getBitmapFromVectorDrawable(getApplicationContext(), frame.getId());
                                BitmapFarm = resource;
                               /* if (resource != null)
                                    resource.recycle();*/
                               /* if (BitmapFarm.getWidth() < BitmapFarm.getHeight()) {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                } else {
                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                }*/
                                if (checkFromDrawable(getApplicationContext(), framesave.getId())) {
                                    resizeDrawableVector();
                                } else {
                                    // imageViewFrame.setImageBitmap(null);
                                    resizeDrawableNotVector();
                                }
                                // imageViewFrame.setImageBitmap(BitmapFarm);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });

            }
        });


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_frame:
                        adapterRecyclerViewFrame = new AdapterRecyclerViewFrame(lisFrame, getApplicationContext());
                        recyclerViewFrame.setAdapter(adapterRecyclerViewFrame);
                        adapterRecyclerViewFrame.setOnItemClickedListener(new AdapterRecyclerViewFrame.OnItemClickedListener() {
                            @Override
                            public void onItemClick(final Frame frame) {
                                framesave = frame;

                                Glide.with(MainActivity.this)
                                        .asBitmap()
                                        .load(frame.getId())
                                        .into(new CustomTarget<Bitmap>() {
                                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                // BitmapFarm = getBitmapFromVectorDrawable(getApplicationContext(), frame.getId());
                                              /*  BitmapFarm = resource;
                                                if (BitmapFarm.getWidth() < BitmapFarm.getHeight()) {
                                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                                } else {
                                                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                                }*/
                                                BitmapFarm = resource;
                                                if (checkFromDrawable(getApplicationContext(), framesave.getId())) {
                                                    resizeDrawableVector();
                                                } else {
                                                    resizeDrawableNotVector();
                                                }
                                                //imageViewFrame.setImageBitmap(BitmapFarm);
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });

                            }
                        });
                        return true;
                    case R.id.navigation_text:
                        Intent intent = new Intent(MainActivity.this, WishActivity.class);
                        startActivityForResult(intent, 1);
                        return true;
                    case R.id.navigation_icon:
                        if (iDCategory == 1) {
                            listIcon = Common.getResourceIconLunarNewYear();
                        } else if (iDCategory == 2) {
                            listIcon = Common.getResourceIconHappyBirthday();
                        } else if (iDCategory == 3) {
                            listIcon = Common.getResourceIconValentine();
                        }else {
                            listIcon = Common.getResourceIconLunarNewYear();
                        }
                        adapterRecyclerViewIcon = new AdapterRecyclerViewIcon(listIcon, getApplicationContext());
                        recyclerViewFrame.setAdapter(adapterRecyclerViewIcon);
                        adapterRecyclerViewIcon.setOnItemClickedListener(new AdapterRecyclerViewIcon.OnItemClickedListener() {
                            @Override
                            public void onItemClick(int frame) {
                                mPanelPaint.setCurrentBitmap(
                                        getBitmapFromVectorDrawable(getApplicationContext(), frame),
                                        Panel.ADD_SINGLE_IMAGE);

                            }
                        });
                        return true;

                }
                return false;
            }
        });


        Array_typeface();
        floatingActionButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveImage() != null) {

                    Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "saved error", Toast.LENGTH_LONG).show();
                }

            }
        });
        floatingActionButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareImageAsynTask().execute();
            }
        });
        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture_delete();
            }
        });
        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_catogery, null);
                alertDialogBuilder.setView(view);
                //alertDialogBuilder.setCancelable(false);
                final AlertDialog dialog = alertDialogBuilder.create();

                dialog.show();
                RecyclerView rvCategory = view.findViewById(R.id.rvCategory);
                ImageView ivClose=view.findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                rvCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                ArrayList<Category> categoryArrayList = new ArrayList<>();
                Category categoryOne = new Category(1, "Tết Nguyên Đán");
                Category categoryTwo = new Category(2, "Sinh Nhật");
                Category categoryThree= new Category(3, "Valentine");
                categoryArrayList.add(categoryOne);
                categoryArrayList.add(categoryTwo);
                categoryArrayList.add(categoryThree);
                AdapterRecyclerChoiceCategory adapterRecyclerChoiceCategory = new AdapterRecyclerChoiceCategory(categoryArrayList, getApplicationContext());
                rvCategory.setAdapter(adapterRecyclerChoiceCategory);
                adapterRecyclerChoiceCategory.setOnItemClickedListener(new AdapterRecyclerChoiceCategory.OnItemClickedListener() {
                    @Override
                    public void onItemClick(Category category) {
                        SharedPreferences sharedPref = getSharedPreferences(
                                Common.NAME_SHAPERENT, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(Common.KEY_ID_CATEGORY, category.getId());
                        editor.commit();
                        iDCategory = category.getId();
                        int drawables[] = new int[0];
                        if (iDCategory == 1) {
                            drawables = Common.getResourceFrameLunarNewYear();
                        } else if (iDCategory == 2) {
                            drawables = Common.getResourceFrameHappyBirthday();
                        }else if (iDCategory == 3) {
                            drawables = Common.getResourceFrameValentine();
                        } else {
                            drawables = Common.getResourceFrameLunarNewYear();
                        }
                        lisFrame.clear();
                        for (int temp : drawables) {
                            Frame frame = new Frame();
                            frame.setId(temp);
                            lisFrame.add(frame);
                        }
                        if( recyclerViewFrame.getAdapter() instanceof AdapterRecyclerViewFrame){
                            adapterRecyclerViewFrame.notifyDataSetChanged();
                        }else if ( recyclerViewFrame.getAdapter() instanceof AdapterRecyclerViewIcon){
                            if (iDCategory == 1) {
                                listIcon = Common.getResourceIconLunarNewYear();
                            } else if (iDCategory == 2) {
                                listIcon = Common.getResourceIconHappyBirthday();
                            } else if (iDCategory == 3) {
                                listIcon = Common.getResourceIconValentine();
                            }else {
                                listIcon = Common.getResourceIconLunarNewYear();
                            }
                            adapterRecyclerViewIcon = new AdapterRecyclerViewIcon(listIcon, getApplicationContext());
                            recyclerViewFrame.setAdapter(adapterRecyclerViewIcon);
                            adapterRecyclerViewIcon.setOnItemClickedListener(new AdapterRecyclerViewIcon.OnItemClickedListener() {
                                @Override
                                public void onItemClick(int frame) {
                                    mPanelPaint.setCurrentBitmap(
                                            getBitmapFromVectorDrawable(getApplicationContext(), frame),
                                            Panel.ADD_SINGLE_IMAGE);

                                }
                            });
                        }

                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = null;
        try {
            drawable = AppCompatResources.getDrawable(context, drawableId);
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }*/

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static boolean checkFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return false;
        } else if (drawable instanceof VectorDrawableCompat /*|| drawable instanceof VectorDrawable*/) {
            return true;
        } else {
            return false;

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (framesave != null)
            outState.putInt("idFrame", framesave.getId());
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            final int idDrawable = savedInstanceState.getInt("idFrame", -1);
            if (idDrawable != -1) {
                Glide.with(MainActivity.this)
                        .asBitmap()
                        .load(idDrawable)
                        .into(new CustomTarget<Bitmap>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                BitmapFarm = resource;
                                if (checkFromDrawable(getApplicationContext(), idDrawable)) {
                                    resizeDrawableVector();
                                } else {
                                    resizeDrawableNotVector();
                                }

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
               /* BitmapFarm = getBitmapFromVectorDrawable(getApplicationContext(), savedInstanceState.getInt("idFrame"));
                if (BitmapFarm != null)
                    imageViewFrame.setImageBitmap(BitmapFarm);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Read the state of item position

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
       /* if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            BitmapFarm = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.happy2013);
            imageViewFrame.setImageBitmap(BitmapFarm);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            BitmapFarm = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.happy2013);
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            imageViewFrame.setImageBitmap(BitmapFarm);
        }*/
    }

    private void resizeDrawableVector() {
        if (BitmapFarm.getWidth() < BitmapFarm.getHeight()) {
            float resizeWidth = (float) (1.0f * Common.getScreenWidth() / BitmapFarm.getWidth());
            float resizeHight = (float) (1.0f * Common.getScreenHeight() / BitmapFarm.getHeight());
            float resize = resizeWidth > resizeHight ? resizeHight : resizeWidth;
            if (resize > 1) {
                BitmapFarm = resizeBitmap(BitmapFarm, 1 / resize);
                imageViewFrame.setImageBitmap(BitmapFarm);
            } else {
                imageViewFrame.setImageBitmap(BitmapFarm);
            }
        } else {
            float resizeWidth = (float) (1.0f * Common.getScreenWidth() / BitmapFarm.getWidth());
            float resizeHight = (float) (1.0f * Common.getScreenHeight() / BitmapFarm.getHeight());
            float resize = resizeWidth > resizeHight ? resizeHight : resizeWidth;
            if (resize > 1) {
                BitmapFarm = resizeBitmap(BitmapFarm, 1 / resize);
                imageViewFrame.setImageBitmap(BitmapFarm);
            } else {
                imageViewFrame.setImageBitmap(BitmapFarm);
            }
            //imageViewFrame.setImageBitmap(BitmapFarm);
        }
    }

    private void resizeDrawableNotVector() {
        float resizeWidth = (float) (1.0f * Common.getScreenWidth() / BitmapFarm.getWidth());
        float resizeHight = (float) (1.0f * Common.getScreenHeight() / BitmapFarm.getHeight());
        float resize = resizeWidth > resizeHight ? resizeHight : resizeWidth;
        BitmapFarm = resizeBitmap(BitmapFarm, 1 / resize);
        imageViewFrame.setImageBitmap(BitmapFarm);
    }

    private String saveImage() {
        String path = null;
        if (Utilsphoto.isExternalStorageWritable()) {
            File image = Utilsphoto.getICuteStorageDir("IMG_" + System.currentTimeMillis()
                    + ".PNG");
            Bitmap bm = getExportImage();

            OutputStream outStream = null;
            try {
                outStream = new FileOutputStream(image);
                bm.compress(Bitmap.CompressFormat.PNG, 85, outStream);
                // 100 to keep full quality of the image
                outStream.flush();
                outStream.close();
			/*	MediaStore.Images.Media.insertImage(getContentResolver(),
						image.getAbsolutePath(), image.getName(), image.getName());*/
                ContentValues imageimage = getImageContent(image);
                Uri result = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageimage);
                path = image.getPath();
                /**Update image to gallery**/
                //MediaStore.Images.Media.insertImage(getContentResolver(),image.getAbsolutePath(),image.getName(),image.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    private class ShareImageAsynTask extends AsyncTask<Void, Void, Void> {
        private String imagePath;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImage();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (imagePath != null) {
                File fileShare = new File(imagePath);
                if (fileShare.exists()) {
                    String type = "*/*";
                    //String extraText = "tungda";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    Uri myPhotoFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", fileShare);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, myPhotoFileUri);
                    startActivity(
                            Intent.createChooser(shareIntent, "Share using"));
                }


            } else {

            }
        }

    }

    private void picture_delete() {
        if (mImageUserPicked != null)
            mImageUserPicked.unloadImages();
        if (mPanelPaint != null)
            mPanelPaint.clear();

    }

    public ContentValues getImageContent(File parent) {
        ContentValues image = new ContentValues();
        image.put(MediaStore.Images.Media.TITLE, "wish");
        image.put(MediaStore.Images.Media.DISPLAY_NAME, "wish");
        image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        image.put(MediaStore.Images.Media.ORIENTATION, 0);
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                .toLowerCase().hashCode());
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                .toLowerCase());
        image.put(MediaStore.Images.Media.SIZE, parent.length());
        image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());
        return image;
    }

    private Bitmap getExportImage() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setFilterBitmap(true);
        int width, height;
        width = mPanelPaint.getWidth();
        height = mPanelPaint.getHeight();
        Bitmap bmbm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmbm);
        canvas.drawColor(Color.WHITE);
        if (BitmapFarm != null) {
            canvas.drawBitmap(BitmapFarm, (width - BitmapFarm.getWidth()) / 2, (height - BitmapFarm.getHeight()) / 2, p);
        }

        Bitmap bmDraw = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas drawCanvas = new Canvas(bmDraw);
        //	drawCanvas.scale(sW, sH);
        List<Graphic> graphics = mPanelPaint.getGraphics();
        if (graphics != null && graphics.size() > 0) {
            for (Graphic graphic : graphics) {
                if (graphic.getResizeBitmap() == null) {
                    drawCanvas.drawBitmap(graphic.getBitmap(), graphic
                            .getCoordinates().getX(), graphic.getCoordinates()
                            .getY(), p);
                } else {
                    drawCanvas.drawBitmap(graphic.getResizeBitmap(), graphic
                            .getCoordinates().getX(), graphic.getCoordinates()
                            .getY(), p);
                }
            }
        }
        canvas.drawBitmap(bmDraw, 0, 0, p);
        return bmbm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 10) {
            int textColor = data.getIntExtra("textcolor", 0);
            int postionFont = data.getIntExtra("typeface", 0);
            String textWish = data.getStringExtra("textwish");
            int textSize = data.getIntExtra("textSize", 30);
            textAsBitmap(textWish, textSize, textColor, postionFont);
        }
    }

    public static ArrayList<Typeface> listTypeface = new ArrayList<>();

    public void Array_typeface() {
        if (listTypeface.size() == 0) {
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/OpenSans-Regular.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/OngDo.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/comic.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/symbol.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/alger.TTF"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/Netmuc.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/Valentine.ttf"));

            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/VarelaRound-Regular.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/VT323-Regular.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/SpaceMono-Bold.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/SpaceMono-BoldItalic.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/Taviraj-SemiBold.ttf"));
            listTypeface.add(Typeface.createFromAsset(getAssets(), "font/Taviraj-SemiBoldItalic.ttf"));
        }
    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor, int positionTypeface) {
        String lines[] = text.split("\n");
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(listTypeface.get(positionTypeface));

        int maxLength = 0;
        for (int i = 0; i < lines.length; ++i) {
            maxLength = lines[i].length() > lines[maxLength].length() ? i : maxLength;
        }
        int width = (int) (paint.measureText(lines[maxLength]) + textSize); // round
        width = width > (int) (Common.getScreenWidth() * 3f / 4f) ? (int) (Common.getScreenWidth() * 3f / 4f) : width;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTypeface(listTypeface.get(positionTypeface));
        StaticLayout sl = new StaticLayout(text, textPaint, width,
                Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        float textHeight = getTextHeight(text, textPaint);
        int numberOfTextLines = sl.getLineCount();
        Bitmap image = Bitmap.createBitmap(width, (int) ((textHeight + textSize / 2) * (numberOfTextLines + 0.5)), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        sl.draw(canvas);
        mPanelPaint.setCurrentBitmap(
                image,
                Panel.ADD_SINGLE_IMAGE);
        return image;
    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Bitmap resizeBitmap(Bitmap bitmap, float pickSize) {
        int dstWidth = (int) (bitmap.getWidth() / pickSize);
        int dstHeight = (int) (bitmap.getHeight() / pickSize);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth,
                dstHeight, true);
        return scaledBitmap;
    }

    @Override
    protected void onStop() {
        System.gc();
        super.onStop();
    }

    @Override
    protected void onResume() {
        System.gc();
        super.onResume();
    }
}
