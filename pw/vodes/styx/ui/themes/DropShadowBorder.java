package pw.vodes.styx.ui.themes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.border.Border;

import com.jhlabs.image.GaussianFilter;

public class DropShadowBorder implements Border {

    protected static final int SHADOW_SIZE = 4;
    protected static final Map<Component, DropShadowBorder.CachedBorder> BORDER_CACHE = new WeakHashMap<Component, CachedBorder>(5);
    private boolean fillContentArea;
    private int shadowSize;
    private float shadowOpacity;
    private Color shadowColor;

    public DropShadowBorder() {

        this(SHADOW_SIZE, Color.BLACK, 0.5f, true);

    }

    public DropShadowBorder(boolean paintContentArea) {

        this(SHADOW_SIZE, Color.BLACK, 0.5f, paintContentArea);

    }

    public DropShadowBorder(int shadowSize) {

        this(shadowSize, Color.BLACK, 0.5f, true);

    }

    public DropShadowBorder(Color shadowColor) {

        this(SHADOW_SIZE, shadowColor, 0.5f, true);

    }

    public DropShadowBorder(int shadowSize, Color showColor) {

        this(shadowSize, showColor, 0.5f, true);

    }

    public DropShadowBorder(int shadowSize, float opacity) {

        this(shadowSize, Color.BLACK, opacity, true);

    }

    public DropShadowBorder(Color shadowColor, float opacity) {

        this(SHADOW_SIZE, shadowColor, opacity, true);

    }

    public DropShadowBorder(int shadowSize, Color shadowColor, float opacity) {

        this(shadowSize, shadowColor, opacity, true);

    }

    public DropShadowBorder(int shadowSize, boolean paintContentArea) {

        this(shadowSize, Color.BLACK, 0.5f, paintContentArea);

    }

    public DropShadowBorder(Color shadowColor, boolean paintContentArea) {

        this(SHADOW_SIZE, shadowColor, 0.5f, paintContentArea);

    }

    public DropShadowBorder(int shadowSize, Color showColor, boolean paintContentArea) {

        this(shadowSize, showColor, 0.5f, paintContentArea);

    }

    public DropShadowBorder(int shadowSize, float opacity, boolean paintContentArea) {

        this(shadowSize, Color.BLACK, opacity, paintContentArea);

    }

    public DropShadowBorder(Color shadowColor, float opacity, boolean paintContentArea) {

        this(SHADOW_SIZE, shadowColor, opacity, paintContentArea);

    }

