/**
 * Created by HafisCZ aka MAR21. http://hafiscz.github.io/
 * This work is licenced under Creative Commons.
 * To view license terms, please visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import entry.EntryProcessor;

public class core extends JPanel {

	/**
	 * More Info at http://hafiscz.github.io/
	 */

	private static final long serialVersionUID = 1L;

	public static float labelFont = 18.0f;

	public static JTextField search_bar;
	public static JButton search_go;

	public static JTable cpu_tableA;
	public static JTable cpu_tableB;
	public static JTable cpu_tableC;
	public static JButton add_entry;
	public static JButton remove_entry;
	public static JButton refresh_entry;

	public static JButton manage_atts;

	public static JComboBox<String> cpu_combobox;

	public static JTabbedPane cpu_owner_sort;

	public static JButton ph_open;
	public static JButton ph_set;

	public static JPanel table_panelA;
	public static JPanel table_panelB;
	public static JPanel table_panelC;
	public static JPanel button_panel;
	public static JPanel main_panel;
	public static JPanel search_panel;
	public static Object[][] cpu_database = {};
	public static String[] table_columns = { "Výrobce", "Socket", "Procesor", "Fotografie", "Atributy", "Stav" };
	public static String[] cpu_status = { "Nevlastnìno", "Vlastnìno" };

	public static String wantedMan = null;
	public static String wantedSoc = null;

	public JPanel global_panel;

	public static String datafile = "";

	public static enum UPDATE {
		LOW, MEDIUM, HIGH
	};

	public void execute(String path) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JFrame frame = new JFrame("CPU-D | CC-BY-NC-ND by mar21 | BETA 6");
		// Frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Start.class.getClass().getResource("/icon16.png")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		core Content = new core(path);
		Content.setOpaque(true);
		frame.setContentPane(Content);
		frame.pack();
		frame.setVisible(true);

	}

	public core(String path) {
		core.datafile = path;
		this.setFocusable(false);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		main_panel = new JPanel();
		main_panel.setLayout(new FlowLayout());
		{

			table_panelA = new JPanel();
			table_panelA.setLayout(new FlowLayout());
			table_panelA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Seznam"));
			{
				cpu_tableA = new JTable();
				cpu_tableA.setModel(new DefaultTableModel(cpu_database, table_columns));
				cpu_tableA.setPreferredScrollableViewportSize(new Dimension(500, 220));
				cpu_tableA.setFillsViewportHeight(true);
				cpu_tableA.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						fireUpdate(cpu_tableA, UPDATE.LOW, datafile);
					}
				});

				JScrollPane slr = new JScrollPane(cpu_tableA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				table_panelA.add(slr);
			}

			table_panelB = new JPanel();
			table_panelB.setLayout(new FlowLayout());
			table_panelB.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Seznam"));
			{
				cpu_tableB = new JTable();
				cpu_tableB.setModel(new DefaultTableModel(cpu_database, table_columns));
				cpu_tableB.setPreferredScrollableViewportSize(new Dimension(500, 220));
				cpu_tableB.setFillsViewportHeight(true);
				cpu_tableB.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						fireUpdate(cpu_tableB, UPDATE.LOW, datafile);
					}
				});
				JScrollPane slr = new JScrollPane(cpu_tableB, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				table_panelB.add(slr);
			}

			table_panelC = new JPanel();
			table_panelC.setLayout(new FlowLayout());
			table_panelC.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Seznam"));
			{
				cpu_tableC = new JTable();
				cpu_tableC.setModel(new DefaultTableModel(cpu_database, table_columns));
				cpu_tableC.setPreferredScrollableViewportSize(new Dimension(500, 220));
				cpu_tableC.setFillsViewportHeight(true);
				cpu_tableC.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						fireUpdate(cpu_tableC, UPDATE.LOW, datafile);
					}
				});
				JScrollPane slr = new JScrollPane(cpu_tableC, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				table_panelC.add(slr);
			}

			cpu_owner_sort = new JTabbedPane();
			cpu_owner_sort.addTab("Všechny", table_panelA);
			cpu_owner_sort.addTab(cpu_status[1], table_panelB);
			cpu_owner_sort.addTab(cpu_status[0], table_panelC);
			main_panel.add(cpu_owner_sort);

			button_panel = new JPanel();
			button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
			{
				JPanel top_panel = new JPanel();
				top_panel.setLayout(new GridLayout(3, 1));
				top_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Databáze"));
				{
					add_entry = new JButton("Pøidat");
					add_entry.setFont(getFont().deriveFont(15.0f));
					add_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								EntryProcessor eP = new EntryProcessor(datafile).readEntries();
								addNewEntry(eP);
								fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
							} catch (Exception f) {
								f.printStackTrace();
							}

						}
					});
					top_panel.add(add_entry);
					refresh_entry = new JButton("Naèíst");
					refresh_entry.setFont(getFont().deriveFont(15.0f));
					refresh_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
						}
					});
					top_panel.add(refresh_entry);
					remove_entry = new JButton("Odebrat");
					remove_entry.setFont(getFont().deriveFont(15.0f));
					remove_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								if (isAnyCellSelected(cpu_tableA)) {
									if (JOptionPane.showConfirmDialog(null, "Opravdu chcete odebrat tento prvek ?", "Odebrat prvek", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
										int selected = cpu_tableA.getSelectedRow();
										EntryProcessor eP = new EntryProcessor(datafile);
										Object[] rt = cpu_database[selected];
										String remove = rt[0] + "^";
										remove += rt[1] + "^";
										remove += rt[2] + "^";
										remove += rt[3] + "^";
										remove += rt[4] + "^";
										remove += rt[5];
										eP.readEntries().removeEntry(remove, false);
										eP.saveFile();
										fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					top_panel.add(remove_entry);
				}
				button_panel.add(top_panel);

				JPanel bot_panel2 = new JPanel();
				bot_panel2.setLayout(new GridLayout(1, 1));
				bot_panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Atributy"));
				{
					manage_atts = new JButton("Upravit");
					manage_atts.setFont(getFont().deriveFont(15.0f));
					manage_atts.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								String atts = showInputDialog(null, "Atributy: ", "Modifikovat atributy", cpu_database[cpu_tableA.getSelectedRow()][4].toString(), 30, false);
								if (atts != "" && atts != null) {
									int selected = cpu_tableA.getSelectedRow();
									if (cpu_database[selected][2].toString() != atts) {
										// TODO att manager
										fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					bot_panel2.add(manage_atts);
				}
				button_panel.add(bot_panel2);

				JPanel bot_panel = new JPanel();
				bot_panel.setLayout(new GridLayout(1, 1));
				bot_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Stav"));
				{
					cpu_combobox = new JComboBox<String>(cpu_status);
					cpu_combobox.setFont(getFont().deriveFont(15.0f));
					cpu_combobox.setSelectedIndex(0);
					cpu_combobox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent event) {
							try {
								if (isAnyCellSelected(cpu_tableA)) {
									int selected = cpu_tableA.getSelectedRow();
									if (Integer.parseInt(cpu_database[selected][5].toString()) != cpu_combobox.getSelectedIndex()) {
										// TODO combobox
										fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					bot_panel.add(cpu_combobox);
				}
				button_panel.add(bot_panel);

				JPanel end_panel = new JPanel();
				end_panel.setLayout(new GridLayout(2, 1));
				end_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Fotografie"));
				{
					ph_open = new JButton("Zobrazit");
					ph_open.setFont(getFont().deriveFont(15.0f));
					ph_open.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							showEntryImage();
						}
					});
					end_panel.add(ph_open);
					ph_set = new JButton("Nastavit");
					ph_set.setFont(getFont().deriveFont(15.0f));
					ph_set.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								String entry = showInputDialog(null, "Cesta: ", "Pøidat fotografii", cpu_database[cpu_tableA.getSelectedRow()][3].toString(), 20, true);
								if (entry != "" && entry != null) {
									int selected = cpu_tableA.getSelectedRow();
									if (cpu_database[selected][1].toString() != entry) {
										// TODO photo set
										fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
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
			search_go.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String phrase = search_bar.getText();
					for (int i = 0; i < cpu_database.length; i++) {
						if (cpu_database[i][2].toString().contains(phrase)) {
							cpu_tableA.setRowSelectionInterval(i, i);
							break;
						} else {
							cpu_tableA.clearSelection();
						}
					}
					fireUpdate(cpu_tableA, UPDATE.LOW, datafile);
				}
			});
			search_panel.add(search_go);
		}
		this.add(search_panel);
		fireUpdate(cpu_tableA, UPDATE.HIGH, datafile);
	}

	public static void showInfo(String name, String message) {
		JOptionPane.showMessageDialog(null, message, name, JOptionPane.PLAIN_MESSAGE);
	}

	public void showEntryImage() {
		String path = cpu_database[cpu_tableA.getSelectedRow()][3].toString();
		if (new File(path).exists()) {
			try {
				JPanel p = new JPanel(new GridLayout(1, 1));
				BufferedImage myPicture = ImageIO.read(new File(path));
				JLabel picLabel = new JLabel(new ImageIcon(myPicture));
				p.add(picLabel);
				JOptionPane.showMessageDialog(null, p, "Fotografie", JOptionPane.PLAIN_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Object[][] readFromFile(String file) {
		try {
			EntryProcessor pc = new EntryProcessor(file).readEntries();
			return pc.customFilter(wantedMan, wantedSoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String[][] {};
	}

	/*
	 * if (new File(file).isFile()) {
	 * // clearDupes(file);
	 * String datas = refactor(file);
	 * String[] block_data = datas.split("\\|");
	 * for (int i = 0; i < block_data.length; i++) {
	 * block_data[i] = block_data[i].substring(0, 1).toUpperCase() + block_data[i].substring(1);
	 * }
	 * Arrays.sort(block_data);
	 * Object[][] raw_data = new Object[block_data.length][4];
	 * for (int i = 0; i < block_data.length; i++) {
	 * String[] cut_data = block_data[i].split("\\^");
	 * for (int y = 0; y < 4; y++) {
	 * raw_data[i][y] = cut_data[y];
	 * }
	 * }
	 * return raw_data;
	 */

	public static boolean isAnyCellSelected(JTable table) {
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			for (int y = 0; y < table.getModel().getColumnCount(); y++) {
				if (table.isCellSelected(i, y)) return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param file Path to database file
	 * @return Returns clean version of scanned file
	 * @throws IOException
	 */
	public static String refactor(String file) throws IOException {
		if (new File(file).exists()) {
			String datas = new String(Files.readAllBytes(Paths.get(file)));
			if (datas.contains("||")) datas = datas.replace("||", "|");
			if (datas.charAt(0) == '|') datas = datas.substring(1);
			return datas;
		} else return null;
	}

	public void addNewEntry(EntryProcessor e) {

		JPanel ps = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel l_manufacturer = new JLabel("Vyrobce: ");
		l_manufacturer.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		ps.add(l_manufacturer, c);

		JLabel l_socket = new JLabel("Socket: ");
		l_socket.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		ps.add(l_socket, c);

		JLabel l_model = new JLabel("Nazev: ");
		l_model.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		ps.add(l_model, c);

		JTextField f_manufacturer = new JTextField(30);
		f_manufacturer.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		ps.add(f_manufacturer, c);

		JTextField f_socket = new JTextField(30);
		f_socket.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		ps.add(f_socket, c);

		JTextField f_model = new JTextField(30);
		f_model.setFont(getFont().deriveFont(15.0f));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		ps.add(f_model, c);

		try {
			if (JOptionPane.showConfirmDialog(null, ps, "Pridat prvek", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				if (f_manufacturer.getText().equals("") || f_socket.getText().equals("") || f_model.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Prvek nebyl pridan z duvodu chybejicich parametru !");
				} else {
					e.writeEntry(e.getEntryDivider() + f_manufacturer.getText() + e.getDivider() + f_socket.getText() + e.getDivider() + f_model.getText());
					for (int i = 0; i < 3; i++) {
						e.writeEntry(e.getDivider() + ((i == 2) ? "0" : " "));
					}
					e.saveFile();
				}
			}
		} catch (Exception d) {
			d.printStackTrace();
		}
	}

	/**
	 * 
	 * @param c Parent (null)
	 * @param message Message next to input JTextField
	 * @param description Description of message
	 * @param defaultInput Predefined input string written in input JTextField
	 * @param fieldWidth Default width of input JTextField
	 * @return
	 */
	public String showInputDialog(Component c, String message, final String description, String defaultInput, int fieldWidth, boolean chooser) {
		JPanel p = new JPanel(new FlowLayout());
		JLabel message_label = new JLabel(message);
		message_label.setFont(getFont().deriveFont(15.0f));
		final JTextField input = new JTextField(fieldWidth);
		input.setFont(getFont().deriveFont(15.0f));
		input.setText(defaultInput);
		p.add(message_label);
		p.add(input);
		if (chooser) {
			JButton bt = new JButton("...");
			bt.setFont(getFont().deriveFont(15.0f));
			bt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser ch = new JFileChooser();
					ch.setCurrentDirectory(new java.io.File("."));
					ch.setDialogTitle(description);
					ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
					ch.setFileHidingEnabled(false);
					ch.setAcceptAllFileFilterUsed(false);
					if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						input.setText((ch.getSelectedFile()).toString());
					}
				}
			});
			p.add(bt);
		}
		return (JOptionPane.showConfirmDialog(c, p, description, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) ? input.getText() : null;
	}

	/**
	 * Update function which updates all needded JComponents with different levels of update, eg.: LOW level for JComponents without used JTable
	 * 
	 * @param table Used JTable
	 * @param level Level of update from UPDATE enum
	 * @param source Path to database file
	 */
	public void fireUpdate(JTable table, UPDATE level, String source) {

		if (level == UPDATE.MEDIUM || level == UPDATE.HIGH) {

			DefaultTableModel dm = (DefaultTableModel) table.getModel();
			dm.getDataVector().removeAllElements();
			dm.fireTableDataChanged();

			cpu_database = readFromFile(source);

			table.setModel(new DefaultTableModel(cpu_database, table_columns) {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
			table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					setForeground(isSelected ? Color.WHITE : Color.BLACK);
					if (column == 5) {
						setBackground(Integer.parseInt(value.toString()) == 0 ? Color.RED.darker() : Color.GREEN.darker());
						setText(Integer.parseInt(value.toString()) == 0 ? "Nevlastnìno" : "Vlastnìno");
					} else {
						setBackground(isSelected ? javax.swing.UIManager.getColor("Table.selectionBackground") : Color.WHITE);
						setText(value != null ? value.toString() : "<null>");
					}
					return this;
				}
			});
		}

		if (level == UPDATE.LOW || level == UPDATE.HIGH) {
			if (isAnyCellSelected(table)) {
				int selected = table.getSelectedRow();
				ph_set.setEnabled(true);
				manage_atts.setEnabled(true);
				remove_entry.setEnabled(true);
				cpu_combobox.setEnabled(true);
				ph_open.setEnabled((new File(cpu_database[selected][1].toString()).exists()) ? true : false);
				cpu_combobox.setSelectedIndex(Integer.parseInt(cpu_database[selected][5].toString()));
			} else {
				ph_set.setEnabled(false);
				manage_atts.setEnabled(false);
				remove_entry.setEnabled(false);
				ph_open.setEnabled(false);
				cpu_combobox.setEnabled(false);
			}
		}
	}

	public static void clearDupes(String file) throws IOException {
		if (new File(file).exists()) {
			String lin_data = refactor(file);
			String[] raw_data = lin_data.split("\\|");
			HashSet<String> hset = new HashSet<>();
			for (int i = 0; i < raw_data.length; i++) {
				hset.add(raw_data[i]);
			}
			String[] clr_data = new String[hset.size()];
			int i = 0;
			for (Iterator<String> iter = hset.iterator(); iter.hasNext();) {
				clr_data[i++] = iter.next();
			}
			BufferedWriter fix = new BufferedWriter(new FileWriter(file));
			for (String s : clr_data) {
				fix.write(s + "|");
			}
			fix.close();
		}
	}

}