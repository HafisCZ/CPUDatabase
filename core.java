/**
 * Created by HafisCZ aka MAR21. http://hafiscz.github.io/
 * This work is licenced under Creative Commons.
 * To view license terms, please visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.table.DefaultTableCellRenderer;
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

	public static JButton manage_atts;

	public static JComboBox<String> cpu_combobox;

	public static JButton ph_open;
	public static JButton ph_set;

	public static JPanel table_panel;
	public static JPanel button_panel;
	public static JPanel main_panel;
	public static JPanel search_panel;
	public static Object[][] cpu_database = {};
	public static String[] table_columns = { "Procesor", "Fotografie", "Atributy", "Stav" };
	public static String[] cpu_status = { "Nevlastnìno", "Vlastnìno" };

	public static final String datafile = "data.db";

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
				cpu_table.getColumnModel().getColumn(1).setCellRenderer(cpu_table.getDefaultRenderer(ImageIcon.class));
				cpu_table.setPreferredScrollableViewportSize(new Dimension(500, 220));
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
					add_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								String entry = createEntry();
								if (entry != null) {
									BufferedWriter out = new BufferedWriter(new FileWriter(datafile, true));
									out.write("|" + entry + "^-^-^0");
									out.close();
									updateTable(datafile);
									update();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					top_panel.add(add_entry);
					refresh_entry = new JButton("Naèíst");
					refresh_entry.setFont(getFont().deriveFont(15.0f));
					refresh_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							updateTable(datafile);
							update();
						}
					});
					top_panel.add(refresh_entry);
					remove_entry = new JButton("Odebrat");
					remove_entry.setFont(getFont().deriveFont(15.0f));
					remove_entry.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								if (isAnyCellSelected(cpu_table)) {
									String whole = new String(Files.readAllBytes(Paths.get(datafile)));
									int selected = cpu_table.getSelectedRow();
									whole = whole.replace(cpu_database[selected][0] + "^" + cpu_database[selected][1] + "^"
											+ cpu_database[selected][2] + "^" + cpu_database[selected][3], "");
									if (whole.contains("||")) whole = whole.replace("||", "|");
									BufferedWriter w = new BufferedWriter(new FileWriter(datafile));
									w.write(whole);
									w.close();
									updateTable(datafile);
									update();
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
								String atts = modifyAttributes();
								if (atts != "" && atts != null) {
									String whole = new String(Files.readAllBytes(Paths.get(datafile)));
									int selected = cpu_table.getSelectedRow();
									if (cpu_database[selected][2].toString() != atts) {
										whole = whole.replace(cpu_database[selected][0] + "^" + cpu_database[selected][1] + "^"
												+ cpu_database[selected][2] + "^" + cpu_database[selected][3], cpu_database[selected][0] + "^"
												+ cpu_database[selected][1] + "^" + atts + "^" + cpu_database[selected][3]);
										if (whole.contains("||")) whole = whole.replace("||", "|");
										BufferedWriter w = new BufferedWriter(new FileWriter(datafile));
										w.write(whole);
										w.close();
										updateTable(datafile);
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
								if (isAnyCellSelected(cpu_table)) {
									String whole = new String(Files.readAllBytes(Paths.get(datafile)));
									int selected = cpu_table.getSelectedRow();
									if (Integer.parseInt(cpu_database[selected][3].toString()) != cpu_combobox.getSelectedIndex()) {
										whole = whole.replace(cpu_database[selected][0] + "^" + cpu_database[selected][1] + "^"
												+ cpu_database[selected][2] + "^" + cpu_database[selected][3], cpu_database[selected][0] + "^"
												+ cpu_database[selected][1] + "^" + cpu_database[selected][2] + "^" + cpu_combobox.getSelectedIndex());
										if (whole.contains("||")) whole = whole.replace("||", "|");
										BufferedWriter w = new BufferedWriter(new FileWriter(datafile));
										w.write(whole);
										w.close();
										updateTable(datafile);
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
								String entry = createEntryLink();
								if (entry != "" && entry != null) {
									String whole = new String(Files.readAllBytes(Paths.get(datafile)));
									int selected = cpu_table.getSelectedRow();
									if (cpu_database[selected][1].toString() != entry) {
										whole = whole.replace(cpu_database[selected][0] + "^" + cpu_database[selected][1] + "^"
												+ cpu_database[selected][2] + "^" + cpu_database[selected][3], cpu_database[selected][0] + "^"
												+ entry + "^" + cpu_database[selected][2] + "^" + cpu_database[selected][3]);
										if (whole.contains("||")) whole = whole.replace("||", "|");
										BufferedWriter w = new BufferedWriter(new FileWriter(datafile));
										w.write(whole);
										w.close();
										updateTable(datafile);
										update();
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
						if (cpu_database[i][0].toString().contains(phrase)) {
							cpu_table.setRowSelectionInterval(i, i);
							break;
						} else {
							cpu_table.clearSelection();
						}
					}
					update();
				}
			});
			search_panel.add(search_go);
		}
		this.add(search_panel);
		updateTable(datafile);
		update();
	}

	public static void showInfo(String name, String message) {
		JOptionPane.showMessageDialog(null, message, name, JOptionPane.PLAIN_MESSAGE);
	}

	public static void updateTable(String file) {
		DefaultTableModel dm = (DefaultTableModel) cpu_table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		cpu_database = readFromFile(file);
		cpu_table.setModel(new DefaultTableModel(cpu_database, table_columns));
		cpu_table.getColumnModel().getColumn(0).setPreferredWidth(1);
		cpu_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (column == 3) {
					Integer sel_val = Integer.parseInt(value.toString());
					if (sel_val == 0) {
						setBackground(Color.RED.darker());
					} else {
						setBackground(Color.GREEN.darker());
					}
				} else {
					setBackground(isSelected ? javax.swing.UIManager.getColor("Table.selectionBackground") : Color.WHITE);
				}
				setForeground(isSelected ? Color.WHITE : Color.BLACK);
				if (column == 3) {
					setText(Integer.parseInt(value.toString()) == 0 ? "Nevlastnìno" : "Vlastnìno");
				} else {
					setText(value != null ? value.toString() : "<null>");
				}
				return this;
			}
		});
	}

	public static void update() {// TODO update
		if (isAnyCellSelected(cpu_table)) {
			cpu_combobox.setEnabled(true);
			ph_set.setEnabled(true);
			manage_atts.setEnabled(true);
			remove_entry.setEnabled(true);
			int selected = cpu_table.getSelectedRow();
			ph_open.setEnabled((new File(cpu_database[selected][1].toString()).exists()) ? true : false);
			cpu_combobox.setSelectedIndex(Integer.parseInt(cpu_database[selected][3].toString()));
		} else {
			ph_open.setEnabled(false);
			manage_atts.setEnabled(false);
			cpu_combobox.setEnabled(false);
			ph_set.setEnabled(false);
			remove_entry.setEnabled(false);
		}
	}

	public String createEntry() {
		JPanel p = new JPanel(new FlowLayout());
		JLabel text = new JLabel("Oznaèení: ");
		text.setFont(getFont().deriveFont(15.0f));
		p.add(text);
		JTextField name = new JTextField(20);
		name.setFont(getFont().deriveFont(15.0f));
		p.add(name);

		if (JOptionPane.showConfirmDialog(null, p, "Pøidat prvek", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			return name.getText();
		} else return null;
	}

	public String createEntryLink() {
		JPanel p = new JPanel(new FlowLayout());
		JLabel text = new JLabel("Cesta: ");
		text.setFont(getFont().deriveFont(15.0f));
		p.add(text);
		JTextField name = new JTextField(20);
		name.setFont(getFont().deriveFont(15.0f));
		name.setText(cpu_database[cpu_table.getSelectedRow()][1].toString());
		p.add(name);

		if (JOptionPane.showConfirmDialog(null, p, "Pøidat fotografii", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			return name.getText();
		} else return null;
	}

	public String modifyAttributes() {
		JPanel p = new JPanel(new FlowLayout());
		JLabel text = new JLabel("Atributy: ");
		text.setFont(getFont().deriveFont(15.0f));
		p.add(text);
		JTextField name = new JTextField(30);
		name.setFont(getFont().deriveFont(15.0f));
		name.setText(cpu_database[cpu_table.getSelectedRow()][2].toString());
		p.add(name);

		if (JOptionPane.showConfirmDialog(null, p, "Modifikovat atributy", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			return name.getText();
		} else return null;
	}

	public void showEntryImage() {
		String path = cpu_database[cpu_table.getSelectedRow()][1].toString();
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
			if (new File(file).isFile()) {
				String datas = new String(Files.readAllBytes(Paths.get(file)));
				if (datas.contains("||")) datas = datas.replace("||", "|");
				if (datas.charAt(0) == '|') {
					datas = datas.substring(1);
				}
				String[] block_data = datas.split("\\|");
				Object[][] raw_data = new Object[block_data.length][4];
				for (int i = 0; i < block_data.length; i++) {
					String[] cut_data = block_data[i].split("\\^");
					for (int y = 0; y < 4; y++) {
						if (y == 1 && new File(cut_data[y]).exists()) raw_data[i][y] = new ImageIcon(cut_data[y]);
						else raw_data[i][y] = cut_data[y];
					}
				}
				return raw_data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String[][] {};
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