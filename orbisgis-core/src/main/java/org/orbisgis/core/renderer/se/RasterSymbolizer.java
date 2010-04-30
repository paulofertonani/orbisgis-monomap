/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.orbisgis.core.renderer.se;

import org.orbisgis.core.renderer.se.raster.OverlapBehavior;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.parameter.color.Categorize2Color;
import org.orbisgis.core.renderer.se.parameter.color.Interpolate2Color;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;
import org.orbisgis.core.renderer.se.raster.Channel;
import org.orbisgis.core.renderer.se.raster.ContrastEnhancement;

/**
 * @ todo implements almost all...
 * @author maxence
 */
public class RasterSymbolizer extends Symbolizer {

    @Override
    public Uom getUom() {
        return Uom.MM;
    }

    public Channel getBlueChannel() {
        return blueChannel;
    }

    public void setBlueChannel(Channel blueChannel) {
        this.blueChannel = blueChannel;
    }

    public Categorize2Color getCategorizedColorMap() {
        return categorizedColorMap;
    }

    public void setCategorizedColorMap(Categorize2Color categorizedColorMap) {
        this.categorizedColorMap = categorizedColorMap;
    }

    public ContrastEnhancement getContrastEnhancement() {
        return contrastEnhancement;
    }

    public void setContrastEnhancement(ContrastEnhancement contrastEnhancement) {
        this.contrastEnhancement = contrastEnhancement;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public Channel getGrayChannel() {
        return grayChannel;
    }

    public void setGrayChannel(Channel grayChannel) {
        this.grayChannel = grayChannel;
    }

    public Channel getGreenChannel() {
        return greenChannel;
    }

    public void setGreenChannel(Channel greenChannel) {
        this.greenChannel = greenChannel;
    }

    public Interpolate2Color getInterpolatedColorMap() {
        return interpolatedColorMap;
    }

    public void setInterpolatedColorMap(Interpolate2Color interpolatedColorMap) {
        this.interpolatedColorMap = interpolatedColorMap;
    }

    public boolean isIsColored() {
        return isColored;
    }

    public void setIsColored(boolean isColored) {
        this.isColored = isColored;
    }

    public RealParameter getOpacity() {
        return opacity;
    }

    public void setOpacity(RealParameter opacity) {
        this.opacity = opacity;
    }

    public AreaSymbolizer getOutline() {
        return outline;
    }

    public void setOutline(AreaSymbolizer outline) {
        this.outline = outline;
    }

    public OverlapBehavior getOverlapBehavior() {
        return overlapBehavior;
    }

    public void setOverlapBehavior(OverlapBehavior overlapBehavior) {
        this.overlapBehavior = overlapBehavior;
    }

    public Channel getRedChannel() {
        return redChannel;
    }

    public void setRedChannel(Channel redChannel) {
        this.redChannel = redChannel;
    }

    public double getShadedReliefFactor() {
        return shadedReliefFactor;
    }

    public void setShadedReliefFactor(double shadedReliefFactor) {
        this.shadedReliefFactor = shadedReliefFactor;
    }

    public boolean isShadedReliefOnlyBrightness() {
        return shadedReliefOnlyBrightness;
    }

    public void setShadedReliefOnlyBrightness(boolean shadedReliefOnlyBrightness) {
        this.shadedReliefOnlyBrightness = shadedReliefOnlyBrightness;
    }

    public boolean isUseInterpolationForColorMap() {
        return useInterpolationForColorMap;
    }

    public void setUseInterpolationForColorMap(boolean useInterpolationForColorMap) {
        this.useInterpolationForColorMap = useInterpolationForColorMap;
    }



    private RealParameter opacity;
    private Channel redChannel;
    private Channel greenChannel;
    private Channel blueChannel;
    private Channel grayChannel;

    private boolean isColored; // true => use red, green and blue channels; false => use gray


    private OverlapBehavior overlapBehavior;


    private Interpolate2Color interpolatedColorMap;
    private Categorize2Color categorizedColorMap;
    
    private boolean useInterpolationForColorMap; // true => interpolatedColorMap, False => CategorizedColorMap

    private ContrastEnhancement contrastEnhancement;
    private double gamma;

    private boolean shadedReliefOnlyBrightness;
    private double shadedReliefFactor;
    
    /*
     * The standard request either a LineSymbolizer or an AreaSymbolizer
     * Since a line symbolizer is an area one witout the fill element, we only provide the latter
     */
    private AreaSymbolizer outline;
    
}