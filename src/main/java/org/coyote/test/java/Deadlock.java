/* 
 * ##############################################################################
 * You may use this code as you desire
 * 
 * you could find a useful piece of code or a disaster that will make you
 * wish a hippocampus dysfunction, no guarantees, use it at your discretion.
 * 
 * ns616dc@gmail.com
 * ##############################################################################
 */
package org.coyote.test.java;

/**
 *
 * @author renato
 */
public class Deadlock {

    static class Friend {

        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s"
                    + "  has bowed to me!%n",
                    this.name, bower.getName());
            bower.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s"
                    + " has bowed back to me!%n",
                    this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        final Friend alphonse
                = new Friend("Alphonse");
        final Friend gaston
                = new Friend("Gaston");
        new Thread(new Runnable() {
            public void run() {
                alphonse.bow(gaston);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                gaston.bow(alphonse);
            }
        }).start();
    }
}
