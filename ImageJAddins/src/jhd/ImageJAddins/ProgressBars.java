package jhd.ImageJAddins;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JProgressBar;


public class ProgressBars {

	/**Process Monitor
	 * @author LazzyIzzi*/
	public class ProgressBarParam
	{
		/**The progress bar title*/
		String progBarTitle;
		/**The progress bar width*/
		int progressbarWidth;
		/**The progress bar height*/
		int progressbarHeight;
		/**The value at the left end of the progress bar*/
		int minBarValue;
		/**The value at the right end of the progress bar*/
		int maxBarValue;
	}

	private String windowTitle = "Default Window Title";
	private Color windowColor = new Color(240,230,190);//slightly darker than buff
	private JFrame frame;
	
	private ArrayList<JProgressBar> prgBars = new ArrayList<JProgressBar>();
	private ArrayList<String> prgBarNames = new ArrayList<String>();
	
	/**Adds a progress bar to the list, call show() to display
	 * @param progBarTitle
	 * @param progressbarWidth
	 * @param progressbarHeight
	 * @param minBarValue
	 * @param maxBarValue
	 */
	public void add(String progBarTitle,int progressbarWidth,int progressbarHeight,int minBarValue,int maxBarValue)
	{
		JProgressBar p = new JProgressBar(minBarValue,maxBarValue);		
		p.setPreferredSize(new Dimension(progressbarWidth,progressbarHeight));
		p.setValue(minBarValue);
		p.setStringPainted(true);
		p.setName(progBarTitle);		
		prgBars.add(p);
		prgBarNames.add(progBarTitle);
	}
	/**Creates a window containing multiple progress bars
	 * @param title The progress monitor window title
	 * @param procgressBarParams An array of progress bar specifications
	 */
	public ProgressBars(String windowTitle)
	{
		this.windowTitle = windowTitle;
	}
	/**Sets the progress bar window color
	 * @param color
	 */
	public void setBackground(Color color)
	{
		this.windowColor = color;
	}
	
	/**Creates the progress bars and displays them in a Window*/
	public void show()
	{		
		//https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html			
		frame = new JFrame(windowTitle);
		Container pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		pane.setBackground(windowColor);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill =  GridBagConstraints.HORIZONTAL;
		int j=0;
		for(JProgressBar prgBar : prgBars)
		{
			gbc.gridx=0;
			gbc.gridy=j;
			gbc.gridwidth =1;
			pane.add(new Label(prgBar.getName()),gbc);
			
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridy=j+1;
			pane.add(prgBar,gbc);
			j+=2;			
		}
		//display at screen center
		frame.setLocationRelativeTo(null);
		//disables the window close box
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setSize(frame.getWidth()+40,frame.getHeight()+20);
		frame.setVisible(true);	
	}
	
	/**Closes the process monitor window*/
	public void close()
	{
		frame.dispose();
	}
	/**Sets and updates the progress bar<br>
	 * Does nothing if the progress bars window is closed.
	 * @param the zero-based index of the process bar
	 * @param value the progress bar value between start and end 
	 */
	public void setValue(String name,int value)
	{
		if(prgBars.isEmpty()==false)
		{
			int index = prgBarNames.indexOf(name);
			prgBars.get(index).setValue(value);
		}
	}
}
