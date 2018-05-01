package produceAndconsumer;

public class ProducerConsumerV1 {
    private volatile boolean isProduced;
    //锁最好置为不可变
    private final Object Lock = new Object();
    private volatile int i;

    public void produce() {
        synchronized (Lock) {
            if (isProduced) {
                try {
                    //本线程阻塞
                    Lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("生产->" + ++i);
            isProduced = true;
            //通知其他阻塞线程
            Lock.notify();
        }


    }

    public void consume() {
        synchronized (Lock) {
            if (isProduced) {
                System.out.println("消费i->" + i);
                isProduced = false;
                //通知其他阻塞线程
                Lock.notify();
            }

            try {
                //自己阻塞
                Lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        ProducerConsumerV1 v1 = new ProducerConsumerV1();
        new Thread(new Producer(v1)).start();
        new Thread(new Producer(v1)).start();
        new Thread(new Consumer(v1)).start();
        new Thread(new Consumer(v1)).start();
    }
}

class Producer implements Runnable {

    public ProducerConsumerV1 producerConsumerV1;

    public Producer(ProducerConsumerV1 producerConsumerV1) {
        this.producerConsumerV1 = producerConsumerV1;
    }

    @Override
    public void run() {
        while (true) {
            producerConsumerV1.produce();
        }
    }
}

class Consumer implements Runnable {

    public ProducerConsumerV1 producerConsumerV1;

    public Consumer(ProducerConsumerV1 producerConsumerV1) {
        this.producerConsumerV1 = producerConsumerV1;
    }

    @Override
    public void run() {
        while (true) {
            producerConsumerV1.consume();
        }
    }
}




