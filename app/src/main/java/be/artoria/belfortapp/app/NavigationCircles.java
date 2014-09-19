package be.artoria.belfortapp.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import be.artoria.belfortapp.R;

/**
 * TODO: document your custom view class.
 */
public class NavigationCircles extends View {
    private int selected_color = R.color.color1;
    private int regular_color = R.color.color2;
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private ShapeDrawable circles[];
    private int selected = 0;
    private int numberOfCircles = 1;
    private final static int HEIGHT = 8;
    private final static int single_width = 5;
    private final static int PADDING = 3;


    public NavigationCircles(Context context) {
        super(context);
        init(null, 0);
    }

    public NavigationCircles(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NavigationCircles(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NavigationCircles, defStyle, 0);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.NavigationCircles_exampleDimension,
                mExampleDimension);

        a.recycle();

    }
    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.

        // Draw the example drawable on top of the text.
        for (int i = 0; i < circles.length; i++) {
            if(i == selected) {
                canvas.drawColor(this.selected_color);
            } else {
                canvas.drawColor(this.regular_color);
            }

            circles[i].draw(canvas);
        }

        for(ShapeDrawable circle : circles) {
           /* circle.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            circle.draw(canvas); */
        }
    }




    public void setSelectedCircle(int selected){
        this.selected = selected;
    }


    public void setNumberOfCircles(int numberOfCircles) {
        this.numberOfCircles = numberOfCircles;
        refreshCircles();
    }

    private void refreshCircles() {
        circles = new ShapeDrawable[numberOfCircles];
        for (int i = 0; i < numberOfCircles; i++) {
            circles[i] = new ShapeDrawable( new OvalShape());
            circles[i].setIntrinsicHeight(5);
            circles[i].setIntrinsicWidth(5);
            int paddingLeft = this.paddingLeft + i*single_width + i*PADDING;
            circles[i].setBounds(paddingLeft, paddingTop,
                    paddingLeft + single_width, paddingTop + HEIGHT);
        }
    }
}
