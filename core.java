/**
 * Created by HafisCZ aka MAR21. http://hafiscz.github.io/
 * This work is licenced under Creative Commons.
 * To view license terms, please visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class core extends JPanel {

	/**
	 * More Info at http://hafiscz.github.io/
	 */

	private static final long serialVersionUID = 1L;

	public static float labelFont = 18.0f;

	public static JTextField search_bar;
	public static JButton search_go;

	public static JTable cpu_table;
	public static JButton add_entry;
	public static JButton remove_entry;
	public static JButton refresh_entry;

	public static JComboBox<String> cpu_combobox;

	public static JButton ph_open;
	public static JButton ph_set;

	public static JPanel table_panel;
	public static JPanel button_panel;
	public static JPanel main_panel;
	public static JPanel search_panel;
	public static String[][] cpu_database = { { "Intel Atom", "Ne", "0" }, { "Intel Beton", "Ne", "1" }, { "Intel Lepron", "Ano", "2" } };
	public static String[] table_columns = { "Procesor", "Fotografie", "Stav" };
	public static String[] cpu_status = { "", "Vlastnìno", "Nevlastnìno" };

	// Listeners
	public static DocumentListener changeListener = new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
			update();
		}

		public void removeUpdate(DocumentEvent e) {
			update();
		}

		public void insertUpdate(DocumentEvent e) {
			update();
		}
	};

	public core() {
		this.setFocusable(false);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		main_panel = new JPanel();
		main_panel.setLayout(new FlowLayout());
		{
			table_panel = new JPanel();
			table_panel.setLayout(new FlowLayout());
			table_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Seznam"));
			{
				cpu_table = new JTable();
				cpu_table.setModel(new DefaultTableModel(cpu_database, table_columns));
				cpu_table.setPreferredScrollableViewportSize(new Dimension(500, 165));
				cpu_table.setFillsViewportHeight(true);
				cpu_table.getColumnModel().getColumn(0).setPreferredWidth(1);
				cpu_table.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						update();
					}
				});
				JScrollPane slr = new JScrollPane(cpu_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				table_panel.add(slr);
			}
			main_panel.add(table_panel);

			button_panel = new JPanel();
			button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
			{
				JPanel top_panel = new JPanel();
				top_panel.setLayout(new GridLayout(3, 1));
				top_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Databáze"));
				{
					add_entry = new JButton("Pøidat");
					add_entry.setFont(getFont().deriveFont(15.0f));
					top_panel.add(add_entry);
					refresh_entry = new JButton("Naèíst");
					refresh_entry.setFont(getFont().deriveFont(15.0f));
					top_panel.add(refresh_entry);
					remove_entry = new JButton("Odebrat");
					remove_entry.setFont(getFont().deriveFont(15.0f));
					top_panel.add(remove_entry);
				}
				button_panel.add(top_panel);

				JPanel bot_panel = new JPanel();
				bot_panel.setLayout(new GridLayout(1, 1));
				bot_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Stav"));
				{
					cpu_combobox = new JComboBox<String>(cpu_status);
					cpu_combobox.setFont(getFont().deriveFont(15.0f));
					cpu_combobox.setSelectedIndex(0);
					bot_panel.add(cpu_combobox);
				}
				button_panel.add(bot_panel);

				JPanel end_panel = new JPanel();
				end_panel.setLayout(new GridLayout(2, 1));
				end_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Fotografie"));
				{
					ph_open = new JButton("Zobrazit");
					ph_open.setFont(getFont().deriveFont(15.0f));
					end_panel.add(ph_open);
					ph_set = new JButton("Nastavit");
					ph_set.setFont(getFont().deriveFont(15.0f));
					end_panel.add(ph_set);
				}
				button_panel.add(end_panel);

			}

			main_panel.add(button_panel);
		}
		this.add(main_panel);

		search_panel = new JPanel();
		search_panel.setLayout(new FlowLayout());
		search_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Vyhledávání"));
		{
			search_bar = new JTextField(42);
			search_bar.setFont(getFont().deriveFont(15.0f));
			search_panel.add(search_bar);

			search_go = new JButton("Vyhledat");
			search_go.setFont(getFont().deriveFont(15.0f));
			search_panel.add(search_go);
		}
		this.add(search_panel);
		update();
	}

	public static void showInfo(String name, String message) {
		JOptionPane.showMessageDialog(null, message, name, JOptionPane.PLAIN_MESSAGE);
	}

	public static void update() {// TODO update

	}

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
		JFrame Frame = new JFrame("CPU-D | CC-BY-NC-ND by mar21");
		// Frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Start.class.getClass().getResource("/icon16.png")));
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setResizable(false);
		core Content = new core();
		Content.setOpaque(true);
		Frame.setContentPane(Content);
		Frame.pack();
		Frame.setVisible(true);
	}

	public static boolean isAnyCellSelected(JTable table) {
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			for (int y = 0; y < table.getModel().getColumnCount(); y++) {
				if (table.isCellSelected(i, y)) return true;
			}
		}
		return false;
	}

}