import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class base extends JPanel {

	private static final long serialVersionUID = 1L;

	public static JFrame frame;

	public static JTextField manufacturer;
	public static JTextField socket;

	public static JButton execute;
	public static JButton seller;		

	public static JTextField inp;
	public static JTextField inp2;	

	public static JPanel panel2;
	public static JPanel panel1;

	public static JPanel sub_panel1;
	public static JPanel sub_panel2;

	public static String path = "data.db";
	public static String path2 = "sell.db";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setupGui();
			}
		});

	}

	public static void setupGui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		frame = new JFrame("CPU-D | CC-BY-NC-ND by mar21 | BETA 6");
		// Frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Start.class.getClass().getResource("/icon16.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		base Content = new base();
		Content.setOpaque(true);
		frame.setContentPane(Content);
		frame.pack();
		frame.setVisible(true);
	}

	public base() {
		this.setFocusable(false);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel l1 = new JLabel("Vyrobce:");
		l1.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 0;
		this.add(l1, c);

		manufacturer = new JTextField();
		manufacturer.setText("");
		manufacturer.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 0;
		this.add(manufacturer, c);

		JLabel l2 = new JLabel("Socket:");
		l2.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 1;
		this.add(l2, c);

		socket = new JTextField();
		socket.setText("");
		socket.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 1;
		this.add(socket, c);

		inp = new JTextField(30);
		inp.setFont(getFont().deriveFont(15.0f));
		inp.setText(path);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 2;
		this.add(inp, c);

		execute = new JButton("Start");
		execute.setFont(getFont().deriveFont(15.0f));
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String mn = manufacturer.getText();
				if (mn.equals("")) mn = null;
				String ms = socket.getText();
				if (ms.equals("")) ms = null;
				new core(inp.getText(), ms, mn).execute(inp.getText(), ms, mn);
				frame.dispose();
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 2;
		this.add(execute, c);

		inp2 = new JTextField(30);
		inp2.setFont(getFont().deriveFont(15.0f));
		inp2.setText(path2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 3;
		this.add(inp2, c);

		seller = new JButton("Prodej");
		seller.setFont(getFont().deriveFont(15.0f));
		seller.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String mn = manufacturer.getText();
				if (mn.equals("")) mn = null;
				String ms = socket.getText();
				if (ms.equals("")) ms = null;
				new sellCore(inp2.getText(), ms, mn).execute(inp2.getText(), ms, mn);				

				frame.dispose();
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 3;
		this.add(seller, c);
	}
}
