package jhd.ImageJAddins;
//Copyright (c) John H Dunsmuir 2022
//MIT-License


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.EventListener;
import ij.IJ;
import ij.gui.GenericDialog;
import ij.gui.TrimmedButton;


/**GenericDialogAddIn adds instance methods for retrieving GenericDialog components<br>
  * @author LazzyIzzi
 *
 */
public class GenericDialogAddin
{

	/**A class containing the objects associated with addFileField and addDirectoryField
	 * @author LazzyIzzi
	 */
	public class PathField
	{
		private Label label;
		private Panel panel;
		private TextField textField;
		private TrimmedButton trimmedButton;

		private PathField(Label label, Panel panel,TextField textField,TrimmedButton trimmedButton)
		{
			//this.buttonLabel = trimmedButton.getLabel();;
			this.panel=panel;
			this.textField = textField;
			this.trimmedButton = trimmedButton;
			this.label = label;
		}
		/**
		 * @return the Label component
		 */
		public Label getLabel() {
			return label;
		}
		/**
		 * @return the Panel component
		 */
		public Panel getPanel() {
			return panel;
		}
		/**
		 * @return the TextField component
		 */
		public TextField getTextField() {
			return textField;
		}
		/**
		 * @return the TrimmedButton component
		 */
		public TrimmedButton getTrimmedButton() {
			return trimmedButton;
		}
		/**
		 * @return the Label (ActionCommand) associated with this button
		 */
		public String getButtonLabel() {
			return trimmedButton.getLabel();
		}
		//		No Setters
		//		public void setButtonLabel(String buttonLabel) {
		//			trimmedButton.setLabel(buttonLabel);
		//		}
		//		public void setPanel(Panel panel) {
		//			this.panel = panel;
		//		}
		//		public void setTextField(TextField textField) {
		//			this.textField = textField;
		//		}
		//		public void setTrimmedButton(TrimmedButton trimmedButton) {
		//			this.trimmedButton = trimmedButton;
		//		}
	}


	/**A class containing the Label Object created by GenericDialog.addMessage
	 * @author LazzyIzzi
	 */
	public class MessageField
	{
		private Label label;

		private MessageField(Label label)
		{
			this.label = label;
		}

		/**
		 * @return the Message's Label Object
		 */
		public Label getLabel()
		{
			return label;
		}
		public void setLabel(String labelText)
		{
			label.setText(labelText);
		}
	}

	/**A class containing the Object(s) created by GenericDialog.addButton
	 * @author LazzyIzzi
	 */
	public class ButtonField
	{
		private Button button;
		private ButtonField(Button button)
		{
			this.button = button;
		}
		/**
		 * @return the Button Object
		 */
		public Button getButton()
		{
			return button;
		}
	}

	/**A class containing the Object(s)created by GenericDialog.addCheckbox
	 * @author LazzyIzzi
	 */
	public class CheckboxField
	{
		private Checkbox checkbox;
		private CheckboxField(Checkbox checkbox) {
			super();
			this.checkbox = checkbox;
		}
		/**
		 * @return the CheckBox Object
		 */
		public Checkbox getCheckBox()
		{
			return checkbox;
		}
	}

	/**A class containing the Object(s) created by  GenericDialog.addChoice
	 * @author LazzyIzzi
	 */
	public class ChoiceField
	{
		private Label label;
		private Choice choice;
		private ChoiceField(Label label, Choice choice)
		{
			this.label=label;
			this.choice = choice;
		}
		/**
		 * @param items create/replace choice's items
		 */
		public void setChoices(String[] items)
		{
			choice.removeAll();
			for(String item :items)
			{
				choice.addItem(item);
			}
		}
		/**
		 * @return the current choices
		 */
		public String[] getChoices()
		{
			int cnt = choice.getItemCount();
			String[] items = new String[cnt];
			for(int i=0;i<cnt;i++)
			{
				items[i]= choice.getItem(i);

			}
			return items;
		}
		/**
		 * @return the Choice's Choice Object
		 */
		public Choice getChoice() {return choice;}
		/**
		 * @return the Choice's Label Object
		 */
		public Label getLabel(){return label;}		
	}

