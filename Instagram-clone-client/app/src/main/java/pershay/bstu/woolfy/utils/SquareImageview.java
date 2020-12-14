package pershay.bstu.woolfy.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class SquareImageview extends AppCompatImageView {

    public SquareImageview(Context context){
        super(context);
    }

    public SquareImageview(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public SquareImageview(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, width);
    }
}
