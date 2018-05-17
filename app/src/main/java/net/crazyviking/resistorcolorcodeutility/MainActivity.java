package net.crazyviking.resistorcolorcodeutility;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ResistorEnum band1, band2, band3, band4, toleranceBand;
    ImageView band1_3Band, band2_3Band, band3_3Band, tolerance_3Band;
    Spinner band1Spinner_3b, band2Spinner_3b, band3Spinner_3b, toleranceSpinner_3b, resMultiplierSpinner, tolEntrySpinner;
    TextView resistance, band1Label, band2Label, band3Label, toleranceLabel;
    private EditText resEntry;


    private static final String[] resColors= {"BLACK", "BROWN", "RED", "ORANGE", "YELLOW",
            "GREEN", "BLUE", "VIOLET", "GRAY", "WHITE"};

    private static final String[] tolColors= {"GOLD", "SILVER"};

    private static final String[] resMultipliers = {"Ohms", "K Ohms", "M Ohms"};

    private static final String[] tolEntries = { "5%", "10%"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.resistance = (TextView) findViewById(R.id.resistance3Band);
        this.band1Label = (TextView) findViewById(R.id.band1Label);
        this.band2Label = (TextView) findViewById(R.id.band2Label);
        this.band3Label = (TextView) findViewById(R.id.band3Label);
        this.toleranceLabel = (TextView) findViewById(R.id.toleranceLabel);
        this.resEntry = (EditText) findViewById(R.id.resEntry);
        this.setupEnums();
        this.setupThreeBandImages();
        this.setupThreeBandSpinners();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();

        // FIRST BAND WAS SELECTED
        if (id == R.id.band1Spinner_3band) {

            band1 = this.setBandColor(band1_3Band, band1Label, resColors[i]);

        }
        // SECOND BAND WAS SELECTED
        else if ( id == R.id.band2Spinner_3band) {

            band2= this.setBandColor(band2_3Band, band2Label, resColors[i]);

        }
        // MULTIPLIER WAS SELECTED
        else if (id == R.id.band3Spinner_3band) {

            band3 = this.setBandColor(band3_3Band, band3Label, resColors[i]);

        }
        // TOLERANCE WAS SELECTED
        else if (id == R.id.toleranceSpinner_3band) {

            toleranceBand = this.setBandColor(tolerance_3Band, toleranceLabel, tolColors[i]);

        }

        updateResistance();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Updates the resistance TextView to display the full reading of the
     * bands selected.
     *
     * First band = # x 10
     * Second band = # x 1
     * Multiplier = 10^x (where x is the band value)
     * Tolerance = 5 or 10% based on color.
     *
     */

    private void updateResistance() {

        double band1, band2, multiplier;

        band1 = (double)this.band1.getValue();
        band2 = (double)this.band2.getValue();
        multiplier = Math.pow(10, (double)this.band3.getValue());

        Double value;

        String engMultiplier = "";

        value = ((band1*10 + band2) * multiplier);

        if (multiplier >= 100 && multiplier < 100000) {
            engMultiplier = " k";
            value = value / 1000;
        } else if (multiplier >= 100000) {
            engMultiplier = " M";
            value = value / 1000000;
        }

        resistance.setText(value.toString() + engMultiplier + " Ohms +/- " + toleranceBand.toString() );
        resistance.setTextColor(Color.argb(255,0,0,0));
    }

    /**
     * Private function to set the band colors.  It will return an enumerator
     * that can be used to set the value to the band internal to the application.
     *
     * @param band The band that needs to be updated, as an ImageView.  Its backgroundResource
     *             will be set to a value from the values.colors.xml file.
     * @param color The color the band was set to by the user via the spinner object.
     * @return  Returns an enumeration of type ResistorEnum that tells the INT value of the band selected.
     */
    private ResistorEnum setBandColor(ImageView band, TextView label, String color) {
        ResistorBand resBand;
        resBand = getColorResource(color);

        band.setBackgroundResource(resBand.getColorId());
        label.setTextColor(ContextCompat.getColor(this, resBand.getColorId()));


        return resBand.getBandValue();

    }

    private ResistorBand getColorResource(String color){
        int resource;
        ResistorEnum bandValue;

        switch(color) {
            case "BLACK":
                resource = R.color.blackBand;
                bandValue = ResistorEnum.BLACK;
                break;
            case "BROWN":
                resource = R.color.brownBand;
                bandValue = ResistorEnum.BROWN;
                break;
            case "RED":
                resource = R.color.redBand;
                bandValue = ResistorEnum.RED;
                break;
            case "ORANGE":
                resource = R.color.orangeBand;
                bandValue = ResistorEnum.ORANGE;
                break;
            case "YELLOW":
                resource = R.color.yellowBand;
                bandValue = ResistorEnum.YELLOW;
                break;
            case "GREEN":
                resource = R.color.greenBand;
                bandValue = ResistorEnum.GREEN;
                break;
            case "BLUE":
                resource = R.color.blueBand;
                bandValue = ResistorEnum.BLUE;
                break;
            case "VIOLET":
                resource = R.color.violetBand;
                bandValue = ResistorEnum.VIOLET;
                break;
            case "GRAY":
                resource = R.color.grayBand;
                bandValue = ResistorEnum.GRAY;
                break;
            case "WHITE":
                resource = R.color.whiteBand;
                bandValue = ResistorEnum.WHITE;
                break;
            case "GOLD":
                resource = R.color.goldBand;
                bandValue = ResistorEnum.GOLD;
                break;
            case "SILVER":
                resource = R.color.silverBand;
                bandValue = ResistorEnum.SILVER;
                break;
            default:
                resource = R.color.blackBand;
                bandValue = ResistorEnum.BLACK;
                break;
        }

        return new ResistorBand(resource, bandValue);

    };

    /**
     * The incrementBand methods move the color
     * one down the list and update the resistance value.
     * If the resistance value is array.length -1, we need
     * to wrap to 0.
     *
     * We then call the same setBandColor as our listener,
     * and update the resistance.
     * 
     * @param view
     */

    public void incrementBand1 (View view) {
        int i = band1Spinner_3b.getSelectedItemPosition();
        if (i == resColors.length - 1) {
            i = 0;
        } else {
            i+=1;
        }
        band1Spinner_3b.setSelection(i);
        band1 = this.setBandColor(band1_3Band, band1Label, resColors[i]);

        this.updateResistance();
    }

    public void incrementBand2 (View view) {
        int i = band2Spinner_3b.getSelectedItemPosition();
        if (i == resColors.length - 1) {
            i = 0;
        } else {
            i++;
        }
        band2Spinner_3b.setSelection(i);
        band2 = this.setBandColor(band2_3Band, band2Label, resColors[i]);

        this.updateResistance();
    }

    public void incrementBand3 (View view) {
        int i = band3Spinner_3b.getSelectedItemPosition();
        if (i == resColors.length - 3) {
            i = 0;
        } else {
            i+=1;
        }
        band3Spinner_3b.setSelection(i);
        band3 = this.setBandColor(band3_3Band, band3Label, resColors[i]);

        this.updateResistance();
    }

    public void incrementTolerance (View view) {
        int i = toleranceSpinner_3b.getSelectedItemPosition();
        if (i == tolColors.length - 1) {
            i = 0;
        } else {
            i+=1;
        }
        toleranceSpinner_3b.setSelection(i);
        toleranceBand = this.setBandColor(tolerance_3Band, toleranceLabel, tolColors[i]);

        this.updateResistance();
    }

    /**
     * Method fired from a button on screen
     * that manually updates the resistor to some
     * user entered resistance value.
     *
     */

    public void setResistance(View view) {

        double resValue;
        String stringValue = resEntry.getText().toString();

        if (stringValue.length() > 0 ) {
            resValue = Double.valueOf(stringValue);
        } else {
            resValue = 0;
        }
        int tempValue = (int)resValue;
        int resMultiIndex = this.resMultiplierSpinner.getSelectedItemPosition();
        int tolIndex = this.tolEntrySpinner.getSelectedItemPosition();

        if (resValue > 990 || resValue < 1) {
            this.resistance.setText("INVALID ENTRY! CHECK VALUES AND TRY AGAIN");
            this.resistance.setTextColor(Color.argb(255, 255,0,0));
            return;
        }

        // round the resistance value by forcing the double
        // into an int.  i.e. 2.7 = 3 as an int.
        // if the int > double, then we rounded up.  subtract
        // one from the int to get the value of 2.
        // else we rounded down, which gives us the right number.

        // find decimalPlace of the entry

        double decimalPlace = resValue - tempValue;

        /*

        if ((double)tempValue > resValue) {
            decimalPlace = (double)tempValue - resValue;
            decimalPlace = 1.0 - decimalPlace;
        } else {
            decimalPlace = resValue - tempValue;
        }

        */

        // find the hundreds place of the entry.

        double hundreds = (int) (resValue / 100);

        /*
        tempValue = (int)resValue / 100;
        double hundreds;

        if ((double)tempValue > resValue / 100) {
            hundreds = tempValue - 1;

        } else {
            hundreds = tempValue;
        }
        */

        // find the tens place of the entry

        double tens = (int)((resValue-(hundreds*100)) / 10);

        /*
        tempMath = (resValue-(hundreds*100)) / 10;
        tempValue = (int)(tempMath);
        double tens;

        if ((double)tempValue > tempMath) {
            tens = tempValue - 1;
        } else {
            tens = tempValue;
        }
        */

        // find the ones place of the entry.

        double singles = resValue - (hundreds*100) - (tens*10) - decimalPlace;





        /*

         Check to see if the hundreds place is > 0
         if so, then we will move hundreds value into band1,
         and check our multiplier selection.

         ### M Ohms = Violet
         ### K Ohms = Yellow
         ### O Ohms = Brown

        */
        if ((int) hundreds > 0) {
            band1 = this.setBandColor(band1_3Band, band1Label, resColors[(int)hundreds]);
            this.band1Spinner_3b.setSelection((int)hundreds);
            if (resMultiIndex == 2 ) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[7]);
                this.band3Spinner_3b.setSelection(7);
            } else if (resMultiIndex == 1) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[4]);
                this.band3Spinner_3b.setSelection(4);
            } else {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[1]);
                this.band3Spinner_3b.setSelection(1);
            }
            band2 = this.setBandColor(band2_3Band, band2Label, resColors[(int)tens]);
            this.band2Spinner_3b.setSelection((int)tens);
        }

        /*
            Else if the TENS > 0, we move that into band 1
                and the singles into band 2.  Check our multiplier.
                ## M = Blue
                ## K = Orange
                ## Ohms = Black
         */

        else if ((int) tens > 0) {
            band1 = this.setBandColor(band1_3Band, band1Label, resColors[(int)tens]);
            this.band1Spinner_3b.setSelection((int)tens);

            if (resMultiIndex == 2 ) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[6]);
                this.band3Spinner_3b.setSelection(6);
            } else if (resMultiIndex == 1) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[3]);
                this.band3Spinner_3b.setSelection(3);
            } else {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[0]);
                this.band3Spinner_3b.setSelection(0);
            }
            band2 = this.setBandColor(band2_3Band, band2Label, resColors[(int)singles]);
            this.band2Spinner_3b.setSelection((int)singles);

        }

        /*
        ELSE TENS = Band 1 and the Remainder / decimal goes in
        band 2.

        #.# M Ohms = Green
        #.# K Ohms = Red

         */

        else {
            band1 = this.setBandColor(band1_3Band, band1Label, resColors[(int)singles]);
            this.band1Spinner_3b.setSelection((int)singles);

            if (resMultiIndex == 2 ) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[5]);
                this.band3Spinner_3b.setSelection(5);
            } else if (resMultiIndex == 1) {
                band3 = this.setBandColor(band3_3Band, band3Label, resColors[2]);
                this.band3Spinner_3b.setSelection(2);
            }
            band2 = this.setBandColor(band2_3Band, band2Label, resColors[(int)(decimalPlace*10)]);
            this.band2Spinner_3b.setSelection((int)(decimalPlace*10));
        }

        toleranceSpinner_3b.setSelection(tolIndex);
        toleranceBand = this.setBandColor(tolerance_3Band, toleranceLabel, tolColors[tolIndex]);

        this.updateResistance();

    }

    /**
     * Helper method to cleanup the constructor.  Finds and initializes
     * the images used with the bands of the resistor.
     */

    private void setupThreeBandImages() {
        this.band1_3Band = (ImageView) findViewById(R.id.band1_3band);
        this.band2_3Band = (ImageView) findViewById(R.id.band2_3band);
        this.band3_3Band = (ImageView) findViewById(R.id.band3_3band);
        this.tolerance_3Band = (ImageView) findViewById(R.id.toleranceBand);
    }

    /**
     * Helper method to cleanup the constructor.  It finds and initializes the
     * spinners that allow the user to select the band color.
     *
     * It also sets the onItemSelectedListener for the spinners, implemented
     * within the MainActivity.
     */

    private void setupThreeBandSpinners() {
        band1Spinner_3b = (Spinner)findViewById(R.id.band1Spinner_3band);
        band2Spinner_3b = (Spinner)findViewById(R.id.band2Spinner_3band);
        band3Spinner_3b = (Spinner)findViewById(R.id.band3Spinner_3band);
        toleranceSpinner_3b = (Spinner)findViewById(R.id.toleranceSpinner_3band);

        // Setup the first two bands color lists first
        // They go 0 - 9, where the multiplier only goes
        // 0 - 7, and tolerance goes 0 - 1.

        final ArrayList<String> resColorList = new ArrayList<String>();

        for (int i = 0; i < resColors.length; i++) {
            resColorList.add(resColors[i]);
        }

        ArrayAdapter<String> resColorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resColorList);

        band1Spinner_3b.setAdapter(resColorAdapter);
        band1Spinner_3b.setOnItemSelectedListener(this);

        band2Spinner_3b.setAdapter(resColorAdapter);
        band2Spinner_3b.setOnItemSelectedListener(this);

        // Setup the multiplier list with the same colors
        // as the resistance values, minus 2 (GRAY, WHTIE)

        final ArrayList<String> multiplierColorList = new ArrayList<String>();

        for (int i = 0; i < resColors.length - 2; i++){
            multiplierColorList.add(resColors[i]);
        }

        ArrayAdapter<String> multiplierColorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, multiplierColorList);

        band3Spinner_3b.setAdapter(multiplierColorAdapter);
        band3Spinner_3b.setOnItemSelectedListener(this);

        // Setup the Tolerance list and adapter from the
        // tolColors array

        final ArrayList<String> tolColorList = new ArrayList<String> ();

        for (int i = 0; i < tolColors.length; i++) {
            tolColorList.add(tolColors[i]);
        }

        final ArrayAdapter<String> tolColorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tolColorList);

        toleranceSpinner_3b.setAdapter(tolColorAdapter);
        toleranceSpinner_3b.setOnItemSelectedListener(this);

        // Setup spinners for the manual resistance entry!!!!
        // They will not use listeners -- a manual button will
        // get the selected items and do the updates so the user
        // can update all information in the boxes before checking
        // the color for that resistance.

        resMultiplierSpinner = (Spinner) findViewById(R.id.resMultiplierSpinner);

        final ArrayList<String> resMultiplierStrings = new ArrayList<String> ();

        for (int i = 0; i < resMultipliers.length; i++) {
            resMultiplierStrings.add(resMultipliers[i]);
        }

        final ArrayAdapter<String> resMultiplierAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, resMultiplierStrings);

        resMultiplierSpinner.setAdapter(resMultiplierAdapter);

        // Setup the tolerance selection spinner

        tolEntrySpinner = (Spinner) findViewById(R.id.tolEntrySpinner);

        final ArrayList<String> tolEntryStrings = new ArrayList<String>();

        for (int i = 0; i < tolEntries.length; i++) {
            tolEntryStrings.add(tolEntries[i]);
        }

        final ArrayAdapter<String> tolEntryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tolEntryStrings);

        tolEntrySpinner.setAdapter(tolEntryAdapter);


    }

    /**
     * This initializes and setups the enumerations that hold
     * the values of the bands.
     */

    private void setupEnums() {
        band1 = ResistorEnum.BLACK;
        band2 = ResistorEnum.BLACK;
        band3 = ResistorEnum.BLACK;
        band4 = ResistorEnum.BLACK;
        toleranceBand = ResistorEnum.GOLD;

        this.updateResistance();
    }
}
