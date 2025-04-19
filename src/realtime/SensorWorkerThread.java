package realtime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SensorWorkerThread extends Thread {
    private final String sensorName;
    private final DataOutputStream output;
    private final int interval;
    private final Random random = new Random();
    private final Semaphore semaphore; // Semaphore chỉ sử dụng khi gửi dữ liệu lên Cloud

    // Constructor có Semaphore chỉ dành cho các thread cần gửi dữ liệu lên Cloud
    public SensorWorkerThread(String sensorName, DataOutputStream output, int interval, int priority, Semaphore semaphore) {
        this.sensorName = sensorName;
        this.output = output;
        this.interval = interval;
        this.semaphore = semaphore; // Truyền vào nếu cần Semaphore cho việc gửi dữ liệu lên Cloud
        setPriority(priority);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String value = generateSensorValue();

                // Chỉ sử dụng Semaphore khi là "Cloud Upload" để đồng bộ hóa khi gửi dữ liệu lên cloud
                if (sensorName.startsWith("Cloud Upload") && semaphore != null) {
                    semaphore.acquire(); // Chờ đến khi có quyền sử dụng tài nguyên
                }

                try {
                    synchronized (output) {
                        output.writeUTF(sensorName + ": " + value);
                        output.flush();
                    }
                } finally {
                    if (sensorName.startsWith("Cloud Upload") && semaphore != null) {
                        semaphore.release(); // Nhả quyền sau khi gửi dữ liệu
                    }
                }

                Thread.sleep(interval); // Chờ trước khi tiếp tục vòng lặp
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(sensorName + " stopped.");
        }
    }

    private String generateSensorValue() {
        if (sensorName.startsWith("Soil Moisture"))
            return random.nextInt(100) + "%";
        if (sensorName.startsWith("Light Intensity"))
            return random.nextInt(100) + "%";
        if (sensorName.startsWith("Temperature & Humidity"))
            return (20 + random.nextInt(10)) + "C, Humidity: " + (40 + random.nextInt(30)) + "%";
        if (sensorName.startsWith("Water Level"))
            return random.nextBoolean() ? "Low" : "Normal";
        if (sensorName.startsWith("Cloud Upload"))
            return "Sending data to cloud...";
        return "Unknown";
    }
}
