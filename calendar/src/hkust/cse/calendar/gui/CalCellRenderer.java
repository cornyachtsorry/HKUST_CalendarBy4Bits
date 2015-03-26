package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;

	public CalCellRenderer(CalGrid.dayInfo info) {
		if (info == CalGrid.dayInfo.Today) {
			setForeground(Color.red);
		} else if (info == CalGrid.dayInfo.HasEvent){
			setForeground(Color.green);
		} else
			setForeground(Color.black);
		setBackground(Color.white);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}

	public int row() {
		return r;
	}

	public int col() {
		return c;
	}

}
