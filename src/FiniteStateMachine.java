import java.io.File;
import java.util.ArrayList;

/**
 * Created by ashkanmehrkar on 1/14/17.
 */
public class FiniteStateMachine {
    private ArrayList[][] states;
    private boolean[] checkedStates;
    private int statesNumber;
    private GraphViz gv;


    public FiniteStateMachine(ArrayList[][] arrayLists, int n) {
        statesNumber = n;
        states = new ArrayList[n][n];
        checkedStates = new boolean[n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
                states[i][j] = new ArrayList();
        }

        for(int i = 0; i < n; i++)
            checkedStates[i] = false;

        states = arrayLists;
    }


    public void finiteStateList() {
        String s = "";

        for(int i = 0; i < statesNumber; i++) {
            for(int j = 0; j < statesNumber; j++) {
                if(states[i][j].size() != 0) {
                    s = String.valueOf(i) + " : (" + String.valueOf(j) + " , ";
                    System.out.println(s + states[i][j] + " )");
                }
                s = "";
            }
        }

        System.out.println();
        System.out.println();
    }


    public boolean isCorrect(String s, int a, int b) {
        if(s.length() == 0)
            return true;
        char c = s.charAt(0);

            for(int i = a; i < statesNumber; i++) {
                for(int j = 0; j < statesNumber; j++) {
                    if(!states[i][j].isEmpty()) {
                        for(int z = 0; z < states[i][j].size(); z++) {
                            if (states[i][j].get(z).equals(c))
                                return isCorrect(s.substring(1), j, 0);
                        }
                    }
                }
                if(b == 0)
                    return false;
            }

        return false;
    }


    /*public boolean hasLoop(int presentState, int wantedState, int delete) {

        for(int j = 0; j < statesNumber; j++) {
            if (!states[presentState][j].isEmpty()) {
                if(j == wantedState) {
                    if (delete == 1)
                        states[presentState][j] = new ArrayList();
                    return true;
                }
                else if(!checkedStates[presentState][j]) {
                    checkedStates[presentState][j] = true;
                    return hasLoop(j, wantedState, delete, arr, n);
                }
            }
        }

        return false;
    }*/


    public boolean loop(int presentState, boolean []checked) {
        if(checked[presentState])
            return true;
        else
            checked[presentState] = true;

        boolean tmp = false;

        for(int j = 0; j < statesNumber; j++) {
            if(!states[presentState][j].isEmpty())
                tmp = tmp || loop(j, checked);
        }
        return tmp;
    }

    public void deleteLoop(int presentState, boolean []checked) {
        checked[presentState] = true;

        for(int j = 0; j < statesNumber; j++) {
            if(!states[presentState][j].isEmpty() && !checked[j])
                deleteLoop(j, checked);
            else if (!states[presentState][j].isEmpty() && checked[j])
                states[presentState][j] = new ArrayList();
        }
    }


    public int getStatesNumber() {
        return statesNumber;
    }


    public boolean[] getCheckedStates() {
        return checkedStates;
    }


    public void makingGraph() {
        gv = new GraphViz();
        gv.addln(gv.start_graph());

        for (int i = 0; i < statesNumber; i++) {
            for (int j = 0; j < statesNumber; j++) {
                if (!states[i][j].isEmpty()) {
                    String s = String.valueOf(i) + " -> " + String.valueOf(j) + "[label=";
                    for(int k = 0; k < states[i][j].size(); k++) {
                        /*if(k != 0)
                            s = s.concat(" ,");*/
                        s = s.concat(String.valueOf(states[i][j].get(k)));
                    }
                    s = s.concat("]");
                    System.out.println(s);
                    gv.addln(s);
                }
            }
        }

        gv.addln(gv.end_graph());
        String type = "gif";
        String representationType= "dot";
        File out = new File("out." + type);
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), out );
    }
}
