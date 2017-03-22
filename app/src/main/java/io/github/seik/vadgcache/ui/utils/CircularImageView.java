package io.github.seik.vadgcache.ui.utils;

/**
 * Created by Iván
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Custom ImageView for circular images in Android while maintaining the
 * best draw performance and supporting custom borders & selectors.
 */
public class CircularImageView extends AppCompatImageView {

    private static final String TAG = CircularImageView.class.getSimpleName();

    // Configuration variables
    private int canvasSize;

    // Objects used for the actual drawing
    private BitmapShader shader;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private Paint paintSelectorBorder;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes paint objects and sets desired attributes.
     */
    private void init() {
        // Initialize paint objects
        paint = new Paint();
        paint.setAntiAlias(true);
        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintSelectorBorder = new Paint();
        paintSelectorBorder.setAntiAlias(true);

        // Enable software rendering
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Don't draw anything without an image
        if (image == null)
            return;

        // Nothing to draw (Empty bounds)
        if (image.getHeight() == 0 || image.getWidth() == 0)
            return;

        // Update shader if canvas size has changed
        int oldCanvasSize = canvasSize;
        canvasSize = getWidth() < getHeight() ? getWidth() : getHeight();
        if (oldCanvasSize != canvasSize)
            updateBitmapShader();

        // Apply shader to paint
        paint.setShader(shader);

        // Keep track of selectorStroke/border width
        int outerWidth = 0;

        // Get the exact X/Y axis of the view
        int center = canvasSize / 2;

        paint.setColorFilter(null);

        // Draw the circular image itself
        canvas.drawCircle(center + outerWidth, center + outerWidth, ((canvasSize - (outerWidth * 2)) / 2), paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // The parent has not imposed any constraint on the child.
            result = canvasSize;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = canvasSize;
        }

        return (result + 2);
    }

    /**
     * Re-initializes the shader texture used to fill in
     * the Circle upon drawing.
     */
    public void updateBitmapShader() {
        if (image == null)
            return;

        shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        if (canvasSize != image.getWidth() || canvasSize != image.getHeight()) {
            Matrix matrix = new Matrix();
            float scale = (float) canvasSize / (float) image.getWidth();
            matrix.setScale(scale, scale);
            shader.setLocalMatrix(matrix);
        }
    }
}
