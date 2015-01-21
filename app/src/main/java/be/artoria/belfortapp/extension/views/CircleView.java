/*
Copyright 2014 David Morrissey

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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Arrays;
import java.util.Collection;

public class CircleView extends SubsamplingScaleImageView {

    private int strokeWidth;
    private Collection<CircledPOI> circles = Arrays.asList(
            new CircledPOI(1, 0.1f, 500, 200),
            new CircledPOI(2, 0.2f, 600, 300),
            new CircledPOI(3, 0.3f, 1000, 400)
    );

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        strokeWidth = (int)(density/60f);
    }

    /* Pre allocated items*/

    final Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pin before image is ready so it doesn't move around during setup.
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

    private class CircledPOI{
        // Used for responding to onclick events.
        public final int poi;
        // In percentage of screen, maybe? don't know yet.
        public final float radius;
        public final int x;
        public final int y;

        private CircledPOI(int poi, float radius, int x, int y) {
            this.poi = poi;
            this.radius = radius;
            this.x = x;
            this.y = y;

        }
    }

}