	/**A class containing the Object(s) created by GenericDialog.addNumericField
	 * @author LazzyIzzi
	 *
	 */
	public class NumericField
	{
		private Label label;
		private TextField textField;
		private NumericField(Label label, TextField textField)
		{
			this.label=label;
			this.textField = textField;
		}
		/**
		 * @return the NumericField's Label Object
		 */
		public Label getLabel(){return label;}
		/**
		 * @return the number in the NumericField or NaN
		 */
		public double getNumber()
		{
			String text = textField.getText();
			if(isNumeric(text)) return Double.parseDouble(text);
			else return Double.NaN;
		}
		/**
		 * @param number set the Numeric field to this value
		 */
		public void setNumber(double number)
		{
			textField.setText(String.valueOf(number));
		}
		/**
		 * @return the NumericField's TextField Object
		 */
		public TextField getNumericField()
		{
			return textField;
		}

		@SuppressWarnings("unused")
		private  boolean isNumeric(String strNum) {
			if (strNum == null) {
				return false;
			}
			try {
				double d = Double.parseDouble(strNum);
			} catch (NumberFormatException nfe) {
				return false;
			}
			return true;
		}		
	}

	/**A class containing the Object(s) created by GenericDialog.addRadioButtonGroup<br>
	 * and convenient access methods
	 * @author LazzyIzzi
	 *
	 */
	public class RadioButtonField 
	{
		private Label label;
		private CheckboxGroup checkboxGroup;
		private Panel panel;

		private RadioButtonField(Label label, Panel panel, CheckboxGroup checkboxGroup)
		{
			this.label=label;
			this.panel = panel;
			this.checkboxGroup = checkboxGroup;
		}
		/**Adds a single RadioButton to the existing group
		 * @param item the item to be added
		 */
		public void addRadioButton(String item) {
			panel.add(new Checkbox(item,checkboxGroup,false));			
		}
		/**Adds RadioButtons to the existing group
		 * @param items the items to be added
		 * @param rows the rows in the new radioButton display, 0 to auto fit
		 * @param columns the columns in the new radioButton display
		 */
		public void addRadioButtons(String[] items,int rows, int columns) {
			panel.setLayout(new GridLayout(rows, columns, 0, 0));
			Checkbox ckbx;
			for(int i=0;i<items.length;i++)
			{
				ckbx= new Checkbox(items[i],checkboxGroup,false);
				panel.add(ckbx);
			}
		}
		/**
		 * @return the checkboxGroup for the radioButtons
		 */
		public CheckboxGroup getCheckboxGroup()
		{
			return checkboxGroup;
		}
		/**
		 * @return gets the RadioButtonGroup's Label Object
		 */
		public Label getLabel() {return label;}
		/**
		 * @return the radio button Checkboxes
		 */
		public Checkbox[] getRadioButtons()
		{
			Component[] cmpnts = panel.getComponents();
			int count = panel.getComponentCount();
			Checkbox[] ckboxes = new Checkbox[count];
			int i=0;
			for(Component cmpnt : cmpnts)
			{
				ckboxes[i] = (Checkbox)cmpnt;
			}
			return ckboxes;
		}
		/**
		 * @return the text label of the selected radio button
		 */
		public String getSelectedItem() {
			return checkboxGroup.getSelectedCheckbox().getLabel();
		}
		/**
		 * @return the Panel containing the RadioButtonGroup
		 */
		public Panel getPanel() {
			return panel;
		}
		/**Sets the Checkbox's Label
		 * @param labelText the new label text
		 * @deprecated use getLabel().setText();
		 */
		public void setLabel(String labelText){
			label.setText(labelText);
		}
		/**Replaces the RadioButtons with new items 
		 * @param items the replacement radio button label strings
		 * @param rows the rows in the new radioButton display, 0 to auto fit
		 * @param columns the columns in the new radioButton display
		 */
		public void setRadioButtons(String[] items,int rows, int columns) {
			//Get the listeners from an existing checkbox			
			ItemListener[] itemListeners = checkboxGroup.getSelectedCheckbox().getItemListeners();
			panel.removeAll();
			panel.setLayout(new GridLayout(rows, columns, 0, 0));
			int count = items.length;
			for(int i=0;i<count;i++)
			{
				Checkbox ckbx= new Checkbox(items[i],checkboxGroup,i==0);
				//add the listener to the new checkboxes
				ckbx.addItemListener(itemListeners[0]);
				panel.add(ckbx);
			}
		}
	}

