package movie_booking;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeoutException;

import sun.misc.Unsafe;

public class VirtConsole {

    Console con;
    BufferedReader auxIn;
    InputStream input;
    PrintWriter auxOut;
    Scanner sc;
    boolean test;

    // LocalDateTime lastInteractionTime;

    public VirtConsole() {
        disableWarning();
        con = System.console();
        if (con == null) {
            System.err.println("WARNING: Secure password entry not available. Using fallback entry method.");
            auxIn = new BufferedReader(new InputStreamReader(System.in),1);
            input = System.in;
            auxOut = new PrintWriter(System.out, true);
            sc = new Scanner(auxIn);
        } else {
            auxIn = new BufferedReader(con.reader());
            input = System.in;
            auxOut = con.writer();
            sc = new Scanner(con.reader());
        }
    }

    public VirtConsole(InputStream in, OutputStream out, boolean test) {
        auxIn = new BufferedReader(new InputStreamReader(in));
        input = in;
        auxOut = new PrintWriter(out, true);
        sc = new Scanner(in);
        this.test = test;
    }

    public String readPassword() {
        if (con != null) {
            return new String(con.readPassword());
        } else {
            return sc.nextLine();
        }
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public void println(String s) {
        this.auxOut.println(s);
    }

    public void print(String s) {
        this.auxOut.print(s);
        this.auxOut.flush();
    }

    public void format(String s, Object... args) {
        this.auxOut.format(s, args);
        this.auxOut.flush();
    }

    public String nextLine() {
        return this.sc.nextLine();
    }

    // TimeoutException on Timeout
    // Exception on underlying exception
    public String nextLineWithTimeout(long millis) throws TimeoutException {
        if(this.test){
            return this.sc.nextLine();
        }
        BufferedInputStream temp = new BufferedInputStream(input, 1) {
            @Override
            public void close() {
            }
        };

        // Busy loop
        long expiry = System.currentTimeMillis() + millis;
        while (true) {
            try {
                if (temp.available() > 0) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException();
            }

            if (System.currentTimeMillis() > expiry) {
                throw new TimeoutException();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        Scanner sc = new Scanner(temp);
        String line = sc.nextLine();
        sc.close();
        return line;
    }

    // TimeoutException on Timeout
    // Exception on underlying exception
    public String readPasswordWithTimeout(long millis) throws TimeoutException {
        if(this.test){
            return this.sc.nextLine();
        }
        if (con == null) {
            return this.nextLineWithTimeout(millis);
        }
        String line = "";
        boolean restoreEcho = false;
        Method consoleEchoMethod = null;
        try {
            consoleEchoMethod = con.getClass().getDeclaredMethod("echo", boolean.class);
            consoleEchoMethod.setAccessible(true);
            Object ret = consoleEchoMethod.invoke(con, false);
            restoreEcho = ret.equals(true);
            line = nextLineWithTimeout(millis);
        } catch (TimeoutException e) {
            throw new TimeoutException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (restoreEcho) {
                try {
                    consoleEchoMethod.invoke(con, true);
                } catch (Exception e) {
                }
            }
        }
        return line;
    }

    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {

        }
    }
}
