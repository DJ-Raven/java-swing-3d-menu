package javaswingdev.menu3d;

import java.awt.Component;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class MenuAnimator {

    private final Animator animator;
    private final Menu3dItem item;
    private boolean show;
    private TimingTarget targetColor1;
    private TimingTarget targetColor2;
    private TimingTarget targetColor3;

    public MenuAnimator(Component com, Menu3dItem item) {
        this.item = item;
        animator = new Animator(300, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    item.animate(fraction);
                } else {
                    item.animate(1f - fraction);
                }
                com.repaint();
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void show() {
        if (!show) {
            startAnimation(true);
        }
    }

    public void hide() {
        if (show) {
            startAnimation(false);
        }
    }

    private void startAnimation(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0);
        }
        this.show = show;
        animator.removeTarget(targetColor1);
        animator.removeTarget(targetColor2);
        animator.removeTarget(targetColor3);
        if (show) {
            targetColor1 = new PropertySetter(item, "background", item.getBackground(), item.getDefaultBackgroundSelected());
            targetColor2 = new PropertySetter(item, "colorShadowTop", item.getBackground(), item.getDefaultColorShadowTopSelected());
            targetColor3 = new PropertySetter(item, "colorShadowLeft", item.getBackground(), item.getDefaultColorShadowLeftSelected());
        } else {
            targetColor1 = new PropertySetter(item, "background", item.getBackground(), item.getDefaultBackground());
            targetColor2 = new PropertySetter(item, "colorShadowTop", item.getBackground(), item.getDefaultColorShadowTop());
            targetColor3 = new PropertySetter(item, "colorShadowLeft", item.getBackground(), item.getDefaultColorShadowLeft());
        }
        animator.addTarget(targetColor1);
        animator.addTarget(targetColor2);
        animator.addTarget(targetColor3);
        animator.start();
    }
}
