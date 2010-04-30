package org.orbisgis.core.renderer.se.stroke;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.gdms.data.DataSource;
import org.orbisgis.core.renderer.se.common.RelativeOrientation;
import org.orbisgis.core.renderer.se.graphic.GraphicCollection;
import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;

public class GraphicStroke extends Stroke{


    public void setGraphicCollection(GraphicCollection graphic){
        this.graphic = graphic;
    }

    public GraphicCollection getGraphicCollection(){
        return graphic;
    }


    public void setLength(RealParameter length){
        this.length = length;
    }

    public RealParameter getLength(){
        return length;
    }

    public void setRelativeOrientation(RelativeOrientation orientation){
        this.orientation = orientation;
    }

    public RelativeOrientation getRelativeOrientation(){
        return orientation;
    }

    @Override
    public void draw(Graphics2D g2, Shape shp, DataSource ds, int fid) throws ParameterException, IOException{
        BufferedImage img = graphic.getGraphic(ds, fid);

        double l;
        if (length != null){
            l = length.getValue(ds, fid);
            if (l<= 0.0){
                // TODO l \in R-* is forbiden ! Should throw, or set l = line.linearLength()
                // for the time, let us l = graphic natural length...
                l = (double)img.getWidth();
            }
        }
        else{
            l = (double)img.getWidth();
        }

        // TODO implements :
        /*
         * dont forget to take into account preGap and postGap !!!
         * Split the line in n part of linear length == l
         * for each part
         *   fetch the point at half the linear length
         *   plot img on this point, according to the orientation
         */
    }

    private GraphicCollection graphic;
    private RealParameter length;
    private RelativeOrientation orientation;
}