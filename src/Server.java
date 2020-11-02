
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;


public class Server extends JFrame implements ActionListener{
    
    static Socket s;
    static ServerSocket sskt;
    static DataInputStream dis;
    static DataOutputStream dos;
    
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea ta1;
    
    Server(){
        
        //add panel where header will reside
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 350, 50);
        add(p1);
        
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
        add(t1);
        
        //add send button
        b1 = new JButton("Send");
        b1.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        b1.setBackground(new Color(7,94,84));
        b1.setForeground(Color.WHITE);
        b1.setBounds(218, 415, 66, 30);
        b1.addActionListener(this);
        add(b1);
        
        //add text area where message can be seen
        ta1 = new JTextArea();
        ta1.setBounds(6, 55, 280, 355);
        ta1.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        ta1.setEditable(false);
        ta1.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        add(ta1);
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(290,450);
        setLocation(400,200);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
            String out = t1.getText();
            ta1.setText(ta1.getText()+"\n\t\t   "+out);
            dos.writeUTF(out);
            t1.setText("");
        }catch(Exception e){}
    }
    
    public static void main(String args[]){
        Server server = new Server();
        
        String msginput;
        
        try{
            sskt = new ServerSocket(9999);
            s = sskt.accept();
            
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            while(true){
                msginput = dis.readUTF();
                ta1.setText(ta1.getText()+"\n"+msginput);
            }

                //sskt.close();
                //s.close();
            
        }catch(Exception e){}
    }
}
