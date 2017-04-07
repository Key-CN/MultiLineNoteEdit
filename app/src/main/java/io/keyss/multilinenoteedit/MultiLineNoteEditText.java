package io.keyss.multilinenoteedit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Key on 2017/4/7 14:25
 * email: MrKey.K@gmail.com
 * description:
 */

public class MultiLineNoteEditText extends android.support.v7.widget.AppCompatEditText {

    /**
     * 提前画的线的条数,为最小行数
     */
    private int drawLine;
    /**
     * 为了不让指针压线太明显，将线下移的像素，可根据字体大小和行间距自己调整
     */
    private float lineDis;

    private Rect mRect;
    private Paint mPaint;

    public MultiLineNoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        lineDis = getTextSize() / 6f + getLineSpacingExtra();
        drawLine = this.getMinLines();
        Log.d("行距", getLineSpacingExtra() + "");
        Log.d("getTextSize", getTextSize() + "");
        Log.d("minLine", getMinLines() + "");
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStrokeWidth(3.5F);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0x60000000);
    }

    /**
     * 用代码设置最小行数,可以直接在XML里设置
     * @param lines 行数
     */
    public void setNotesMinLines(int lines){
        this.drawLine = lines;
        setMinLines(lines);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = mPaint;
        int basicLine = 0;
        //第一次画第一条线。以后每次输入，换行时仍然检测，继续画线
        for (int i = 0; i < count; i++) {
            int baseLine = getLineBounds(i, r);
            basicLine = baseLine;
            canvas.drawLine(r.left, baseLine + lineDis, r.right, baseLine + lineDis, paint);
        }
        //根据判定条件，画出固定条数的线,从第二套开始画
        if(count < drawLine){
            for (int j = 1; j < drawLine; j++) {
                int baseline = basicLine+j*getLineHeight();
                canvas.drawLine(r.left, baseline + lineDis, r.right, baseline + lineDis, paint);
            }
        }
        super.onDraw(canvas);
    }
}
