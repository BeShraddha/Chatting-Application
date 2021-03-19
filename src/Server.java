
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;


import java.util.Calendar;
import java.text.SimpleDateFormat;


public class Server implements ActionListener{
    
    static JFrame f1 = new JFrame();
    
    static Socket s;
    static ServerSocket sskt;
    static DataInputStream dis;
    static DataOutputStream dos;
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel j2;
    
    static Box vertical = Box.createVerticalBox();
    
    Boolean typing;
    
    Server(){
        
        //add panel where header will reside
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 350, 50);
        f1.add(p1);
        
        //Add back arraow
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatapp/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        
        JLabel l1 = new JLabel(i3);
        l1.setBounds(6,12,20,20);
        p1.add(l1);
        
        //CLICK event on back arrow
        l1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        
        //add photo
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatapp/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        
        JLabel l2 = new JLabel(i6);
        l2.setBounds(32,5,40, 40);
        p1.add(l2);
        
        //Add name
        JLabel l3 = new JLabel("Server");
        l3.setBounds(80,1,80, 35);
        l3.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
        l3.setForeground(Color.WHITE);
        p1.add(l3);
        
        //Add online status
        JLabel l4 = new JLabel("Online");
        l4.setBounds(80,1,60, 68);
        l4.setFont(new Font("SAN_SERIF",Font.PLAIN,10));
        l4.setForeground(Color.WHITE);
        p1.add(l4);
        
        //timer class for chnaging the status 
        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Online");
                }
            }
        });
        
        t.setInitialDelay(1000);
        
        //add videocall icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatapp/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(27, 28, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        
        JLabel l5 = new JLabel(i9);
        l5.setBounds(190,13,27, 28);
        p1.add(l5);
        
        //add call icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatapp/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(22, 28, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        
        JLabel l6 = new JLabel(i12);
        l6.setBounds(232,13,22, 28);
        p1.add(l6);
        
        //add setting_3_dot icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatapp/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(9, 19, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        
        JLabel l7 = new JLabel(i15);
        l7.setBounds(270,18,9, 19);
        p1.add(l7);
        
        //add text field
        t1 = new JTextField();
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        t1.setBounds(6, 415, 210, 30);
        f1.add(t1);
        
         t1.addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent ke){
               l4.setText("typing...");
               
               t.stop();
               
               typing = true;
           }
           
           public void keyReleased(KeyEvent ke){
               typing = false;
               
               if(!t.isRunning()){
                   t.start();
               }
           }
       });
        
        //add send button
        b1 = new JButton("Send");
        b1.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setBounds(218, 415, 66, 30);
        b1.addActionListener(this);
        f1.add(b1);
        
        //add panel where message can be seen in a formated way
        j2 = new JPanel();
        //j2.setBounds(6, 55, 280, 355);
        j2.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        j2.setBackground(Color.WHITE);
        //f1.add(j2);
        
        //Adding scroll bar
        JScrollPane sp = new JScrollPane(j2);
        sp.setBounds(6, 55, 280, 355);
        sp.setBorder(BorderFactory.createEmptyBorder());
        
        
        //for changing the color of scrollbar
        ScrollBarUI sui = new BasicScrollBarUI(){
            protected JButton createDecreaseButton(int orientation){
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor = new Color(7,94,84);
                return button;
            }
            protected JButton createIncreaseButton(int orientation){
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor = new Color(7,94,84);
                return button;
            }
        };
        
        sp.getVerticalScrollBar().setUI(sui);
        f1.add(sp); 
        
        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(290,450);
        f1.setLocation(400,200);
        f1.setUndecorated(true);
        f1.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        
        try{
            String out = t1.getText();
            
            //storing text to file
            TexttoFile(out);
            
            //adding panel so that message will be seen in formated manner
            JPanel jp = formatlabel("<html><p style = \"width: 150px\">"+out+"</p></html>");
            
            j2.setLayout(new BorderLayout());
            
            //adding text in right side of the panel
            JPanel right = new JPanel(new BorderLayout());
            right.add(jp, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            right.setBackground(Color.WHITE);
            
            j2.add(vertical, BorderLayout.PAGE_START);
            
            //j2.add(jp);
            dos.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void TexttoFile(String msg){
        try(FileWriter f = new FileWriter("chat.txt");
                PrintWriter p = new PrintWriter(new BufferedWriter(f));){
            p.println("Cleint : "+msg);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static JPanel formatlabel(String out){
        JPanel j3 = new JPanel();
        j3.setLayout(new BoxLayout(j3, BoxLayout.Y_AXIS));
        j3.setBackground(Color.WHITE);
        
        JLabel l1 = new JLabel(out); 
        l1.setFont(new Font("Lucida", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(6,6,6,2));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        j3.add(l1);
        j3.add(l2);
        return j3;
    }
    
    public static void main(String args[]){
        new Server().f1.setVisible(true);
        
        String msginput;
        
        try{
            sskt = new ServerSocket(9999);
            s = sskt.accept();
            
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            while(true){
                msginput = dis.readUTF();
                JPanel jp = formatlabel("<html><p style = \"width: 150px\">"+msginput+"</p></html>");
                
                //printing coming message in left side of the panel
                JPanel left = new JPanel(new BorderLayout());
                left.add(jp, BorderLayout.LINE_START);
                left.setBackground(Color.WHITE);
                
                vertical.add(left);
                f1.validate();
            }

                //sskt.close();
                //s.close();
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
