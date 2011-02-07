/*
 * Created on May 8, 2005
 *
 */
package chess.media;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;


import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

/**
 * @author Arvydas Bancewicz
 *
 */
public class Surface {

  public static final byte COLOR    = 1;
  public static final byte TEXTURE  = 2;
  public static final byte GRADIENT = 3;
  
  public byte    mode;          // one of the above
  public Color   color;         // plain color / first gradient color
  public String  texture;       // texture file name
  public Color   gradientColor; // seconds gradient color
  public boolean reversed;      // reverse gradient colors ?
  public boolean cyclic;        // repeat gradient ?
  
  public float x1,y1,x2,y2;
  public Surface() {
    this(COLOR,null,null);
  }
  
  public Surface(byte mode, Color color, String texture) {
    this.mode    = mode;
    this.color   = color;
    this.texture = texture;
    x1 = y1 = 0.0f;
    y2 = y2 = 1.0f;
    cyclic = false;
  }
  
  public static Surface newColor(Color color) {
    return new Surface(COLOR,color,null);
  }
  
  public static Surface newColor(int r, int g, int b) {
    return new Surface(COLOR,new Color(r,b,b),null); 
  }
  
  public static Surface newGradient(Color c1, Color c2) {
    Surface result = new Surface(GRADIENT,c1,null);
    result.gradientColor = c2;
    return result;
  }
  public static Surface newTexture() {
    return new Surface(TEXTURE,Color.BLUE,null);
  }

  
  public GradientPaint getGradientPaint(float offx, float offy, float scalex, float scaley) {
    if (reversed)
      return new GradientPaint(offx+x1*scalex,offy+y1*scaley, gradientColor,
          offx+x2*scalex,offy+y2*scaley, color, cyclic);
    else
      return new GradientPaint(offx+x1*scalex,offy+y1*scaley, color,
          offx+x2*scalex,offy+y2*scaley, gradientColor, cyclic);
      
  }
  public TexturePaint getTexturePaint(float offx, float offy, float scalex, float scaley) {
    Image image = BoardMedia.defaultLargePieces.getImage((byte)1,true);
    BufferedImage img =
      new BufferedImage(image.getWidth(null), image.getHeight(null),
          BufferedImage.TYPE_INT_RGB);
    
    //BufferedImage img = (BufferedImage) Toolkit.getDefaultToolkit().getImage(getClass().getResource("media/images/back.png"));
    Rectangle2D anchor = new Rectangle2D.Float(offx,offy, scalex, scaley);
    return new TexturePaint(img,anchor);
  }
  
  public Paint getPaint(float offx, float offy, float scalex, float scaley) {
    switch (mode) {
      case COLOR: return color;
      case GRADIENT: return getGradientPaint(offx,offy,scalex,scaley);
    }
    return null;
  }
  
  public Image getTexture() {
    return null;
  }
  
}
