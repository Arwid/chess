/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*                              Java Chess                                      *
*                 Copyright (C) 2005  Arvydas Bancewicz                        *
*                                                                              *
*    This program is free software; you can redistribute it and/or modify      *
*    it under the terms of the GNU General Public License as published by      *
*    the Free Software Foundation; either version 2 of the License, or         *
*    (at your option) any later version.                                       *
*                                                                              *
*    This program  is distributed in the hope that it will be useful,          *
*    but WITHOUT ANY WARRANTY; without even the implied warranty of            *
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
*    GNU General Public License for more details.                              *
*                                                                              *
*    You should have received a copy of the GNU General Public License         *
*    along with Java Chess; if not, write to the Free Software                 *
*    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA *
*                                                                              *                                                       *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 * Created on Mar 27, 2005
 *
 */

package chess.media;

import java.applet.Applet;
import java.awt.*;
import java.util.Vector;

import chess.core.Constants;

public final class BoardMedia implements Constants{
  //private Image[] arrow;
  //protected Image circle;
  //private Image block;
  //private AudioClip pieceDrop;
  //private AudioClip pieceClick;
    public final static BoardMedia im;
    
    
    private final static Image largeLogo;
    private final static Image logo;
    
    // Default or Factory pieces
    
    // All image sets
    private final static PieceImageSet imageSet1;
    private final static PieceImageSet imageSet2;
    private final static PieceImageSet imageSet3;
    private final static PieceImageSet imageSet4;
    private final static PieceImageSet imageSet5;
    
    // Vector containing all image sets
    public final static Vector imageSets;
    
    // Different board set types
    public final static int set_1 = 1;
    public final static int set_2 = 2;
    public final static int set_3 = 3;
    public final static int set_4 = 4;
    public final static int set_5 = 5;
    
    private PieceImageSet originalLargePieces;
    public PieceImageSet largePieces;
    public PieceImageSet smallPieces;
    
    public Image cell;
    public Image cell2;
    
    private int set;
    
    static {
      im = new BoardMedia();
      imageSet1 = im.new PieceImageSet(true,set_1);
      imageSet1.setName("Far cry");
      imageSet2 = im.new PieceImageSet(true,set_2,2);
      imageSet2.setName("3D retro");
      imageSet3 = im.new PieceImageSet(true,set_3,2);
      imageSet3.setName("3D standard");
      imageSet4 = im.new PieceImageSet(true,set_4);
      imageSet4.setName("Base lemon");
      imageSet5 = im.new PieceImageSet(true,set_5);
      imageSet5.setName("Base default");
      
      // Put image sets into vector imageSets
      imageSets = new Vector();
      imageSets.add(imageSet1);
      imageSets.add(imageSet2);
      imageSets.add(imageSet3);
      imageSets.add(imageSet4);
      imageSets.add(imageSet5);
      
      largeLogo = getImage("images/sign.png");
      logo = getImage("images/logo.png");
    }
    
    public static Image getLargeLogo() {
      return largeLogo;
    }
    
    public static Image getLogo() {
      return logo;
    }
    
    private static Image getImage(String file) {
      
      // GetImage
      Image image = Toolkit.getDefaultToolkit().getImage(BoardMedia.class.getResource(file));
      
      // Load image
      MediaTracker tracker = new MediaTracker(new Applet());
      tracker.addImage(image,0);
      
      try {
        tracker.waitForAll();
      } catch(InterruptedException ie){}
      if (tracker.isErrorAny()) {
        System.err.println("Error while loading image!");
      }
      
      return image;
    }
    
    public BoardMedia() {}
    
