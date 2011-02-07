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
 * Created on Nov 20 2005
 *
 */

package chess.core;

import java.awt.*;
import java.awt.image.*;

import javax.swing.GrayFilter;

/**
 * ImageManipulator is used to manipulate an image, such as brighten or darken
 * the image.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class ImageManipulator extends RGBImageFilter {

    private boolean brighten;
    private int percent;
    
    ImageManipulator(boolean brighten, int percent) {
        this.brighten = brighten;
        this.percent = percent;
        canFilterIndexColorModel = true;
    }
    
    public int filterRGB(int x, int y, int rgb) {
        return (rgb & 0xff000000) |
               (filter(rgb >> 16) << 16) |
               (filter(rgb >> 8)  << 8 ) |
               (filter(rgb));
    }

    private int filter(int color) {
        color = color & 0xff;
        if (brighten) {
            color = (255 - ((255 - color) * (100 - percent) / 100));
        } else {
            color = (color * (100 - percent) / 100);
        }
	
        if (color < 0) color = 0;
        if (color > 255) color = 255;
        return color;
    }
    

    public static Image brighten(Image img) {
        return manipulate(img, true, 20);
    }
    

    public static Image darken(Image img) {
        return manipulate(img, false, 10);
    }
    

    public static Image gray(Image img) {
        return GrayFilter.createDisabledImage(img);
    }

    public static Image manipulate(Image img, boolean brighten, int percent) {
    	ImageFilter filter = new ImageManipulator(brighten, percent);
    	ImageProducer prod = new FilteredImageSource(img.getSource(), filter);
    	return Toolkit.getDefaultToolkit().createImage(prod);
    }
}