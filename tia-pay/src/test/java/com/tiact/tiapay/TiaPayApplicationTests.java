package com.tiact.tiapay;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class TiaPayApplicationTests {

    @Test
    void contextLoads() {
        Map<String,int[]> map = new HashMap<>();
        int[] arr = {0,1,2,3};
        map.put("a",arr);
        Arrays.fill(arr,1,2,20);
        map.put("b",arr);
        arr = Arrays.copyOf(arr,6);
        arr[5] = 10;
        map.put("c",arr);


        System.out.println(map.keySet());
        System.out.println(map.entrySet());

        //372ms
        for(Map.Entry<String, int[]> entry  :map.entrySet()){
            System.out.println(Arrays.toString(entry.getValue()));
        }

        //383ms
/*        for(String key:map.keySet()){
            System.out.println(Arrays.toString(map.get(key)));
        }*/

        //419ms
/*        for(int[] val:map.values()){
            System.out.println(Arrays.toString(val));
        }*/

        //501ms
/*        Iterator<Map.Entry<String,int[]>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,int[]> entry = iterator.next();
            System.out.println(Arrays.toString(entry.getValue()));
        }*/
    }


    @Test
    public void test1() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println("Local HostAddress: "+addr.getHostAddress());

        double score = 1.9193;
        System.out.println(String.format("%.0f", score*100));

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Double x = 2.2345;
        System.out.println("输入：");
        while(!sc.hasNextDouble()){
            if(sc.hasNextDouble()){
                break;
            }
            System.out.println("请输入数字");
            sc = new Scanner(System.in);
        }
        x = sc.nextDouble();
        System.out.println(x);
        x = Math.ceil(x);
        x = Math.pow(x,3);
        System.out.println("四舍五入，立方值:"+x);
        int[] arr = {1,2,3,4,5};
        int chk = Arrays.binarySearch(arr,2);
        if(chk!=-1){
            arr[chk] = x.intValue();
        }
        Arrays.sort(arr);
        String str = Arrays.toString(arr);
        System.out.println(str);
        Date date = new Date(System.currentTimeMillis()-1000*60*60*x.longValue());
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:dd");
        System.out.println(df.format(date));

    }

    @Test
    void FileTes() throws IOException {
        String fileName = "D:\\";
        File file = new File(fileName+"umi1.txt");
        FileOutputStream fo = new FileOutputStream(fileName+"data.txt");
        OutputStreamWriter writer = new OutputStreamWriter(fo,"utf-8");
        writer.append("文件");
        writer.close();
        if(!file.exists()){
            file.createNewFile();
        }
/*        String[] fileList = new File(fileName).list(); //文件目录
        System.out.println("文件目录："+Arrays.toString(fileList));*/
        OutputStream iOut = new FileOutputStream(file);
        String str = "a123sa";
        for(char c : str.toCharArray()){
            iOut.write(c);
        }
        InputStream iPut = new FileInputStream(file);
        for(int i =0;i<str.toCharArray().length;i++){
            System.out.println((char) iPut.read());
        }

    }

    <T> void test3(T t){
        System.out.println(t.toString());
    }

    void test5(List<? extends Number> list){
        System.out.println(list.toString());
    }

    void test6(List<?> list){
        System.out.println(list.toString());
    }

    @Test
    void test4(){
        String a = "aaabcdeasgeee";
        int b = 15;
        if(a.contains("asg")){
            test3(a);
        }
        test3(b);

        List<String> str = new ArrayList<>();
        str.add("测");
        str.add("试");
        List<Integer> num = new ArrayList<>();
        num.add(3);
        num.add(4);
        test5(num);
    }




}
