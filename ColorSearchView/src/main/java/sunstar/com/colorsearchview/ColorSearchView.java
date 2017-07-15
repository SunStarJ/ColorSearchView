package sunstar.com.colorsearchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Sun.Star on 2017/7/12.
 */

public class ColorSearchView extends View {
    private Paint backGroundPaind;
    private Paint searchPaint;
    private int searchPaintSize;
    private int bgColor = R.color.colorNo;
    private int searchColor;
    private int changeColor1,changeColor2,changeColor3;


    public ColorSearchView(Context context) {
        this(context, null);
    }

    public ColorSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ColorSearchView);
        bgColor = array.getColor(R.styleable.ColorSearchView_bg_color,getResources().getColor(R.color.colorNo));
        searchColor = array.getColor(R.styleable.ColorSearchView_searchview_color, getResources().getColor(R.color.colorGray));
        changeColor1 = array.getColor(R.styleable.ColorSearchView_change_color_frist, searchColor);
        changeColor2 = array.getColor(R.styleable.ColorSearchView_change_color_second, searchColor);
        changeColor3 = array.getColor(R.styleable.ColorSearchView_change_color_third, searchColor);
        initPaint();
    }

    private void initPaint() {
        searchPaint = new Paint();
        backGroundPaind = new Paint();
        backGroundPaind.setColor(bgColor);
        backGroundPaind.setAntiAlias(true);
        backGroundPaind.setStyle(Paint.Style.FILL);
        searchPaint.setColor(searchColor);
        searchPaint.setAntiAlias(true);
        searchPaint.setStyle(Paint.Style.STROKE);
    }
    int colorControlIndex = 0;
    int centerX;
    int centerY;
    int radiusSize = 0;
    int backRadius;
    @Override
    protected void onDraw(Canvas canvas) {
        searchPaint.setStrokeWidth(searchPaintSize);
        if (getWidth() / 2 > getHeight() / 2) {
            backRadius = getHeight() / 2;
        } else {
            backRadius = getWidth() / 2;
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, backRadius, backGroundPaind);
        canvas.drawCircle(centerX, centerY, radiusSize / 3, searchPaint);
        if (!isSearch) {
            canvas.drawLine((int) (centerX + (radiusSize / 3 * Math.sqrt(2) / 2)), (int) (centerY + (radiusSize / 3 * Math.sqrt(2) / 2)),
                    (int) (centerX + ((radiusSize - getWidth() / 15) * Math.sqrt(2) / 2)),
                    (int) (centerY + ((radiusSize - getHeight() / 15) * Math.sqrt(2) / 2)), searchPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        int width;
        width = widthSize;
        height = heightSize;
        if(height>width){
            height = width;
        }else{
            width = height;
        }
        centerX = width / 2 - width / 15;
        centerY = height / 2 - height / 15;
        Log.d("SearchView", "width:" + width);
        Log.d("SearchView", "height:" + height);
        if (width / 2 > height / 2) {
            radiusSize = height / 2;
        } else {
            radiusSize = width / 2;
        }
        searchPaintSize = width/20;
        setMeasuredDimension(width,height);
    }

    private boolean isSearch;

    public void searchComplete(){
        isSearch = false;
        radiusSize = getWidth() / 2;
        centerX = getWidth() / 2 - getWidth() / 15;
        centerY = getHeight() / 2 - getHeight() / 15;
        searchPaintSize = getWidth()/20;
        if(handler!=null&&run!=null){
            handler.removeCallbacks(run);
        }

        shou = true;
        handler = null;
        searchPaint.setColor(searchColor);
        invalidate();
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (centerX != getWidth()/2  && centerY != getHeight() / 2) {
                searchChangeAdd();
                if(getWidth()>=100){
                    handler.postDelayed(this, 100);
                }else{
                    handler.postDelayed(this, 130);
                }

            }else{
                Log.d("SearchView", "更改属性");
                centerChange();
                if(getWidth()>=100){
                    handler.postDelayed(this, 100);
                }else{
                    handler.postDelayed(this, 130);
                }
            }
        }
    };


    public void search() {
        if(isSearch == false){
            isSearch = true;
            if(handler == null){
                handler = new Handler();
            }
            handler.postDelayed(run, 20);
        }else{
        }
    }

    boolean shou = true;
    private void centerChange() {
        if(shou){
            radiusSize -=10;
        }else{
            radiusSize +=10;
        }
        if(radiusSize/3>=backRadius - 20-(searchPaintSize/2)){
            shou = true;
        }else if(radiusSize/3<=0){
            shou = false;
            colorControlIndex+=1;
            if(colorControlIndex<4){
                changeColor();
            }else{
                colorControlIndex = 0;
                changeColor();
            }
        }
        invalidate();
    }

    private void changeColor() {
        Log.d("SearchView", "colorControlIndex:" + colorControlIndex);
        switch (colorControlIndex){
            case 0:
                searchPaint.setColor(searchColor);
                break;
            case 1:
                searchPaint.setColor(changeColor1);
                break;
            case 2:
                searchPaint.setColor(changeColor2);
                break;
            case 3:
                searchPaint.setColor(changeColor3);
                break;
        }
    }

    Handler handler ;
    int count = 0 ;
    private void searchChangeAdd() {
        if(count!=1){
            count+=1;
        }else{
            searchPaintSize +=1;
            count= 0;
        }
        centerX += 1;
        centerY += 1;
        if(radiusSize/3<backRadius - 20-(searchPaintSize/2)){
            radiusSize +=10;
        }
        invalidate();
    }

}
