package wenqi.nio_mmf;

/**
 * Created by wenqi on 2018/8/14.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("C:\\Users\\wenqi\\Desktop\\图书\\Java并发编程的艺术.pdf");
        FileOutputStream fos = new FileOutputStream("C:\\Users\\wenqi\\Desktop\\图书\\hello.pdf");



        FileChannel fisc = fis.getChannel();
        FileChannel fosc = fos.getChannel();
        long curTime=System.currentTimeMillis();
        fisc.transferTo(0,fis.available(),fosc);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-curTime);
        fis.close();
        fos.close();
    }

}