    public BoardMedia(int set) {
      
      setPieceSet(set);
      
      System.out.println("Start piece y scale : " + largePieces.getPieceYScale());
      //smallPieces = defaultSmallPieces;
      
      cell = Toolkit.getDefaultToolkit().getImage(getClass().getResource("media/images/back.png"));
      cell2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("media/images/back2.png"));
    }
    
    public int getSet() {
      return set;
    }
    
    public void setPieceSet(int set) {
      this.set = set;
      switch (set) {
        case set_1:
          originalLargePieces = imageSet1;
          break;
        case set_2:
          originalLargePieces = imageSet2;
          break;
        case set_3:
          originalLargePieces = imageSet3;
          break;
        case set_4:
          originalLargePieces = imageSet4;
          break;
        case set_5:
          originalLargePieces = imageSet5;
          break;
      }
      largePieces = originalLargePieces;
    }
    
    private PieceImageSet copyImageSet(PieceImageSet set) {
      PieceImageSet newSet = new PieceImageSet();
      newSet.setYScale(set.getPieceYScale());
      for (byte type = TYPE_PAWN; type <= TYPE_KING; type++) {
        newSet.setImage(set.getImage(type,true),type,true);
        newSet.setImage(set.getImage(type,false),type,false);
      }
      return newSet;
    }
    
    public double getPieceYScale() {
      return largePieces.getPieceYScale();
    }
    
    public void scaleImages(double scale) {
      largePieces.scaleImages(scale);
    }
    
    public void scaleImages(int width, int height) {
      height = (int) (height*largePieces.getPieceYScale());
      largePieces = copyImageSet(originalLargePieces);
      largePieces.scaleImages(width, height);
    }
    
    public Image getImage(boolean white, byte type) {
      Image image = largePieces.getImage(type, white);
      if (image != null)
      	return image;
      return null;
    }
    
    public static BoardMedia getInstance() { 
      return im;
    }

    public class PieceImageSet {
      
      private Image whitePawn;
      private Image whiteKnight;
      private Image whiteBishop;
      private Image whiteRook;
      private Image whiteQueen;
      private Image whiteKing;
      
      private Image blackPawn;
      private Image blackKnight;
      private Image blackBishop;
      private Image blackRook;
      private Image blackQueen;
      private Image blackKing;
      
      private boolean enabled;
      
      private double piece_yScale = 1;
      
      private int set;
      
      private String name = "empty";
      
      public PieceImageSet() {
        // Default Constructor
        enabled = true;
        set = set_5;
      }
      
      public int getSet() {
        return set;
      }
      
      public void setName(String name) {
        this.name = name;
      }
      
      public String getName() {
        return name;
      }
      
      public double getPieceYScale() {
        return piece_yScale;
      }
      
      public PieceImageSet(boolean enabled, int set, double yScale) {
        this.enabled = enabled;
        this.set = set;
        piece_yScale = yScale;
        loadImages();
      }
      
      public PieceImageSet(boolean enabled, int set) {
        this.enabled = enabled;
        this.set = set;
        loadImages();
      }
      
      public void setYScale(double scale) {
        piece_yScale = scale;
      }
      
      public double getYScale(int scale) {
        return piece_yScale;
      }
      
      private void loadImages() { 
        
        for (byte type=TYPE_PAWN; type<=TYPE_KING; type++) {
          setImage(retrieveImage(type,true),type,true);
          setImage(retrieveImage(type,false),type,false);
        }
        
        waitForImages();
        //scaleImages(0.60);
        //waitForImages();
      }
      
      private void waitForImages() {
        // Wait for images to load
        MediaTracker tracker = new MediaTracker(new Applet());
        tracker.addImage(whitePawn,1);
        tracker.addImage(whiteKnight,2);
        tracker.addImage(whiteBishop,3);
        tracker.addImage(whiteRook,4);
        tracker.addImage(whiteQueen,5);
        tracker.addImage(whiteKing,6);
        
        tracker.addImage(blackPawn,7);
        tracker.addImage(blackKnight,8);
        tracker.addImage(blackBishop,9);
        tracker.addImage(blackRook,10);
        tracker.addImage(blackQueen,11);
        tracker.addImage(blackKing,12);
        
        try {
          tracker.waitForAll();
        } catch(InterruptedException ie){}
        if (tracker.isErrorAny()) {
          System.err.println("Error while loading images!");
        }
      }
      
      private Image retrieveImage(byte type, boolean white) {
        //byte set = 5;
        //System.out.println("set"+set);
        String fileName = "images/set/"+set+"/";
        String ext;
        //if(set == "small")
        //  ext = ".png";
        //else
          ext = ".gif";
        switch(type) {
          case TYPE_PAWN:
            if(white) fileName += "wp"+ext;
            else      fileName += "bp"+ext;
            break;
          case TYPE_KNIGHT:
            if(white) fileName += "wn"+ext;
            else      fileName += "bn"+ext;
            break;
          case TYPE_BISHOP:
            if(white) fileName += "wb"+ext;
            else      fileName += "bb"+ext;
            break;
          case TYPE_ROOK:
            if(white) fileName += "wr"+ext;
            else      fileName += "br"+ext;
            break;
          case TYPE_QUEEN:
            if(white) fileName += "wq"+ext;
            else      fileName += "bq"+ext;
            break;
          case TYPE_KING:
            if(white) fileName += "wk"+ext;
            else      fileName += "bk"+ext;
            break;
        }
        
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileName));
        
        double scale = 0.9;
        int width = 70;//(int)(scale*image.getWidth(null));
        int height = 70;//(int)(scale*image.getHeight(null));
        Image outImage = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        //return outImage;
        //if(!enabled)
        //  image = ImageManipulator.manipulate(image,true,80);
        return image;
      }
      
      private void setImage(Image image, byte type, boolean white) {
        switch(type) {
          case TYPE_PAWN :
            if (white) whitePawn = image;
            else       blackPawn = image;
            break;
          case TYPE_KNIGHT :
            if (white) whiteKnight = image;
            else       blackKnight = image;
            break;
          case TYPE_BISHOP :
            if (white) whiteBishop = image;
            else       blackBishop = image;
            break;
          case TYPE_ROOK:
            if (white) whiteRook = image;
            else       blackRook = image;
            break;
          case TYPE_QUEEN:
            if (white) whiteQueen = image;
            else       blackQueen = image;
            break;
          case TYPE_KING:
            if (white) whiteKing = image;
            else       blackKing = image;
            break; 
        }
      }
      
      public Image getImage(byte type, boolean white) {
        switch(type) {
          case TYPE_PAWN:
            if(white) return whitePawn;
            else      return blackPawn;
          case TYPE_KNIGHT:
            if(white) return whiteKnight;
            else      return blackKnight;
          case TYPE_BISHOP:
            if(white) return whiteBishop;
            else      return blackBishop;
          case TYPE_ROOK:
            if(white) return whiteRook;
            else      return blackRook;
          case TYPE_QUEEN:
            if(white) return whiteQueen;
            else      return blackQueen;
          case TYPE_KING:
            if(white) return whiteKing;
            else      return blackKing;
        }
        return null;
      }
      
      public void scaleImages(double scale) {
        for (byte type=TYPE_PAWN; type<=TYPE_KING; type++) {
          scaleImage(scale, type, true);
          scaleImage(scale, type, false);
        }
        waitForImages();
      }
      
      private void scaleImage(double scale, byte type, boolean white) {
        Image inImage = getImage(type,white);
        int newWidth = (int)(inImage.getWidth(null)*scale);
        int newHeight = (int)(inImage.getHeight(null)*scale);
        Image image = inImage.getScaledInstance(newWidth,newHeight,Image.SCALE_SMOOTH);
        setImage(image,type,white);
      }
      
      public void scaleImages(int width, int height) {
        for (byte type=TYPE_PAWN; type<=TYPE_KING; type++) {
          scaleImage(width, height, type, true);
          scaleImage(width, height, type, false);
        }
        waitForImages();
      }
      
      private void scaleImage(int width, int height, byte type, boolean white) {
        /*
        int value;
        
        try   { value = ChessSettings.getImageScaleQuality(); } 
        catch (ExceptionInInitializerError e) { value = Image.SCALE_DEFAULT; } 
        catch (NoClassDefFoundError e)        { value = Image.SCALE_DEFAULT; }
        */
        Image inImage = getImage(type,white);
        Image image = inImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        setImage(image,type,white);
      }
    
    }
}

