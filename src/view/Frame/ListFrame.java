package view.Frame;
import view.UII.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;

public class ListFrame extends JFrame{
    private Frame frame;
    private JTable table;
    public ListFrame(Frame frame) {
        this.frame = frame;
        setTitle("排行榜");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // 数据模型
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Score", "WinRate"}, 0);
        table = new JTable(model);
        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        // 读取文件并填充数据模型
        File currentDir = new File("./user.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(currentDir.getAbsolutePath()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                System.out.println(line);
                //Object[] data2;
                /*if(Integer.parseInt(data[4])==0){
                    Object[] data2 = {data[0], data[2], 0.0};
                    System.out.println(data[0]+" "+data[2]+" "+"0");
                    model.addRow(data2);
                }
                else{
                    Object[] data2 = {data[0], data[2], (double)(Integer.parseInt(data[3]) / Integer.parseInt(data[4]))};
                    System.out.println(data[0]+" "+data[2]+" "+(double)(Integer.parseInt(data[3]) / Integer.parseInt(data[4])));
                    model.addRow(data2);
                }*/
                if(Integer.parseInt(data[4])!=0){
                    Object[] data2 = {data[0], Integer.parseInt(data[2]), (Double.parseDouble(data[3]) / Double.parseDouble(data[4]))};
                    //System.out.println(data[0]+" "+data[2]+" "+(double)(Integer.parseInt(data[3]) / Integer.parseInt(data[4])));
                    model.addRow(data2);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果需要排序，可以对模型进行排序
        // 例如，按第一列升序排列
        Collections.sort(model.getDataVector(), new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                // 假设第二列的数据是整数
                int index = 1; // 第二列的索引
                Integer num1 = (Integer) ((Vector) o1).get(index);
                Integer num2 = (Integer) ((Vector) o2).get(index);
                return num2.compareTo(num1);
            }
        });
        table.setModel(model);
        // 设置表格为不可编辑
        table.setRowHeight(24);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);

    }

    //别忘了加背景图！！！！！！！
}
