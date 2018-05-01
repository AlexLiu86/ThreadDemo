package ThreadConstructors;


public class ThreadDemo1 {
    private static int count;

    public static void main(String args[]) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    add(1);
                } catch (Error e) {
                    System.out.println(count);

                }
            }

            private void add(int i) {
                count++;
                add(i + 1);
            }
        }).start();
    }


}


