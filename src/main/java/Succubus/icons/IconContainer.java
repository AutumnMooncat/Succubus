package Succubus.icons;

import Succubus.MainModfile;
import Succubus.util.TexLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class IconContainer {
    public static class FireIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public FireIcon() {
            super(MainModfile.makeID("Fire"), TexLoader.getTexture(MainModfile.makeImagePath("elements/Fire.png")));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new FireIcon();
            }
            return singleton;
        }
    }
    public static class IceIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public IceIcon() {
            super(MainModfile.makeID("Ice"), TexLoader.getTexture(MainModfile.makeImagePath("elements/Ice.png")));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new IceIcon();
            }
            return singleton;
        }
    }
    public static class BoltIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public BoltIcon() {
            super(MainModfile.makeID("Bolt"), TexLoader.getTexture(MainModfile.makeImagePath("elements/Bolt.png")));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new BoltIcon();
            }
            return singleton;
        }
    }
    public static class WindIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public WindIcon() {
            super(MainModfile.makeID("Wind"), TexLoader.getTexture(MainModfile.makeImagePath("elements/Wind.png")));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new WindIcon();
            }
            return singleton;
        }
    }
}
