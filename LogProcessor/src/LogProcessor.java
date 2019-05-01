import java.io.*;

public class LogProcessor {
	
	public void logProcessor(String filePath) {
		try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            double ts = 0;
            double tj = 0;
            int count = 0;
            String str;
            while ((str = in.readLine()) != null) {
                String[] nums = str.split(",");
                double jdbcTime = Double.valueOf(nums[0]) / 1000000.0;
                double servletTime = Double.valueOf(nums[1]) / 1000000.0;
                count++;
                ts += servletTime;
                tj += jdbcTime;
            }
            if (count == 0) {
            	System.out.println("no element found");
            } else {
            	System.out.println("average TS is: " + ts / count + " ms");
            	System.out.println("average TJ is: " + tj / count + " ms");
            }
        } catch (IOException e) {
        }
	}
	public static void main(String[] args) {
		LogProcessor log = new LogProcessor();
		log.logProcessor("timeLog.txt");
	}
}
