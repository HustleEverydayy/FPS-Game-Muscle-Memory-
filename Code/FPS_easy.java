package FPS_GAME;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
/***主程式***/
public class FPS_easy extends JFrame implements ActionListener,MouseMotionListener,MouseListener //名稱
{
    public void MyFrame(){
//視窗的窗口風格 java風格的不怎好看，我換一個windows系統的介面。
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");//Windows Classic的風格
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame jf=new JFrame();//例項化JFrame物件
        Container container=jf.getContentPane();//將窗體轉化為容器，取的JFrame的內容版面。
        container.setLayout(null);//位置設不固定
        container.setBackground(new Color(0xAFDEF0));
        JButton jb=new JButton("新手版");
        jb.setBounds(30, 200,400, 400); //位置和大小
        jb.setFont(new java.awt.Font("微軟正黑體", 1, 90));//字形
        JButton jb1=new JButton("高手版");
        jb1.setBounds(455, 200,400, 400);
        jb1.setFont(new java.awt.Font("微軟正黑體", 1, 90));//字形
        jb.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
//Jdialog的聆聽跳出你所選擇的視窗並顯示
        jb.addActionListener(new ActionListener() {
            //定義匿名內部類，這樣才可以點選出現反應
            @Override
            public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
                new FPS_easy().setVisible(true);
            }
        });
        jb1.addActionListener(new ActionListener() {
            //定義匿名內部類，這樣才可以點選出現反應
            @Override
            public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
                new FPS_hard().setVisible(true);
            }
        });
        container.add(jb);//將按鈕新增到容器中，這點非常重要，不然無法顯示
        container.add(jb1);//將按鈕新增到容器中，這點非常重要，不然無法顯示
//設定容器的結構的特性
        jf.setTitle("射擊遊戲選單");
        jf.setLocation(520,75);//位置
//        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setSize(900,900);//設定容器的大小
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//關閉視窗
//        jf.setBackground(new Color(0,255,0,0));
        jf.setResizable(false);//關掉放大，沒啥必要放大。
        jf.setVisible(true);//使窗體可見