	/**A class containing the Object(s) created byGenericDialog.addSlider
	 * @author LazzyIzzi
	 *
	 */
	public class SliderField
	{
		private Label label;
		//private Panel panel;
		private Scrollbar scrollbar;
		private TextField textField;
		private SliderField(Label label,  Scrollbar scrollbar, TextField textField)
		{
			this.label = label;
			this.scrollbar = scrollbar;
			this.textField = textField;			
		}
		/**
		 * @return the Slider's Label Object
		 */
		public Label getLabel()
		{
			return label;
		}
		/**
		 * @return the Slider's Scrollbar Object
		 */
		public Scrollbar getScrollbar()
		{
			return scrollbar;
		}
		/**
		 * @return the Slider's TextField Object
		 */
		public TextField getTextField()
		{
			return textField;
		}		
	}

	/**A class containing the Object(s) created by GenericDialog.addStringField
	 * @author LazzyIzzi
	 *
	 */
	public class StringField
	{
		private Label label;
		private TextField textField;
		/**Constructor for StringField
		 * @param label the StringFields Label Object
		 * @param textField
		 */
		private StringField(Label label, TextField textField)
		{
			this.label=label;
			this.textField = textField;
		}
		/**
		 * @return the TextArea's Label Object
		 */
		public Label getLabel(){return label;}
		/**
		 * @return the TextField (StringField) Object
		 */
		public TextField getTextField() {return textField;}		
	}

	/**A class containing the Object(s) created by GenericDialog.addTextAreas
	 * @author LazzyIzzi
	 *
	 */
	public class TextAreasField
	{

		private TextArea textArea1,textArea2;

		/**Constructor for TextAreasField
		 * @param textArea1 the primary textArea Object
		 * @param textArea2 the secondary textArea Object
		 */
		public TextAreasField( TextArea textArea1, TextArea textArea2)
		{
			this.textArea1 = textArea1;
			this.textArea2 = textArea2;
		}

		/**
		 * @return the textArea1 Object
		 */
		public TextArea getTextArea1()
		{
			return textArea1;
		}
		/**
		 * @return the textArea2 Object
		 */
		public TextArea getTextArea2()
		{
			return textArea2;
		}

	}

	//**********************************************************************	
	//**********************************************************************
	//**********************************************************************

	/**Call immediately after addButton if you need to access the Button's methods.<br>
	 * @param gd The parent GenericDialog.
	 * @param buttonName assign this name to the button Label (ActionCommand)<br>
	 * Pass null as argument to retain the Button's component default name.
	 * @return a reference to the objects in the prior addButton
	 */	
	public  ButtonField getButtonField(GenericDialog gd,String buttonName)
	{
		int cnt = gd.getComponentCount();
		int buttonIndex = cnt-1;
		try
		{
			Button button  = (Button)gd.getComponent(buttonIndex);
			if(buttonName!=null)button.setName(buttonName);

			ButtonField bf = new ButtonField(button);			
			return bf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(buttonName + e.getMessage());
			return null;
		}				
	}

	/**Call immediately after addCheckbox if you need to access the Checkbox's methods.<br>
	 * @param gd The parent GenericDialog.
	 * @param checkboxName assign this name to the Checkbox<br>
	 * Pass null as argument to retain the Checkbox's component default name.
	 * @return a reference to the prior addCheckbox
	 */
	public  CheckboxField getCheckboxField(GenericDialog gd,String checkboxName)
	{
		int cnt = gd.getComponentCount();
		int index = cnt-1;

		try
		{
			Checkbox ckbx  = (Checkbox)gd.getComponent(index);
			if(checkboxName!=null)ckbx.setName(checkboxName);
			CheckboxField cbf = new CheckboxField(ckbx);

			return cbf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(checkboxName + e.getMessage());
			return null;
		}
	}

