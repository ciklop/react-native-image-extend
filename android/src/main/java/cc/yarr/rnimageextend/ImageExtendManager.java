package cc.yarr.rnimageextend;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.image.ReactImageView;

import java.util.Map;

public class ImageExtendManager extends ReactImageManager {
  public static final String REACT_CLASS = "ImageExtendView";
  private static final int COMMAND_GET_PIXEL = 1;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public @Nullable Map<String, Integer> getCommandsMap() {
    return MapBuilder.of(
      "getPixleAt",
      COMMAND_GET_PIXEL);
  }

  @Override
  public void receiveCommand(
    ReactImageView view,
    int commandId,
    @Nullable ReadableArray args) {
    switch (commandId) {
      case COMMAND_GET_PIXEL:
        assert args != null;
        float destX = (float)args.getDouble(0);
        float destY = (float)args.getDouble(1);
        this.getPixelAt(view, destX, destY);
        return;
      default:
        throw new IllegalArgumentException(String.format(
          "Unsupported command %d received by %s.",
          commandId,
          this.getClass().getSimpleName()));
    }
  }

  public void getPixelAt(ReactImageView view, float x, float y) {
    Matrix inverse = new Matrix();
    view.getImageMatrix().invert(inverse);
    float[] touchPoint = new float[] {x, y};
    inverse.mapPoints(touchPoint);
    int xCoord = (int) touchPoint[0];
    int yCoord = (int) touchPoint[1];
    int colorTouched = ((BitmapDrawable)view.getDrawable()).getBitmap().getPixel(xCoord, yCoord);
    Log.d(
      REACT_CLASS,
      String.format(
        "Color: r=%d, g=%d, b=%d",
        Color.red(colorTouched),
        Color.green(colorTouched),
        Color.blue(colorTouched)
      )
    );
  }
}
