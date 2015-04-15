import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class base extends JPanel {

	private static final long serialVersionUID = 1L;

	public static JFrame frame;

	public static JComboBox<String> manufacturer;
	public static JComboBox<String> socket;

	public static JButton addManufacturer;
	public static JButton addSocketType;

	public static JButton execute;
	public static JTextField inp;
	public static JPanel panel2;
	public static JPanel panel1;

	public static JPanel sub_panel1;
	public static JPanel sub_panel2;

	public static String path = "data.db";

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
		c.gridy = 0;
		this.add(l1, c);

		manufacturer = new JComboBox<String>();
		manufacturer.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		this.add(manufacturer, c);

		addManufacturer = new JButton("  +  ");
		addManufacturer.setFont(getFont().deriveFont(15.0f));
		addManufacturer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		this.add(addManufacturer, c);

		JLabel l2 = new JLabel("Socket:");
		l2.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(l2, c);

		socket = new JComboBox<String>();
		socket.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		this.add(socket, c);

		addSocketType = new JButton("  +  ");
		addSocketType.setFont(getFont().deriveFont(15.0f));
		addSocketType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		this.add(addSocketType, c);

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
				new core(inp.getText()).execute(inp.getText());
				frame.dispose();
			}
		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		this.add(execute, c);
	}
}
