package missiledefense;

import missiledefense.Game;
import missiledefense.Main;
import missiledefense.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Main extends JFrame{
	private static final long serialVersionUID = 1L;
	static Settings settings = new Settings();
	static JFrame frame;
	static Game game;
	static JPanel contentPane;
	
	private Game getPanel() {
        if (game == null) {
            game = new Game(); // The panel is created
        }
        return game;
    }

    public Main() {
        super();
        initialize();
    }

    private void initialize() {
        this.setResizable(false);
        // Position on the desktop
        this.setLocation(settings.screenPositionX, settings.screenPositionY);
        this.setSize(settings.screenWidth, settings.screenHeight);
        this.setContentPane(getContentPanel());
        this.setTitle(Game.gameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(game);
        game.setBackground(Color.BLACK);

    }

    private JPanel getContentPanel() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(getPanel(), BorderLayout.CENTER);
        }
        return contentPane;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main instance = new Main();
                instance.setVisible(true);
            }
        });
    }

}
