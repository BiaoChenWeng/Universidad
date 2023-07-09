package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {

	String _title;
	TableModel _tableModel;

	/**
	 * Constructor of the InfoTable class
	 * 
	 * @param title      The title of the table
	 * @param tableModel The table's model
	 */
	public InfoTable(String title, TableModel tableModel) {
		this._title = title;
		this._tableModel = tableModel;
		initGUI();
	}

	/**
	 * Initializes the table
	 */
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), _title,
				TitledBorder.TOP, TitledBorder.LEFT));
		JTable table = new JTable(_tableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}
}
