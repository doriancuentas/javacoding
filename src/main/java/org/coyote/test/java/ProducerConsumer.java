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

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renato
 */
public class ProducerConsumer {

    private static class AssemplyLine {

        public static String[] _MPARTS = "ModeloT".split("");
        private static final String EMPTY = "EMPTY";
        private Random random;
        private volatile String[] parts;
        private volatile int produced;

        public AssemplyLine() {
            this.random = new Random();
            this.parts = new String[_MPARTS.length];
            Arrays.fill(this.parts, EMPTY);
            produced = 0;
        }

        public synchronized void produce() {
            while (produced < 10) {
                while (!parts[0].equals(EMPTY)) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                int index = 0;
                for (String singlePart : _MPARTS) {
                    try {
                        Thread.sleep(random.nextInt(200));
                        parts[index] = singlePart;
                        index++;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                produced++;
                notifyAll();
            }
        }

        public synchronized void assemble() {
            while (produced < 10) {
                while (Arrays.stream(parts).anyMatch(e -> e.equals(EMPTY))) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProducerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Arrays.stream(parts).forEach(System.out::print);
                System.out.printf(" : %d%n", this.produced);
                Arrays.fill(this.parts, EMPTY);
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        AssemplyLine assemplyLine = new AssemplyLine();
        new Thread(() -> assemplyLine.produce()).start();
        new Thread(() -> assemplyLine.assemble()).start();
    }
}
