package com.cameraapp;

import androidx.camera.core.ImageProxy;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;

import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.media.Image;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Base64;

import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

public class QRCodeFrameProcessorPlugin extends FrameProcessorPlugin {
   // private final YuvToRgbConverter yuvToRgbConverter;
    private boolean frontDevice = false;

  @Override
  public Object callback(ImageProxy image, Object[] params) {
      
    // code goes here

      String orientation = (String)params[0];
      frontDevice = ((String)params[1]).equals("yes");
      Bitmap rgbFrameBitmap = toBitMap(image.getImage());
      rgbFrameBitmap = rotateBitMap(rgbFrameBitmap,getRotateDegree(orientation));

      return toBase64(rgbFrameBitmap);
  }

  Bitmap toBitMap(Image image)
  {
      Bitmap rgbFrameBitmap;
      int[] cachedRgbBytes = new int[image.getWidth() * image.getHeight()];
      cachedRgbBytes = ImageUtils.convertImageToBitmap(image, cachedRgbBytes, null);
      rgbFrameBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
      rgbFrameBitmap.setPixels(cachedRgbBytes,0,image.getWidth(), 0, 0,image.getWidth(), image.getHeight());

      return rgbFrameBitmap;
  }

  String toBase64(Bitmap img)
  {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      img.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
      byte[] byteArray = byteArrayOutputStream .toByteArray();
      String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
      return encoded;
  }


  Bitmap rotateBitMap(Bitmap img,int degree)
  {
      Matrix matrix = new Matrix();

      matrix.postRotate(degree);

      Bitmap scaledBitmap = Bitmap.createScaledBitmap(img, img.getWidth(), img.getHeight(), true);

      Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

      return rotatedBitmap;
  }

  int getRotateDegree(String orientation)
  {
      if(orientation.toLowerCase().equals("portrait")) {
          if(frontDevice)
          return 270;
          return 90;
      }
      if(orientation.toLowerCase().equals("landscape-right"))
          return 180;

      return 0;
  }


  QRCodeFrameProcessorPlugin(Context context) {
    super("scanQRCodes");
    //  yuvToRgbConverter =  new YuvToRgbConverter(context);
  }

}