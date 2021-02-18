package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Thread1 extends Thread {
    @Override
    public void run() {
        long val=MainFrame.getInstance().getValue();
        while (val > 10*MainFrame.mult && MainFrame.getInstance().getSemaphore()==1) {
            Thread.yield();
            synchronized (this) {
                val = MainFrame.getInstance().getValue();
                MainFrame.getInstance().setValue(MainFrame.getInstance().getValue() - 1);
            }
        }
        MainFrame.getInstance().setSemaphore(0);
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        long val=MainFrame.getInstance().getValue();
        while (val<90*MainFrame.mult && MainFrame.getInstance().getSemaphore()==1) {
            Thread.yield();
            synchronized (this) {
                val = MainFrame.getInstance().getValue();
                MainFrame.getInstance().setValue(MainFrame.getInstance().getValue() + 1);
            }
        }
        MainFrame.getInstance().setSemaphore(0);
    }
}

class Thread3 extends Thread {
    @Override
    public void run() {
        while (true) {
            Thread.yield();
            MainFrame.getInstance().setScrollValue((int)(MainFrame.getInstance().getValue()/MainFrame.mult));
            System.out.println("Value is "+MainFrame.getInstance().getValue()/1000);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MainFrame extends JFrame implements ActionListener {
    static final int mult=1000000;

    Thread thread1, thread2,thread3;
    private static final MainFrame ourInstance = new MainFrame();
    JScrollBar scrollBar = new JScrollBar(Adjustable.HORIZONTAL, 50, 1, 0, 100);
    JButton start1 = new JButton("Пуск1");
    JButton stop1 = new JButton("Стоп1");
    JButton start2 = new JButton("Пуск2");
    JButton stop2 = new JButton("Стоп2");
    volatile long value=50*mult;
    volatile Integer semaphore = 0;

    public int getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(int semaphore) {
        this.semaphore = semaphore;
    }

    public static MainFrame getInstance() {
        return ourInstance;
    }

    private MainFrame() {
        this.getContentPane().add(BorderLayout.NORTH, scrollBar);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(BorderLayout.SOUTH, start1);
        panel.add(BorderLayout.SOUTH, stop1);
        panel.add(BorderLayout.SOUTH, start2);
        panel.add(BorderLayout.SOUTH, stop2);
        this.getContentPane().add(BorderLayout.SOUTH,panel);
        start1.addActionListener(this);
        stop1.addActionListener(this);
        start2.addActionListener(this);
        stop2.addActionListener(this);
        thread3=new Thread3();
        thread3.start();
        stop1.setEnabled(false);
        stop2.setEnabled(false);
    }

    public long getValue(){
        return value;
    }

    public void setValue(long val){
        value=val;
    }

    public void setScrollValue(int value){
        scrollBar.setValue(value);
    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource().equals(start1))
            synchronized (this) {
                if (semaphore == 0) {
                    semaphore = 1;
                    thread1 = new Thread1();
                    thread1.start();
                    thread1.setPriority(Thread.MIN_PRIORITY);
                    stop1.setEnabled(true);
                    stop2.setEnabled(false);
                }
                else
                    JOptionPane.showMessageDialog(this, "Критична область зайнята");
            }
        if (event.getSource().equals(start2))
            synchronized (this) {
                if (semaphore == 0) {
                    semaphore = 1;
                    thread2 = new Thread2();
                    thread2.start();
                    thread2.setPriority(Thread.MAX_PRIORITY);
                    stop1.setEnabled(false);
                    stop2.setEnabled(true);
                }
                else
                    JOptionPane.showMessageDialog(this, "Критична область зайнята");
            }
        if (event.getSource().equals(stop1) || event.getSource().equals(stop2))
        {
            synchronized (this) {
                semaphore = 0;
                stop1.setEnabled(false);
                stop2.setEnabled(false);
            }
        }
    }

}
