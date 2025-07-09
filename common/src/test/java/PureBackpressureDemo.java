import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicLong;

public class PureBackpressureDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建发布者 - 使用标准JDK的SubmissionPublisher
        // 设置缓冲区大小为50（背压关键点）
        SubmissionPublisher<DataItem> publisher = new SubmissionPublisher<>(
                Runnable::run,  // 使用调用者线程执行
                50            // 设置缓冲区大小
        );

        // 创建慢消费者
        SlowProcessor slowProcessor = new SlowProcessor();
        publisher.subscribe(slowProcessor);

        // 创建快速生产者
        FastProducer producer = new FastProducer(publisher);
        new Thread(producer).start();

        // 等待处理完成
        Thread.sleep(5000);
        publisher.close();
        System.out.println("Demo结束");
    }

    // 数据项
    static class DataItem {
        private final long id;
        private final String content;

        public DataItem(long id) {
            this.id = id;
            this.content = "Data-" + id;
        }

        @Override
        public String toString() {
            return "Item#" + id;
        }
    }

    // 快速生产者
    static class FastProducer implements Runnable {
        private final Publisher<DataItem> publisher;
        private long counter = 0;

        public FastProducer(Publisher<DataItem> publisher) {
            this.publisher = publisher;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    DataItem item = new DataItem(++counter);

                    // 发布项目（当缓冲区满时会阻塞）
                    ((SubmissionPublisher<DataItem>)publisher).submit(item);

                    // 打印生产速度
                    if (counter % 100 == 0) {
                        System.out.println("[生产者] 已生成 " + counter + " 项");
                    }

                    // 模拟快速生产（每1ms生产一个）
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // 慢速处理器（消费者）
    static class SlowProcessor implements Subscriber<DataItem> {
        private Subscription subscription;
        private final AtomicLong pendingRequests = new AtomicLong(0);
        private final int batchSize = 20; // 每次请求的批量大小

        @Override
        public void onSubscribe(Subscription subscription) {
            System.out.println("[消费者] 订阅建立，初始请求 " + batchSize + " 项");
            this.subscription = subscription;
            // 初始请求一批数据
            subscription.request(batchSize);
            pendingRequests.set(batchSize);
        }

        @Override
        public void onNext(DataItem item) {
            try {
                // 模拟慢速处理（每100ms处理一个）
                System.out.println("[消费者] 正在处理: " + item);
                Thread.sleep(100);

                // 减少待处理计数
                long remaining = pendingRequests.decrementAndGet();

                // 当剩余请求量低于阈值时，请求更多数据
                if (remaining <= batchSize / 2) {
                    long newRequests = batchSize - remaining;
                    System.out.println("[消费者] 请求更多数据: +" + newRequests + " (待处理: " + remaining + ")");
                    subscription.request(newRequests);
                    pendingRequests.addAndGet(newRequests);
                }
            } catch (InterruptedException e) {
                subscription.cancel();
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.println("[消费者] 发生错误: " + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("[消费者] 处理完成");
        }
    }

}