//設定窗體的關閉模式
    }
    Container c;
    //設定UI元件
    JButton but,pause,unpause,remove,txt,tsql;
    JLabel lab1,lab2,lab3,lab4;
    //設定共用的變數與類別
    java.util.Random rg,rg1,rg2,rg3,rg4;
    int x[]= new int[100];
    int y[]=new int[100];
    int mode,ms,rd,rd1,rd2,rd3;//mt時間,ms分數
    double mt;
    javax.swing.Timer t;
    boolean paused = false;//設定暫停要用的boolean
    JFrame f;//設定Dialogmessger跳出訊息視窗的Jframe
    int number = 0 ;//次數
    JPanel p;
    JTextPane tex;   //排行榜顯示
    public FPS_easy() //建構元
    {
        super("射擊遊戲準度練習小遊戲(新手版)");
//初始化共用變數
        x[0]=550;
        y[0]=200;
        for (int i=0;i<5;i++){
            x[i+1]=x[i]+200;
            for (int j=0;j<5;j++){
                y[j+1]=200;
            }
        }
        x[5]=550;
        y[5]=350;
        for (int i=5;i<10;i++){
            x[i+1]=x[i]+200;
            for (int j=5;j<10;j++){
                y[j+1]=350;
            }
        }
        x[10]=550;
        y[10]=500;
        for (int i=10;i<15;i++){
            x[i+1]=x[i]+200;
            for (int j=10;j<15;j++){
                y[j+1]=500;
            }
        }
        x[15]=550;
        y[15]=650;
        for (int i=15;i<20;i++){
            x[i+1]=x[i]+200;
            for (int j=15;j<20;j++){
                y[j+1]=650;
            }
        }
        x[20]=550;
        y[20]=800;
        for (int i=20;i<25;i++){
            x[i+1]=x[i]+200;
            for (int j=20;j<25;j++){
                y[j+1]=800;
            }
        }
        //x[0]=200;x[1]=400;x[2]=1550;
        //y[0]=200;y[1]=200;y[2]=800;mode=0;
        rg=new Random();//亂數
        rg1=new Random();
        rg2=new Random();
        rg3=new Random();
        rg4=new Random();
        c=getContentPane();//取得ContentPane
//設定版面設定
        c.setLayout(null);
//初始化UI元件
        but= new JButton("開始");
        pause= new JButton("暫停");
        unpause= new JButton("繼續");
        remove = new JButton("清除排行榜/重置次數");
        txt = new JButton("儲存成txt");
        tsql = new JButton("txt存到Mysql");
        lab1=new JLabel("剩下:60秒");
        lab2=new JLabel("成績:0");
        lab3=new JLabel(" 次數");
        lab4=new JLabel("   分數");
        p=new JPanel();
        tex = new JTextPane();
//將UI元件加入ContentPane
        c.add(lab1);//倒數時間
        c.add(but);//開始
        c.add(pause);//暫停
        c.add(unpause);//繼續
        c.add(lab2);//成績
        c.add(p);
//        c.add(lab3);
//        c.add(lab4);
        c.add(tex);
        c.add(remove);
        c.add(txt);
        c.add(tsql);
//設定UI元件與滑鼠的事件觸發傾聽者
        c.setBackground(Color.WHITE);
//        but.setPreferredSize(new Dimension(150,80));//大小
        but.setBounds(650,5,150,80); //設置大小和座標
//        pause.setPreferredSize(new Dimension(150,80));
        pause.setBounds(850,5,150,80);//設置大小和座標
//        unpause.setPreferredSize(new Dimension(150,80));
        unpause.setBounds(1050,5,150,80);//設置大小和座標
//        lab1.setPreferredSize(new Dimension(200,80));//設置大小
        remove.setBounds(1450,850,440,100);//設置大小和座標

        txt.setBounds(50,100,300,100);//設置大小和座標

        tsql.setBounds(50,250,300,100);//設置大小和座標

        lab1.setBounds(400,5,250,80);//設置大小和座標
//        lab2.setPreferredSize(new Dimension(150,80));//設置大小
        lab2.setBounds(1300,5,200,80);//設置大小和座標
//        lab1.setFont(new  java.awt.Font("微軟楷體",  1,  30)); //設置字體樣式
        lab1.setFont(new  java.awt.Font("標楷體",  1,  30)); //設置字體樣式
        lab2.setFont(new  java.awt.Font("標楷體",  1,  30)); //設置字體樣式
        pause.addActionListener(this);
        unpause.addActionListener(this);
        but.addActionListener(this);
        remove.addActionListener(this);
        txt.addActionListener(this);
        tsql.addActionListener(this);
        //bot.setContentAreaFilled(false);//透明度
        but.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        pause.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        unpause.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        remove.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        txt.setBorder(BorderFactory.createRaisedBevelBorder());//凸版
        tsql.setBorder(BorderFactory.createRaisedBevelBorder());
        but.setFont(new java.awt.Font("標楷體", 1, 30));//字形大小
        //bot.setBackground(new Color(63, 181,230));
//        pause.setContentAreaFilled(false);
//        unpause.setContentAreaFilled(false);
        pause.setFont(new java.awt.Font("標楷體", 1, 30));//字形大小
        unpause.setFont(new java.awt.Font("標楷體", 1, 30));//字形大小
        remove.setFont(new java.awt.Font("標楷體", 1, 40));//字形大小
        txt.setFont(new java.awt.Font("標楷體", 1, 35));//字形大小
        tsql.setFont(new java.awt.Font("標楷體", 1, 35));//字形大小
//        pause.setBackground(new Color(0xD3EDEF));
        addMouseMotionListener(this);//增加滑鼠聆聽者
        addMouseListener(this);//增加滑鼠聆聽者
        t=new javax.swing.Timer(1000,this);//時間倒數計時
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//取得視窗
        setSize(d.width-this.getWidth(),d.height-this.getHeight());//設定size，顯示出去,用Toolkit取的螢幕長寬,全螢幕
        setLocationRelativeTo(null);//視窗置中
        JFrame.setDefaultLookAndFeelDecorated(true);//showmessger彈跳視窗
        JDialog.setDefaultLookAndFeelDecorated(true);//showmessger彈跳視窗
//        String first ="次數"+"\t"+"\t"+"分數"+"\n\n";
        p.setBounds(1450,0,440,830); //設置size位置
        p.setBackground(Color.WHITE); //JPanel的背景顏色
        p.add(lab3); //把標籤3丟到JPanel
        p.add(lab4); //把標籤4丟到JPanel
        lab3.setPreferredSize(new Dimension(150,50)); //設置寬度高度=大小
        lab3.setFont(new java.awt.Font("標楷體", 1, 40));//字形
        lab4.setPreferredSize(new Dimension(150,50));//設置寬度高度=大小
        lab4.setFont(new java.awt.Font("標楷體", 1, 40));//字形
        p.add(tex);  //把JTextPane 加到 JPanel
//        tex.setText(first);
        tex.setFont(new java.awt.Font("標楷體", 1, 40)); //字形
//        setVisible(true);
    }
    public void paint(Graphics g)
    {
        super.paint(g);//畫出元件
//        g.drawLine(1449, 0, 1449, getMaximumSize().height);
        for (int i=0;i<=24;i++) {
            if (i + rd > 24 && i + rd1 <= 24 && i + rd3 <= 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
            } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 <= 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
            } else if (i + rd3 > 24 && i + rd1 <= 24 && i + rd <= 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
            } else if (i + rd > 24 && i + rd1 <= 24 && i + rd3 > 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
            } else if (i + rd > 24 && i + rd1 > 24 && i + rd3 <= 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
            } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 > 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
            } else if (i + rd3 > 24 && i + rd1 > 24 && i + rd > 24) {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
            } else {
                g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
            }
        }

