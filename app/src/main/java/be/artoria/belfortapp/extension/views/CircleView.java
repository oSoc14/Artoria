/*
Copyright 2014 David Morrissey, Adapted by Laurens De Graeve

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package be.artoria.belfortapp.extension.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Collection;

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.activities.MonumentDetailActivity;
import be.artoria.belfortapp.app.PrefUtils;
import be.artoria.belfortapp.extension.CircledPOI;
import be.artoria.belfortapp.viewpager.ViewPagerActivity;

public class CircleView extends SubsamplingScaleImageView {

    private int strokeWidth;
    private Collection<CircledPOI> circles;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        strokeWidth = (int)(density/70f);
        final GestureDetector gestureDetector = new GestureDetector(PrefUtils.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                /* This handler opens the associated point of interest */
                if (isImageReady()) {
                    PointF sCoord = viewToSourceCoord(e.getX(), e.getY());
                    // Integer > float calculations.
                    final int x = (int) sCoord.x;
                    final int y = (int) sCoord.y;
                    /* Linearly search all circles to see if any match.*/
                    for(CircledPOI cpoi: circles){
                        // Eucleadian distance, taking the square root is a waste of time.
                        final int distance = (cpoi.x - x) * (cpoi.x - x) + (cpoi.y - y) * (cpoi.y - y);
                        final int radius = (int) (getSWidth() * cpoi.radius);
                        if (distance <= radius*radius){
                            getContext().startActivity(MonumentDetailActivity.newIntent(getContext(),cpoi.poi,true));
                            break;
                            }
                    }
                } else {
                    Toast.makeText(PrefUtils.getContext(), R.string.panormanotready, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            @Override
            public void onLongPress(MotionEvent e) {
                onSingleTapConfirmed(e);
            }
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return onSingleTapConfirmed(e);
            }
        });



        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public void addCircles(Collection<CircledPOI> circles){
        this.circles = circles;
    }

    public Collection<CircledPOI> getCircles() {
        return circles;
    }
    /* Pre allocated item, apparently better for performance */

    final Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw circles before image is ready so it doesn't move around during setup.
        if (!isImageReady()) {
            return;
        }

        paint.reset();
        for(CircledPOI circle : circles) {
            final PointF vCenter = sourceToViewCoord(circle.x, circle.y);
            final float radius = (getScale() * getSWidth()) * circle.radius;
            paint.setAntiAlias(true);
            paint.setStyle(Style.STROKE);
            paint.setStrokeCap(Cap.ROUND);
            paint.setStrokeWidth(strokeWidth * 2);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(vCenter.x, vCenter.y, radius, paint);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(Color.argb(255, 51, 181, 229));
            canvas.drawCircle(vCenter.x, vCenter.y, radius, paint);
        }
    }

}
