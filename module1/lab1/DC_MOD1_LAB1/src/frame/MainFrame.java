package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


class Thread1 extends Thread {
    @Override
    public void run() {
        synchronized (MainFrame.class) {
            long val = MainFrame.getInstance().getValue();
            while (val > 1000 && MainFrame.getInstance().isRunning()) {
                Thread.yield();
                val = MainFrame.getInstance().getValue();
                MainFrame.getInstance().setValue(MainFrame.getInstance().getValue() - 1);
            }
        }
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        synchronized (MainFrame.class) {
            long val=MainFrame.getInstance().getValue();
            while (val < 9000 && MainFrame.getInstance().isRunning()) {
                Thread.yield();
                val=MainFrame.getInstance().getValue();
                MainFrame.getInstance().setValue(MainFrame.getInstance().getValue() + 1);
            }
        }
    }
}

class Thread3 extends Thread {
    @Override
    public void run() {
        synchronized (MainFrame.class) {
            while (MainFrame.getInstance().isRunning()) {
                Thread.yield();
                MainFrame.getInstance().setScrollValue((int) (MainFrame.getInstance().getValue() / 100));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class MainFrame extends JFrame implements ActionListener, AdjustmentListener {
    Thread thread1, thread2,thread3;
    private static final MainFrame ourInstance = new MainFrame();
    JScrollBar scrollBar = new JScrollBar(Adjustable.HORIZONTAL, 50, 1, 0, 100);
    JScrollBar thread1Prior = new JScrollBar(Adjustable.VERTICAL, Thread.NORM_PRIORITY, 1, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY+1);
    JScrollBar thread2Prior = new JScrollBar(Adjustable.VERTICAL, Thread.NORM_PRIORITY, 1, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY+1);
    JButton button = new JButton("Пуск");
    volatile long value=5000;
    volatile boolean running = false;


    public static MainFrame getInstance() {
        return ourInstance;
    }

    private MainFrame() {
        this.getContentPane().add(BorderLayout.NORTH, scrollBar);
        this.getContentPane().add(BorderLayout.WEST, thread1Prior);
        this.getContentPane().add(BorderLayout.EAST, thread2Prior);
        this.getContentPane().add(BorderLayout.SOUTH, button);
        button.addActionListener(this);
        thread1Prior.addAdjustmentListener(this);
        thread2Prior.addAdjustmentListener(this);
    }

    public long getValue(){
        return value;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setValue(long val){
        value=val;
    }

    public void setScrollValue(int value){
        scrollBar.setValue(value);
    }

    public void actionPerformed(ActionEvent event)
    {
        if (!isRunning()) {
            button.setText("Стоп");
            thread1 = new Thread1();
            thread2 = new Thread2();
            thread3 = new Thread3();
            thread1.setDaemon(true);
            thread2.setDaemon(true);
            thread3.setDaemon(true);
            setRunning(true);
            thread1.setPriority(thread1Prior.getValue());
            System.out.println("Thread1 priority: " + thread1.getPriority());
            thread2.setPriority(thread2Prior.getValue());
            System.out.println("Thread2 priority: " + thread2.getPriority());
            thread1.start();
            thread2.start();
            thread3.start();
        }
        else {
            setRunning(false);
            button.setText("Пуск");
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if (e.getSource().equals(thread1Prior)) {
            if (thread1 != null) {
                thread1.setPriority(thread1Prior.getValue());
            }
        }
        if (e.getSource().equals(thread2Prior)) {
            if (thread2 != null) {
                thread2.setPriority(thread2Prior.getValue());
            }
        }
    }
}
