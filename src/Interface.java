import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ashkanmehrkar on 1/14/17.
 */
public class Interface extends JFrame {
    public final static int TABLE_SIZE = 500;
    public final static int MAIN_SIZE = 300;
    private ArrayList[][] states;
    public FiniteStateMachine finiteStateMachine;


    public Interface() throws HeadlessException {
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(TABLE_SIZE, TABLE_SIZE);
        setLocationRelativeTo(null);
        setTitle("Finite State Machine");


        JTextField size = new JTextField(3);
        size.setSize(100 , 25);
        size.setLocation(200, 50 - (25/2));
        getContentPane().add(size);


        JButton submit = new JButton("Submit");
        submit.setSize(200, 25);
        submit.setLocation(150, 450 - (25/2));
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = Integer.parseInt(size.getText());
                states = new ArrayList[a][a];
                for(int i = 0; i < a; i ++) {
                    for(int j = 0; j < a; j++) {
                        states[i][j] = new ArrayList();
                    }
                }
                getContentPane().removeAll();
                JTextField finite_state_machine[][] = new JTextField[a][a];
                for(int i = 0; i < a; i++) {
                    for(int j = 0; j < a; j++) {
                        finite_state_machine[i][j] = new JTextField(1);
                        finite_state_machine[i][j].setSize(MAIN_SIZE/a, MAIN_SIZE/a);
                        finite_state_machine[i][j].setLocation((TABLE_SIZE-MAIN_SIZE)/2 + j*(MAIN_SIZE/a), (TABLE_SIZE - MAIN_SIZE)/2 + i*(MAIN_SIZE/a));
                        getContentPane().add(finite_state_machine[i][j]);
                    }
                }
                for(int i = 0; i < a; i++) {
                    JLabel jLabel = new JLabel();
                    jLabel.setText(String.valueOf(i));
                    jLabel.setSize(10, MAIN_SIZE/a);
                    jLabel.setLocation(50, (TABLE_SIZE-MAIN_SIZE)/2 + i*(MAIN_SIZE/a));
                    getContentPane().add(jLabel);

                    JLabel jLabel1 = new JLabel();
                    jLabel1.setText(String.valueOf(i));
                    jLabel1.setSize(MAIN_SIZE/a, 10);
                    jLabel1.setLocation((TABLE_SIZE-MAIN_SIZE)/2 + MAIN_SIZE/(2*a) + i*(MAIN_SIZE/a), 50);
                    getContentPane().add(jLabel1);
                }

                JButton submit1 = new JButton("Submit");
                submit1.setSize(200, 25);
                submit1.setLocation(150, 450 - (25/2));
                submit1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        states = new ArrayList[a][a];
                        for (int i = 0; i < a; i++)
                            for (int j = 0; j < a; j++)
                                states[i][j] = new ArrayList();
                        for(int i = 0; i < a; i++) {
                            for(int j = 0; j < a; j++) {
                                for(int k = 0; k < finite_state_machine[i][j].getText().length(); k++)
                                    if(finite_state_machine[i][j].getText().charAt(k) != ',')
                                        states[i][j].add(finite_state_machine[i][j].getText().charAt(k));
                            }
                        }
                        finiteStateMachine = new FiniteStateMachine(states, a);
                    }
                });

                getContentPane().add(submit1);
                repaint();
            }
        });
        getContentPane().add(submit);
        add(submit);
    }


    public static void main(String[] args) {
        Interface test = new Interface();
        test.setVisible(true);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1- Show me finite state list");
            System.out.println("2- Check me the string");
            System.out.println("3- Does my diagram have any loops?");
            System.out.println("4- Remove any existing loops");
            System.out.println("5- Create an image from my diagram");
            String tmp = scanner.nextLine();

            if(tmp.equals("1"))
                test.finiteStateMachine.finiteStateList();


            else if(tmp.equals("2")) {
                System.out.println("Please enter your string:");
                if(test.finiteStateMachine.isCorrect(scanner.nextLine(), 0, 1))
                    System.out.println("True\n");
                else
                    System.out.println("False\n");
            }


            else if(tmp.equals("3")) {
                /*int tmp1 = 0;
                for(int i = 0; i < test.finiteStateMachine.getStatesNumber(); i++) {
                    if (test.finiteStateMachine.hasLoop(i, i, 0)) {
                        tmp1 = 1;
                        break;
                    }
                }*/

                if (test.finiteStateMachine.loop(0, test.finiteStateMachine.getCheckedStates()))
                    System.out.println("Yes, it has loop\n");
                else
                    System.out.println("No, it doesn't have loop\n");
            }


            else if (tmp.equals("4")) {
                /*int tmp1 = 0;
                for(int i = 0; i < test.finiteStateMachine.getStatesNumber(); i++) {
                    while (test.finiteStateMachine.hasLoop(i, i , 1))
                        tmp1 = 1;
                }
                if (tmp1 == 1)
                    System.out.println("Done\n");
                else
                    System.out.println("No loops found\n");*/
                test.finiteStateMachine.deleteLoop(0, test.finiteStateMachine.getCheckedStates());
                System.out.println("Done");
            }


            else if (tmp.equals("5")) {
                test.finiteStateMachine.makingGraph();
                System.out.println("Done\n");
            }
        }
    }
}
