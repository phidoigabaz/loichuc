package com.smile.wish;

import android.content.res.Resources;

import java.util.Calendar;

public class Common {
    public  static  final  String NAME_SHAPERENT="wish_shaperent";
    public  static  final  String KEY_ID_CATEGORY="IDCATEGORY";
    public  static  final  long   DayMilis=24*60*60*1000;
        public  static int valueByEvent(){
            Calendar calendar = Calendar.getInstance();
            Calendar calendarValentine= Calendar.getInstance();
            calendarValentine.set(Calendar.MONTH,1);
            calendarValentine.set(Calendar.DATE,14);
            if(calendar.getTimeInMillis()>calendarValentine.getTimeInMillis()-5*Common.DayMilis&&calendar.getTimeInMillis()<calendarValentine.getTimeInMillis()+2*Common.DayMilis){
               return 3;
            }else {
                return 2;
            }

    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public static int[] getResourceFrameLunarNewYear(){

        int[] drawables =  {
                R.drawable.tet_01,
                R.drawable.tet_02,
                R.drawable.tet_03,
                R.drawable.tet_04,
                R.drawable.tet_06,
                R.drawable.tet_07,
                R.drawable.tet_08,
                R.drawable.tet_09,
                R.drawable.tet_11,
                R.drawable.tet_12,
                R.drawable.tet_13,
                R.drawable.tet_14,
                R.drawable.tet_15,
                R.drawable.tet_16,
                R.drawable.tet_17

        };
        return drawables;
    }
    public static int[] getResourceFrameHappyBirthday(){

        int[] drawables =  {
                R.drawable.ic_birthday_01,
                R.drawable.ic_birthday_02,
                R.drawable.ic_birthday_03,
                R.drawable.ic_birthday_04,
                R.drawable.ic_birthday_06,
                R.drawable.ic_birthday_07,
                R.drawable.ic_birthday_08,
        };
        return drawables;
    }
    public static int[] getResourceFrameValentine(){

        int[] drawables =  {

                R.drawable.frame_valentine_02,
                R.drawable.frame_valentine_03,
                R.drawable.frame_valentine_04,
                R.drawable.frame_valentine_05,
                R.drawable.frame_valentine_06,
                R.drawable.frame_valentine_07,
                R.drawable.frame_valentine_08,
                R.drawable.frame_valentine_09,
                R.drawable.frame_valentine_11,
                R.drawable.frame_valentine_12,
                R.drawable.frame_valentine_13,
                R.drawable.frame_valentine_14,

        };
        return drawables;
    }
    public static int[] getResourceIconLunarNewYear(){

        int[] drawables =  {
                R.drawable.ic_mouse_01,
                R.drawable.ic_buffalo,
                R.drawable.ic_dog_01,
                R.drawable.ic_horse_01,
                R.drawable.ic_pig_01,
                R.drawable.ic_goat,
                R.drawable.ic_chicken_01,
                R.drawable.ic_tiger_01,
                R.drawable.ic_snake_01,
                R.drawable.ic_dragon_03,
                R.drawable.ic_monkey_01,
                R.drawable.ic_flower_02,
                R.drawable.ic_flower_03,//
                R.drawable.ic_flower_04,
                R.drawable.ic_flower_05,
                R.drawable.ic_flower_07,//
                R.drawable.ic_flower_08,
                R.drawable.ic_flower_09,
                R.drawable.ic_flower_10,
                R.drawable.ic_flower_12,
                R.drawable.ic_flower_14,
                R.drawable.ic_flower_15,
                R.drawable.ic_flower_16,
                R.drawable.ic_flower_18,
                R.drawable.ic_flower_19,
                R.drawable.ic_flower_20,
                R.drawable.ic_flower_21,
                R.drawable.ic_tree_02,
                R.drawable.ic_gift_01,
                R.drawable.ic_candle_01,
                R.drawable.ic_twitter_117595,
                R.drawable.ic_turtle_151431,
                R.drawable.ic_tortoise_47047,
                R.drawable.ic_teddy_bear_447422,
                R.drawable.ic_squirrel_1456764,
                R.drawable.ic_sowa_356552,
                R.drawable.ic_giraffe_40035,
                R.drawable.ic_frog_157934,
                R.drawable.ic_dog_48490,
                R.drawable.ic_dog_2729805,
                R.drawable.ic_cat_46676,
                R.drawable.ic_pig,
                R.drawable.ic_fox,
                R.drawable.ic_snake,
                R.drawable.ic_cat_46676,
                R.drawable.ic_pig,
                R.drawable.ic_fox,
                R.drawable.ic_snake,
                R.drawable.ic_beer
        };
        return drawables;
    }
    public static int[] getResourceIconHappyBirthday(){

        int[] drawables =  {
                R.drawable.ic_candle_01,
                R.drawable.ic_birthday_cake_01,
                R.drawable.ic_birthday_cake_03,
                R.drawable.ic_decorate_02,
                R.drawable.ic_decore_03,
                R.drawable.ic_decorate_05,
                R.drawable.ic_decorate_06,
                R.drawable.ic_decorate_07,
                R.drawable.ic_decorate_08,
                R.drawable.ic_decorate_10,
                R.drawable.ic_decorate_11,
                R.drawable.ic_decorate_12,
                R.drawable.ic_decorate_13,
                R.drawable.ic_decorate_14,
                R.drawable.ic_decorate_15,
                R.drawable.ic_decorate_16,
                R.drawable.ic_decorate_17,
                R.drawable.ic_decorate_18,
                R.drawable.ic_decorate_19,
                R.drawable.ic_decorate_21,
                R.drawable.ic_decorate_22,
                R.drawable.ic_decorate_23,
                R.drawable.ic_decorate_25,
                R.drawable.ic_decorate_26,
                R.drawable.ic_ball_02,
                R.drawable.ic_cartoon_sun,
                R.drawable.ic_chatlicorne,
                R.drawable.ic_cipynormal,
                R.drawable.ic_clem15_honte,
                R.drawable.ic_cute_whale_colored,
                R.drawable.ic_droplet_water_0100,
                R.drawable.ic_kawaii_star,
                R.drawable.ic_mix_and_match_characters4,
                R.drawable.ic_owl3,
                R.drawable.ic_pinkrabbit,
                R.drawable.ic_smiley_salute,
                R.drawable.ic_greendino_remix
        };
        return drawables;
    }
    public static int[] getResourceIconValentine(){

        int[] drawables =  {
                R.drawable.ic_rose_01,
                
                R.drawable.ic_rose_02,
                R.drawable.ic_rose_03,
                R.drawable.ic_rose_04,
                R.drawable.ic_rose_05,
                R.drawable.ic_rose_06,


                
                R.drawable.ic_valentine_01,
                R.drawable.ic_valentine_02,
               /* R.drawable.ic_valentine_03,*/
                R.drawable.ic_valentine_04,
                R.drawable.ic_valentine_05,
                R.drawable.ic_valentine_06,
                R.drawable.ic_valentine_07,
                R.drawable.ic_valentine_08,
                R.drawable.ic_valentine_09



        };
        return drawables;
    }
}