//額外的畫圖程式寫在這裡

        for (int i=0;i<=24;i++){
            if(mode==i+1) {
                int R = (int )(Math.random() * 255 + 1);
                int G = (int )(Math.random() * 255 + 1);
                int B = (int )(Math.random() * 255 + 1);
                Color Random_Color = new Color(R, G, B); //圈圈顏色
                if (i + rd > 24 && i + rd1 <= 24 && i + rd3 <= 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.fillOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 <= 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.fillOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd3 > 24 && i + rd1 <= 24 && i + rd <= 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.fillOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.fillOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd > 24 && i + rd1 <= 24 && i + rd3 > 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.fillOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd > 24 && i + rd1 > 24 && i + rd3 <= 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 > 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.fillOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd3 > 24 && i + rd1 > 24 && i + rd > 24) {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.fillOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else {
                    g.setColor(Random_Color);
                    g.fillOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.fillOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.fillOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.fillOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                }
            }
            else{
                if (i + rd > 24 && i + rd1 <= 24 && i + rd3 <= 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 <= 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd3 > 24 && i + rd1 <= 24 && i + rd <= 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd > 24 && i + rd1 <= 24 && i + rd3 > 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd > 24 && i + rd1 > 24 && i + rd3 <= 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                } else if (i + rd1 > 24 && i + rd <= 24 && i + rd3 > 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else if (i + rd3 > 24 && i + rd1 > 24 && i + rd > 24) {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd - rd2] - 50, y[i + rd - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd1 - rd2] - 50, y[i + rd1 - rd2] - 50, 100, 100);
                    g.drawOval(x[i + rd3 - rd2] - 50, y[i + rd3 - rd2] - 50, 100, 100);
                } else {
                    g.drawOval(x[i] - 50, y[i] - 50, 100, 100);
                    g.drawOval(x[i + rd] - 50, y[i + rd] - 50, 100, 100);
                    g.drawOval(x[i + rd1] - 50, y[i + rd1] - 50, 100, 100);
                    g.drawOval(x[i + rd3] - 50, y[i + rd3] - 50, 100, 100);
                }
            }
        }
    }
    public void mouseDragged(MouseEvent xxx) { };
    public void mouseMoved(MouseEvent e){ };
    //UI元件事件處理類別寫在這裡
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==but)
        {
            mt=60; //時間
            ms=0;   //分數
            number+=1;//次數
            t.start(); //時間開始倒數
            mode=rg.nextInt(25)+1;//亂數
            rd=rg1.nextInt(5)+1;
            rd1=rg1.nextInt(5)+6;
            rd2=rg2.nextInt(20-16+1)+16;
            rd3=rg3.nextInt(5)+11;
            lab1.setText("剩下:"+mt+"秒");//按下開始，重置時間
            lab2.setText("成績:"+ms+"");//按下開始，重置分數
            repaint();
            paused = false;//重設boolean的false，才可以連續重新開始和暫停。
        }
        //時間到
        else if (e.getSource()==t)
        {
            //mt=mt-0.5;
            mt=mt-1;
            lab1.setText("剩下:"+mt+"秒");
            if (mt==0 ){
                t.stop();
//                lab1.setText("剩下:60秒");
                if (ms>=100){
                    JOptionPane.showMessageDialog(f,"分數 " + ms + " 分恭喜你成為高手");//JDialogshowmessage的彈出分數
                }else if (ms>=0 && ms<100){
                    JOptionPane.showMessageDialog(f, "分數 " + ms + " 分有朝一日，你會再變更強的");
                }
                String first = "第"+number+"次"+"\t"+"\t"+ms+"分"+"\n"; //當玩完一次遊戲的次數和分數，再用下面的公式來填加
                StyledDocument doc = tex.getStyledDocument(); //去獲取tex的編輯器關聯的模型
                try {
                    doc.insertString(doc.getLength(), first, null); //在把first 給insert 到JTextPane
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace(); //堆疊追蹤 看哪裡錯誤
                }//記分板add給予新的第幾次和分數
//                repaint();
            }
            //repaint();
        }
        if(!paused) {
            //System.out.println("不是被暫停");

            if(e.getSource() == pause) {
                paused = true;//暫停
                t.stop(); //時間停止
            }
        } else {
            //System.out.println("我被暫停");
            if(e.getSource() == unpause) {
                paused = false;//繼續
                t.start(); //時間開始
                if (mt==0){
                    t.stop(); //時間停止 以防繼續扣到負
                }
            }
        }
        if (e.getSource()==remove){ //清除計分板
            Element root = tex.getDocument().getDefaultRootElement();
            Element first = root.getElement(0); //取到第一行
            try {
                tex.getDocument().remove(first.getStartOffset(), tex.getDocument().getLength() );//移除第一個到最後一個
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace(); //追蹤錯誤
            }
            number=0;//次數
        }
        if (e.getSource()==txt){ //寫入txt檔案
            File fileName = new File("C:\\Final project 108021011\\Leaderboard.txt"); //要寫入的txt的路徑
            BufferedWriter outFile = null;
            try {
                String filePath = "C:\\Final project 108021011"; //要創立新資料夾的路徑
                File dir = new File(filePath);
                // 一、檢查放置檔案的資料夾路徑是否存在，不存在則建立
                if (!dir.exists()) {
                    dir.mkdirs();// mkdirs建立多級目錄
                }
                fileName.createNewFile(); //建立txt
                outFile = new BufferedWriter(new FileWriter(fileName));
                outFile.write("次數"+"\t\t"+"分數"+"\n");
                tex.write(outFile);   // *** here: ***
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (outFile != null) {
                    try {
                        outFile.close(); //正常關閉txt檔案
                    } catch (IOException ex) {
                        // one of the few times that I think that it's OK
                        // to leave this blank
                    }
                }
            }
        }
        if (e.getSource()==tsql) { //創建Mysql 和 填入資料給MYsql
            //一开始必须填一个已经存在的数据库
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //要註冊的Driver
            String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"; //網址
            //  Database credentials
            String USER = "root"; //帳號
            String PASS = "11111111"; //密碼
            Connection conn = null; //連結
            Statement stmt = null;
            File file = new File("C:\\Final project 108021011\\Leaderboard.txt"); //要讀取的txt檔案
            StringBuffer sql = null;
            BufferedReader reader = null; //讀取用的reader
            String line = null; //行數
            String[] str = null;
            String Number = null; //MySQL次數欄位
            String score = null; //MySQL分數欄位
//        String city = null;
            try{
                //STEP 2: Register JDBC driver
                Class.forName(JDBC_DRIVER); //註冊 JDBC driver
                //STEP 3: Open a connection
                System.out.println("Connecting to a selected database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS); //連結Mysql需要的網址、帳號、密碼
                System.out.println("Connected database successfully...");
                //STEP 4: Execute a query
                System.out.println("Creating table in given database...");
                stmt = conn.createStatement(); //採用可更新數據庫的模式。

                stmt.executeUpdate("create database DB_108021011_2A"); //建立新的資料庫
                stmt.close(); //資料庫關閉
                conn.close(); //連結關閉

                //新建的資料庫網址
                String DB_URL1 = "jdbc:mysql://localhost:3306/DB_108021011_2A?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                conn = DriverManager.getConnection(DB_URL1, USER, PASS); //除了網址其他都一樣
                stmt = conn.createStatement(); //再次開啟可更新的模式
                //建立結構
                String sql1 = "CREATE TABLE table_108021011_2A " +
                        "(Number VARCHAR(50) not NULL, " +
                        " score VARCHAR(50), " +
//                    " city VARCHAR(50), " +
                        " PRIMARY KEY ( Number ))";
                stmt.executeUpdate(sql1); //更新進去
                System.out.println("Created table in given database...");
                reader = new BufferedReader(new FileReader(file)); //讀取你的記事本的內容
                stmt = conn.createStatement();
                while((line = reader.readLine())!= null){ //迴圈去填加到Mysql的table
                    sql = new StringBuffer();
//                str = line.split("-");
//                str = line.split("\\s+");
                    str = line.replaceAll("　", " ").trim().split("\\s+"); //分割多個空白和轉換空白
                    Number = str[0]; //把次數填進來
                    score = str[1]; //把分數填進來
//                city = str[2];
                    sql.append("insert into table_108021011_2A(Number,score) values('"); //insert進去
                    sql.append(Number+"','");
//                sql.append(endNumber+"','");
                    sql.append(score+"')");
                    stmt.executeUpdate(sql.toString()); //更新進去
                }
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace(); //堆疊追蹤
            }catch(Exception ex){
                //Handle errors for Class.forName
                ex.printStackTrace(); //堆疊追蹤
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        conn.close(); //連結關閉 資料庫關閉
                }catch(SQLException se){
                }// do nothing
                try{
                    if(conn!=null)
                        conn.close(); //連結關閉 資料庫關閉
                }catch(SQLException se){
                    se.printStackTrace(); //追蹤
                }//end finally try
            }//end try
            System.out.println("Goodbye!");
        }//end main
    }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
    public void mousePressed(MouseEvent e)
    {
        int mx,my;
        mx=e.getX();my=e.getY();
        for (int i = 0; i <= 24; i++) {
            if (mode == i + 1) {
                if (i + rd > 24 && i + rd1 <= 24 && i + rd3 <= 24) {
                    //圓圈裡面的xy座標減去取到距離的xy座標並平方<2500就得分  ，算式：x*x+y*y=r*r
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);//成績的JLabel變更分數
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;//點完之後變數
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd - rd2] - mx) * (x[i + rd - rd2] - mx) + (y[i + rd - rd2] - my) * (y[i + rd - rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1] - mx) * (x[i + rd1] - mx) + (y[i + rd1] - my) * (y[i + rd1] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3] - mx) * (x[i + rd3] - mx) + (y[i + rd3] - my) * (y[i + rd3] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }else if (i+rd1>24 && i+rd<=24 && i+rd3<=24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd ] - mx) * (x[i + rd ] - mx) + (y[i + rd ] - my) * (y[i + rd ] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1- rd2] - mx) * (x[i + rd1- rd2] - mx) + (y[i + rd1- rd2] - my) * (y[i + rd1- rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3] - mx) * (x[i + rd3] - mx) + (y[i + rd3] - my) * (y[i + rd3] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }else if (i+rd3>24 && i+rd1<=24 && i+rd<=24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd ] - mx) * (x[i + rd ] - mx) + (y[i + rd ] - my) * (y[i + rd ] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1] - mx) * (x[i + rd1] - mx) + (y[i + rd1] - my) * (y[i + rd1] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3- rd2] - mx) * (x[i + rd3- rd2] - mx) + (y[i + rd3- rd2] - my) * (y[i + rd3- rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }else if (i+rd>24  && i+rd1<=24 && i+rd3>24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd -rd2] - mx) * (x[i + rd -rd2] - mx) + (y[i + rd -rd2] - my) * (y[i + rd -rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1] - mx) * (x[i + rd1] - mx) + (y[i + rd1] - my) * (y[i + rd1] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3- rd2] - mx) * (x[i + rd3- rd2] - mx) + (y[i + rd3- rd2] - my) * (y[i + rd3- rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }else if (i+rd>24  && i+rd1>24 && i+rd3<=24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd -rd2] - mx) * (x[i + rd -rd2] - mx) + (y[i + rd -rd2] - my) * (y[i + rd -rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1-rd2] - mx) * (x[i + rd1-rd2] - mx) + (y[i + rd1-rd2] - my) * (y[i + rd1-rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3] - mx) * (x[i + rd3] - mx) + (y[i + rd3] - my) * (y[i + rd3] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }
                else if (i+rd1>24 && i+rd<=24 && i+rd3>24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd ] - mx) * (x[i + rd ] - mx) + (y[i + rd ] - my) * (y[i + rd ] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1-rd2] - mx) * (x[i + rd1-rd2] - mx) + (y[i + rd1-rd2] - my) * (y[i + rd1-rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3- rd2] - mx) * (x[i + rd3- rd2] - mx) + (y[i + rd3- rd2] - my) * (y[i + rd3- rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }else if (i+rd3>24 && i+rd1>24 && i+rd>24){
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd -rd2] - mx) * (x[i + rd -rd2] - mx) + (y[i + rd -rd2] - my) * (y[i + rd -rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1-rd2] - mx) * (x[i + rd1-rd2] - mx) + (y[i + rd1-rd2] - my) * (y[i + rd1-rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd3- rd2] - mx) * (x[i + rd3- rd2] - mx) + (y[i + rd3- rd2] - my) * (y[i + rd3- rd2] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                } else {
                    if ((x[i] - mx) * (x[i] - mx) + (y[i] - my) * (y[i] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd] - mx) * (x[i + rd] - mx) + (y[i + rd] - my) * (y[i + rd] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    } else if ((x[i + rd1] - mx) * (x[i + rd1] - mx) + (y[i + rd1] - my) * (y[i + rd1] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }else if ((x[i + rd3] - mx) * (x[i + rd3] - mx) + (y[i + rd3] - my) * (y[i + rd3] - my) < 2500) {
                        ms = ms + 1;//成績加分
                        lab2.setText("成績:" + ms);
                        //mode=0;
                        //repaint();
                        mode = rg.nextInt(25) + 1;
                        rd = rg1.nextInt(5) + 1;
                        rd1 = rg1.nextInt(5) + 6;
                        rd2 = rg2.nextInt(20 - 16 + 1) + 16;
                        rd3 = rg3.nextInt(5) + 11;
                        repaint();//重畫
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e){ };
    public void mouseClicked(MouseEvent e){ };
    public static void main(String args[]) //程式起點
    {
        FPS_easy app=new FPS_easy(); //啟動UI元件
        app.MyFrame(); //JDialog所需要用到的
        app.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        }); //處理視窗關閉要求
    }
}