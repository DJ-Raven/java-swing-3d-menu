package javaswingdev.menu3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class Menu3D extends JComponent {

    public List<Menu3dItem> getItems() {
        return items;
    }

    public int getMenuHeight() {
        return menuHeight;
    }

    public void setMenuHeight(int menuHeight) {
        this.menuHeight = menuHeight;
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getOverIndex() {
        return overIndex;
    }

    public void setOverIndex(int overIndex) {
        this.overIndex = overIndex;
    }

    public int getPressedIndex() {
        return pressedIndex;
    }

    public void setPressedIndex(int pressedIndex) {
        this.pressedIndex = pressedIndex;
    }

    private final List<EventMenu> events = new ArrayList<>();
    private final List<Menu3dItem> items = new ArrayList<>();
    private int menuHeight = 50;
    private int shadowSize = 15;
    private int left = 60;
    private float angle = 150f;
    private int overIndex = -1;
    private int pressedIndex = -1;

    public Menu3D() {
        init();
        initAnimator();
    }

    private void initAnimator() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = getOverIndex(e.getPoint());
                if (index != pressedIndex) {
                    pressedIndex = index;
                    if (pressedIndex != -1) {
                        items.get(pressedIndex).getAnimator().show();
                        hideMenu(pressedIndex);
                        runEvent();
                    }
                }
            }
        };
        addMouseListener(mouse);
    }

    private void init() {
        setForeground(new Color(238, 238, 238));
        addMenuItem("Home");
        addMenuItem("About");
        addMenuItem("Profile");
        addMenuItem("Staff");
        addMenuItem("Setting");
        addMenuItem("User");
        addMenuItem("Report");
    }

    public void addEvent(EventMenu event) {
        this.events.add(event);
    }

    private void runEvent() {
        for (EventMenu event : events) {
            event.menuSelected(pressedIndex);
        }
    }

    public void addMenuItem(String menu) {
        int y = items.size() * menuHeight + left;
        items.add(new Menu3dItem(this, left, y, menuHeight, shadowSize, menu));
    }

    private int getOverIndex(Point mouse) {
        int index = -1;
        for (Menu3dItem d : items) {
            index++;
            if (d.isMouseOver(mouse)) {
                return index;
            }
        }
        return -1;
    }

    private void hideMenu(int exitIndex) {
        for (int i = 0; i < items.size(); i++) {
            if (i != exitIndex) {
                items.get(i).getAnimator().hide();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for (int i = items.size() - 1; i >= 0; i--) {
            items.get(i).render(g2, 360 - angle, left, this);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
