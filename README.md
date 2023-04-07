# ImageJAddins
A class for making it easier to write imageJ plugins.  It currently contains only one class, GenericDialogAddin.
<p>The GenericDialogAddin class contains methods that return a Field containing references to the individual components created by an immediately preceding call to a GenericDialog addXxxx method. This allows full access to each component's methods independent of its creation order in the dialog. If this sounds complicated, using the Addin is actually quite easy. The JavaDocs for GenericDialogAddin are <a href="https://lazzyizzi.github.io/GenericDialogAddin/jhd/AddIns/package-summary.html" target="_blank">here</a>.</p>
<p>The example is adapted from the X-ray Calculator plugin.</p>
<ul>
	<li>The XxxxField declarations and the GenericDialogAddin instance "gda" have plugin wide scope.</li>
	<li>Immediately following the gd.addXxxx method, the Xxxxfield is returned by a call to the gda.getXxxxField method.</li>
	<li>The gda.getXxxxField arguments are the parent gd followed by optional names for each component in the Field. Naming a component allows an event handler to dispatch an event by the source's name rather than by its index (no more index tracking!). We also have access to all component methods.</li>
</ul>
<h4>Some example syntax for setting up the GenericDialogAddin Fields</h4>
<pre><strong>
//required GenericDialogAddin imports
import jhd.AddIns.GenericDialogAddin;
import jhd.AddIns.GenericDialogAddin.*;

//The Fields and GenericDialogAddin have global scope
StringField matlNameSF,filterSF,formulaSF;
ChoiceField matlNameCF;
NumericField gmPerCCNF,kevNF;
CheckboxField useTabDensCF;
ButtonField getKevBF;
GenericDialog gd;
GenericDialogAddin gda;
boolean useTabDensity;

	
private void DoDialog()
{
	gda = new GenericDialogAddin();
	gd =  GUI.newNonBlockingDialog(myDialogTitle);
	gd.addDialogListener(this);

  gd.addCheckbox("Import_tabulated_densities", true);
  useTabDensCF = gda.getCheckboxField(gd, "useTabDensity");

  gd.setInsets(5, 0, 0);
	gd.addMessage("Formula Format\n"
			+ "Atom1:Count1:Atom2:Count2 etc.", myFont);
	gd.addStringField("Material_List_Filter",  "");
	filterSF = gda.getStringField(gd, null, "filter");
  
  gd.addChoice("Material_List", matlName, matlName[0]);
  //We don't need to name the gd.addChoice label component, use null
  //We are going to use the Choice component name
  matlNameCF = gda.getChoiceField(gd, null, "materialChoice");

  //We will access these items explicitly so we really
  //don't need to name the components, name them anyway in case we do
  gd.addStringField("Material_Name",  matlFormula[0],18);
  matlNameSF = gda.getStringField(gd, null, "matlName");

  gd.addStringField("Formula",  matlFormula[0],18);
  formulaSF = gda.getStringField(gd, null, "formula");

  //Other stuff ....
  .
  .
  //....Other Stuff		
  gd.showDialog();

}
</strong></pre>

<h4>Some example syntax for using the GenericDialogAddin Fields</h4>

<pre><strong>
public boolean dialogItemChanged(GenericDialog gd, AWTEvent e)
{
boolean dialogOK = getSelections();

if(e!=null)
{
	Object src = e.getSource();			
	if(src instanceof Choice)
	{
		Choice choice = (Choice)src;
		switch(choice.getName())
		{
		//here is where we need the choice component's name
		//to separate it from the other Choice components
		case "materialChoice":
			//Access the relevant components for this event
			int index =  matlNameCF.getChoice().getSelectedIndex();
			matlNameSF.getTextField().setText(filteredMatlName[index]);
			formulaSF.getTextField().setText(filteredMatlFormula[index]);
			if(useTabDensity)
			{
				gmPerCCNF.setNumber(filteredMatlGmPerCC[index]);
			}
			dialogOK=true;
			break;
		}
	}
	else if(src instanceof TextField)
//Other stuff ....
.
.
//....Other Stuff		
return dialogOK;
				
</strong></pre>

<h4>Some example syntax for using the GenericDialogAddin Fields to screen user inputs</h4>

<pre><strong>
private boolean getSelections()
{
	boolean dialogOK=true;
	//ds is a class with fields for our variables
	ds.formulaName = matlNameSF.getTextField().getText();
	ds.formula = formulaSF.getTextField().getText();
	ds.gmPerCC = gmPerCCNF.getNumber();
	if(Double.isNaN(ds.gmPerCC))
	{
		dialogOK=false;
	}
	ds.meV = kevNF.getNumber();
	if(Double.isNaN(ds.meV))
	{
		getKevBF.getButton().setEnabled(false);
		dialogOK=false;
	}
	else
	{
		getKevBF.getButton().setEnabled(true);
		ds.meV = ds.meV/1000;
	}
	useTabDensity = useTabDensCF.getCheckBox().getState();
	return dialogOK;
}
	</strong></pre>
<p></p>

<p><small>1. Why call it an Addin? It needs to work with both GenericDialog and GUI.nonBlockingDialog. Java does not allow extending both in one class. Creating separate but identical classes is impractical. An interface worked OK but it's really not a proper use of an interface. Calling it a library seemed inappropriate since it is completely dependent on GenericDialog components. In the best Excel tradition I called it an Addin.  Ideally, it could someday become part of the GenericDialog class where the calls to GenericDialog.add... would return the added item's components.</small></p>

