/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moonbase.beta;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameRender extends JFrame {

    public GameRender() {
        initComponents();
    }

    public static void main(String[] args) {
        // Important http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(() -> {
            new GameRender().setVisible(true);
        });
    }

    private void initComponents() {
        add(new Board());
        setSize(1366, 768);
        setTitle("MoonBase Beta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