    public DropShadowBorder(int shadowSize, Color showColor, float opacity, boolean paintContentArea) {

        setShadowSize(shadowSize);
        setShadowColor(showColor);
        setShadowOpacity(opacity);
        setFillContentArea(paintContentArea);

    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    public void setShadowOpacity(float shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public float getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowSize(int size) {

        shadowSize = size;

    }

    public int getShadowSize() {

        return shadowSize;

    }

    public static GraphicsConfiguration getGraphicsConfiguration() {

        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    }

    public static BufferedImage createCompatibleImage(int width, int height) {

        return createCompatibleImage(width, height, Transparency.TRANSLUCENT);

    }

    public static BufferedImage createCompatibleImage(int width, int height, int transparency) {

        BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;

    }

    public static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {

        int imgWidth = imgSource.getWidth() + (size * 2);
        int imgHeight = imgSource.getHeight() + (size * 2);

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgMask.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int x = Math.round((imgWidth - imgSource.getWidth()) / 2f);
        int y = Math.round((imgHeight - imgSource.getHeight()) / 2f);
        g2.drawImage(imgSource, x, y, null);
        g2.dispose();

        // ---- Blur here ---

        BufferedImage imgGlow = generateBlur(imgMask, size, color, alpha);
//
//    BufferedImage imgGlow = ImageUtilities.createCompatibleImage(imgWidth, imgHeight);
//    g2 = imgGlow.createGraphics();
//
//    g2.drawImage(imgMask, 0, 0, null);
//    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
//    g2.setColor(color);
//
//    g2.fillRect(x, y, imgSource.getWidth(), imgSource.getHeight());
//    g2.dispose();
//
//    imgGlow = filter.filter(imgGlow, null);

        // ---- Blur here ----

//    imgGlow = ImageUtilities.applyMask(imgGlow, imgMask, AlphaComposite.DST_OUT);

        return imgGlow;

    }

    public static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {

        GaussianFilter filter = new GaussianFilter(size);

        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2d = imgBlur.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.drawImage(imgSource, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2d.setColor(color);

        g2d.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2d.dispose();

        imgBlur = filter.filter(imgBlur, null);

        return imgBlur;

    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        /*
         * Because of the amount of time it can take to render the drop shadow,
         * we cache the results in a static cache, based on the component
         * and the components size.
         * 
         * This allows the shadows to repainted quickly so long as the component
         * hasn't changed in size.
         */

        BufferedImage dropShadow = null;

        DropShadowBorder.CachedBorder cached = BORDER_CACHE.get(c);
        if (cached != null) {

            dropShadow = cached.getImage(c);

        }

        if (dropShadow == null) {

            int shadowSize = getShadowSize();
            float opacity = getShadowOpacity();
            Color color = getShadowColor();

            // Create a blank canvas, from which the actually border can be generated
            // from...
            // The ahadow routine can actually generate a non-rectangular border, but
            // because we don't have a suitable template to run from, we need to 
            // set this up our selves...
            // It would be nice to be able to user the component itself, but this will
            // have to
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.fillRect(0, 0, width - (shadowSize * 2), height - (shadowSize * 2));
            g2d.dispose();

            // Generate the shadow
            BufferedImage shadow = generateShadow(img, shadowSize, getShadowColor(), getShadowOpacity());

            // We need to produce a clipping result, cause the border is painted ontop
            // of the base component...
            BufferedImage clipedShadow = createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d = clipedShadow.createGraphics();
            Shape clip = g2d.getClip();

            // First we create a "area" filling the avaliable space...
            Area area = new Area(new Rectangle(width, height));
            // Then we subtract the space left over for the component
            area.subtract(new Area(new Rectangle(width - (shadowSize * 2), height - (shadowSize * 2))));
            // And then apply the clip
            g2d.setClip(area);
            // Then draw the shadow image
//    g2d.drawImage(shadow, -(shadowSize / 2), -(shadowSize / 2), c);
            g2d.drawImage(shadow, 0, 0, c);
            g2d.setClip(clip);

        if (!c.isOpaque() && isFillContentArea()) {

            area = new Area(new Rectangle(width - (shadowSize * 2), height - (shadowSize * 2)));
            g2d.setColor(c.getBackground());
            g2d.fill(area);

        }

//        g2d.setColor(Color.RED);
//        g2d.drawRect(x, y, width - 1, height - 1);
//
//        g2d.setColor(Color.GREEN);
//        g2d.drawRect(x, y, width - (shadowSize * 2), height - (shadowSize * 2));

            g2d.dispose();

            dropShadow = clipedShadow;
            BORDER_CACHE.put(c, new CachedBorder(dropShadow, c.getSize()));

        }

        g.drawImage(dropShadow, x, y, c);

//    if (!c.isOpaque() && isFillContentArea()) {
//
//        Graphics2D g2d = (Graphics2D) g;
//        
//        Area area = new Area(new Rectangle(width - (shadowSize * 2), height - (shadowSize * 2)));
//        g2d.setColor(c.getBackground());
//        g2d.fill(area);
//
//    }

//    g.setColor(Color.MAGENTA);
//    g.drawRect(x + 1, y + 1, width - (shadowSize * 2) - 1, height - (shadowSize * 2) - 1);

    }

    public Insets getBorderInsets(Component cmpnt) {
        return new Insets(0, 0, getShadowSize() * 2, getShadowSize() * 2);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * Returns if the content area should be painted by this border when the
     * parent component is opaque...
     *
     * The problem is, the paintComponent method will paint the WHOLE component
     * background, including the border area. This is a reasonable assumption to
     * make, but it makes the shadow border really show up when the parent
     * component is a different color.
     *
     * This allows the border to take control of that fact.
     *
     * When using it, you will need to try and make this the first border to get
     * painted though :P
     *
     * @return
     */
    public boolean isFillContentArea() {
        return fillContentArea;
    }

    public void setFillContentArea(boolean fill) {

        fillContentArea = fill;

    }

    protected class CachedBorder {

        private BufferedImage image;
        private Dimension size;

        public CachedBorder(BufferedImage border, Dimension size) {

            this.image = border;
            this.size = size;

        }

        public BufferedImage getImage(Component comp) {

            BufferedImage dropShadow = null;

            if (comp.getSize().equals(size)) {

                dropShadow = image;

            }

            return dropShadow;

        }
    }
}