	/**Call immediately after addChoice if you need to access the Choice's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the choices Label<br>
	 * @param choiceName assign this name to the choice.<br>
	 * @return a reference to the objects in the prior addChoice
	 */
	public  ChoiceField getChoiceField(GenericDialog gd,String labelName, String choiceName)
	{
		int cnt = gd.getComponentCount();
		int choiceIndex = cnt-1;
		int labelIndex = cnt-2;
		try
		{
			Label lbl  = (Label)gd.getComponent(labelIndex);
			if(labelName!=null)lbl.setName(labelName);
			Choice c = (Choice)gd.getComponent(choiceIndex);
			if(choiceName!=null)c.setName(choiceName);
			ChoiceField cf = new ChoiceField(lbl,c);

			return cf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(labelName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after the extended form of addNumericField if you need to access the StringField's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param panelName assign this name to the panel contailing the NumericField<br>
	 * @param labelName assign this name to the NumericField Label<br>
	 * @param numericFieldName assign this name to the NumericField.<br>
	 * @return a reference to the objects in the prior NumericField
	 */
	public  NumericField getNumericField2(GenericDialog gd,String panelName,String labelName, String numericFieldName)
	{
		int cnt = gd.getComponentCount();
		int panelIndex = cnt-1;
		try
		{
			Panel panel = (Panel)gd.getComponent(panelIndex);
			TextField tf = (TextField)panel.getComponent(0);
			if(numericFieldName!=null)tf.setName(numericFieldName);
			Label lbl  = (Label)panel.getComponent(1);
			if(labelName!=null)lbl.setName(labelName);

			NumericField sf = new NumericField(lbl,tf);

			return sf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(labelName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after addNumericField if you need to access the StringField's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the NumericField Label<br>
	 * @param numericFieldName assign this name to the NumericField.<br>
	 * @return a reference to the objects in the prior NumericField
	 */
	public  NumericField getNumericField(GenericDialog gd,String labelName, String numericFieldName)
	{
		int cnt = gd.getComponentCount();
		int textFieldIndex = cnt-1;
		int labelIndex = cnt-2;
		try
		{
			Label lbl  = (Label)gd.getComponent(labelIndex);
			if(labelName!=null)lbl.setName(labelName);
			TextField tf = (TextField)gd.getComponent(textFieldIndex);
			if(numericFieldName!=null)tf.setName(numericFieldName);
			NumericField sf = new NumericField(lbl,tf);

			return sf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(labelName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after addRadioButtonGroup if you need to access the RadioButtonGroup's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the RadioButtonGroup's Label
	 * @param groupName assign this name to the RadioButtonGroup's enclosing Panel
	 * @return a RadioButtonField
	 */
	public  RadioButtonField getRadioButtonField(GenericDialog gd,String labelName, String groupName)
	{
		int cnt = gd.getComponentCount();
		int panelIndex = cnt-1;
		int labelIndex = cnt-2;
		Panel panel;
		Label label;
		RadioButtonField rbf=null;
		try
		{
			panel  = (Panel)gd.getComponent(panelIndex);
			if(groupName!=null) panel.setName(groupName);
			label = (Label)gd.getComponent(labelIndex);
			if(labelName!=null) label.setName(labelName);
			Vector<?>radioButtonGroups = gd.getRadioButtonGroups();
			CheckboxGroup cbg = (CheckboxGroup)radioButtonGroups.lastElement();
			rbf = new RadioButtonField(label,panel,cbg);	
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(groupName + e.getMessage());;
		}		
		return rbf;		
	}

	/**Call immediately after addSlider if you need to access the Slider's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the Slider's Label<br>
	 * @param scrollbarName assign this name to the Slider.<br>
	 * @param textFieldName assign this name to the Slider.<br>
	 * @return a reference to the objects in the prior addSlider
	 */
	public  SliderField getSliderField(GenericDialog gd,String labelName, String scrollbarName, String textFieldName)
	{
		int cnt = gd.getComponentCount();
		int panelIndex = cnt-1;
		int labelIndex = cnt-2;
		try
		{
			Label lbl  = (Label)gd.getComponent(labelIndex);
			if(labelName!=null)lbl.setName(labelName);
			Panel panel = (Panel)gd.getComponent(panelIndex);
			Scrollbar scrlBar = (Scrollbar)panel.getComponent(0);
			if(scrollbarName!=null)scrlBar.setName(scrollbarName);
			TextField tf = (TextField)panel.getComponent(1);
			if(textFieldName!=null)tf.setName(textFieldName);
			SliderField sf = new SliderField(lbl,scrlBar,tf);			
			return sf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(labelName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after addStringField if you need to access the StringField's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the StringField Label<br>
	 * @param stringFieldName assign this name to the StringField.<br>
	 * @return a reference to the objects in the prior StringField
	 */
	public  StringField getStringField(GenericDialog gd,String labelName, String stringFieldName)
	{
		int cnt = gd.getComponentCount();
		int textFieldIndex = cnt-1;
		int labelIndex = cnt-2;
		try
		{
			Label lbl  = (Label)gd.getComponent(labelIndex);
			if(labelName!=null)lbl.setName(labelName);
			TextField tf = (TextField)gd.getComponent(textFieldIndex);
			if(stringFieldName!=null)tf.setName(stringFieldName);
			StringField sf = new StringField(lbl,tf);

			return sf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(labelName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after addTextAreas if you need to access the TextAreas's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param textArea1Name assign this name to TextArea 1.<br>
	 * @param textArea2Name assign this name to  TextArea 2.<br>
	 * @return a reference to the objects in the prior addTextAreas
	 */
	public  TextAreasField getTextAreasField(GenericDialog gd,String textArea1Name, String textArea2Name)
	{
		int cnt = gd.getComponentCount();
		int panelIndex = cnt-1;
		Panel panel;
		try
		{
			panel  = (Panel)gd.getComponent(panelIndex);
			TextArea textArea1 = (TextArea)panel.getComponent(0);
			if(textArea1Name!=null) textArea1.setName(textArea1Name);
			TextArea textArea2 = (TextArea)panel.getComponent(1);
			if(textArea2Name!=null) textArea2.setName(textArea2Name);

			//ImageJ bug workaround
			TextListener[] ta1Listeners  = textArea1.getTextListeners();
			textArea2.addTextListener(ta1Listeners[0]);

			TextAreasField taf = new TextAreasField(textArea1,textArea2);
			return taf;
		}
		catch(Exception e)
		{
			IJ.showMessage(textArea1Name + e.getMessage());
			return null;
		}

	}

	/**Call immediately after addMessage if you need to access the Message's methods.<br>
	 * Pass null as argument to retain message's Label component default name.
	 * @param gd The parent GenericDialog.
	 * @param messageName assign this name to the Message Label<br>
	 * @return a reference to the objects in the prior Message
	 */
	public  MessageField getMessageField(GenericDialog gd,String messageName)
	{
		int cnt = gd.getComponentCount();
		int messageIndex = cnt-1;
		try
		{
			Label lbl  = (Label)gd.getComponent(messageIndex);
			if(messageName!=null)lbl.setName(messageName);
			MessageField mf = new MessageField(lbl);

			return mf;
		}
		catch(ClassCastException e)
		{
			IJ.showMessage(messageName + e.getMessage());
			return null;
		}		
	}

	/**Call immediately after addFileField if you need to access the FileFields's methods.<br>
	 * Pass null as arguments to retain respective component default names.
	 * @param gd The parent GenericDialog.
	 * @param labelName assign this name to the FileField Label
	 * @param panelName assign this name to the FileField Panel
	 * @param textFieldName assign this name to the FileField TextField. In most cases this is the component
	 * of interest and should be named to access by name.
	 * @param buttonName assign this name to the FileField Button
	 * @return a reference to the Objects in the prior addFileField
	 */
	public  PathField getPathField(GenericDialog gd,String labelName, String panelName,String textFieldName,String buttonName)
	{
		PathField fpf = null;
		int cnt = gd.getComponentCount();
		int labelIndex = cnt-2;
		int panelIndex = cnt-1;

		try {
			Label label = (Label) gd.getComponent(labelIndex);
			if(labelName!=null) label.setName(labelName);

			Panel panel = (Panel)gd.getComponent(panelIndex);
			if(panelName!=null) panel.setName(panelName);

			TextField textField = (TextField)panel.getComponent(0);
			if(textFieldName!=null) textField.setName(textFieldName);

			TrimmedButton button = (TrimmedButton)panel.getComponent(1);
			if(buttonName!=null) button.setName(buttonName);		

			fpf= new PathField(label,panel,textField,button);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fpf;
	}


	/**A class that attaches a "spinner" widget to a GenericDialog.addNumericField's TextField
	 * @author LazzyIzzi
	 *
	 */
	public class SpinnerPanel implements  ActionListener,TextListener
	{
		//private Label label;
		private TextField valTF;
		private TextField incTF;

		/**Default Constructor
		 * 
		 */
		public SpinnerPanel() {}

		//		/**
		//		 * @return the SpinnerField Label Object
		//		 */
		//		public String getLabel()
		//		{
		//			return label.getText();
		//		}
		//		/**
		//		 * @return the number in the SpinnerField or NaN
		//		 */
		public double getValue()
		{
			String text = valTF.getText();
			if(isNumeric(text)) return Double.parseDouble(text);
			else return Double.NaN;
		}
		/**
		 * @param val set the SpinnerField value to this value
		 */
		public void setValue(Double val)
		{
			valTF.setText(val.toString());
		}					
		/**
		 * @return the number in the SpinnerField or NaN
		 */
		public double getIncrement()
		{
			String text = incTF.getText();
			if(isNumeric(text)) return Double.parseDouble(text);
			else return Double.NaN;
		}
		/**
		 * @param number set the SpinnerField value to this value
		 */
		public void setIncrement(double number)
		{
			incTF.setText(String.valueOf(number));
		}

		/**Adds a spinner panel to a GenericDialog.addNumericField component
		 * <br>e.g.
		 * <br>gd.addNumericField("DegC", 50);		
		 * <br>gd.addToSameRow(); //Optional
		 * <br>gd.addPanel(new SpinnerPanel().addSpinner((TextField)gd.getNumericFields().get(index),1.0));
		 * <br>where index is the zero-based current count of addNumericField items in the dialog.
		 * <br>Note: A GenericDialog NumericField is actually a Java TextField
		 * @param numericField A NumericField added by GenericDialog.addNumericField
		 * @param numericFieldName assign this name to the numericField, if "null", the numericField will be named "spinVal"
		 * @param increment The increment for the spinner parameter
		 * @return a panel containing a label, a value, a "+" button, a "-" button and an increment
		 */
		public Panel addSpinner(TextField numericField, String numericFieldName, Double increment)
		{
			Panel spinPanel = new Panel();

			valTF = (TextField)numericField;
			if(numericFieldName!=null) valTF.setName(numericFieldName);
			else valTF.setName("spinVal");
			valTF.addTextListener(this);
			spinPanel.add(valTF);

			Button upBtn = new Button(" + ");
			upBtn.setName("upBtn");
			spinPanel.add(upBtn);
			upBtn.addActionListener(this);

			Button downBtn = new Button(" - ");
			downBtn.setName("downBtn");
			spinPanel.add(downBtn);
			downBtn.addActionListener(this);

			incTF = new TextField(increment.toString());
			incTF.setName("increment");
			incTF.addTextListener(this);
			spinPanel.add(incTF);

			spinPanel.add(new Label("Inc"));
			return spinPanel;
		}

		//************************************************************************

		@Override
		public void textValueChanged(TextEvent e)
		{
			Object src = e.getSource();
			if(src instanceof TextField)
			{
				//Both spinner TextFields must be numeric
				TextField tf = (TextField)src;
				if(isNumeric(tf.getText()))
				{
					tf.setBackground(Color.white);
				}
				else
				{
					tf.setBackground(Color.red);
				}
			}			
		}

		//************************************************************************

		public void actionPerformed(ActionEvent e)
		{
			//IJ.log("spinner actionPerformed");
			double inc = getIncrement();
			double val = getValue();

			String cmd = e.getActionCommand();
			switch(cmd)
			{
			case " + ":
				setValue(val + inc);
				break;
			case " - ":
				setValue(val - inc);
				break;
			}
		}
	}

	//*******************************************************************************

	/**
	 * @param str Determines if a String is a numeric value
	 * @return true if str is a number, false if not.
	 */
	public static boolean isNumeric(String str)
	{ 
		try
		{  
			Double.parseDouble(str);  
			return true;
		}
		catch(NumberFormatException e)
		{  
			return false;  
		}  
	}
}
