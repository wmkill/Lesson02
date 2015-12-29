package com.dever.lesson02;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;


/**
 * Created by admin on 2015/12/29.
 */
public class CircleTansForm implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(
                source,
                Shader.TileMode.CLAMP,//MIRROR:镜像
                Shader.TileMode.CLAMP));
        new Canvas(bitmap).drawCircle(source.getWidth()/2,source.getHeight()/2,source.getHeight()/2,paint);
        source.recycle();//把原图的资源释放掉，否则内存会吃紧
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
