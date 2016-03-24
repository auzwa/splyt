package com.example.android.splitr;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.inputmethod.InputMethodManager;



import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

// Main activity inherits from Activity
public class MainActivity extends Activity implements OnClickListener, OnFocusChangeListener, OnSeekBarChangeListener {

    // Variable declarations go here.
    private double grandTotal;
    private double totalTip;
    private double tax;
    private double taxPercentValue;
    private EditText taxDollar;
    private TextView tipDollar;
    private TextView textGTotal;
    private double tipPercentValue;
    private TextView tipPercent;
    private SeekBar tipSlider;
    private TableLayout mainTable;
    private Button addDinerButton;
    private EditText firstCustomer;
    private EditText amount1of1;
    private ImageButton addMeal;
    private TextView customerAmount;
    private TextView customerName;
    private ArrayList<Diner> dinerList;



    // Replace the onCreate method we inherited from Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // First run Activity's version of onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grandTotal = 0.0;
        tipPercentValue = 0.15;
        taxPercentValue = 0.0;
        totalTip = 0.0;
        tax = 0.0;



        mainTable = (TableLayout) findViewById(R.id.mainTable);
        addDinerButton = (Button) findViewById(R.id.addDinerButton);
        firstCustomer = (EditText) findViewById(R.id.firstCustomer);
        amount1of1 = (EditText) findViewById(R.id.amount1of1);
        addMeal = (ImageButton) findViewById(R.id.addMeal);
        customerName = (TextView) findViewById(R.id.customerName);
        customerAmount = (TextView) findViewById(R.id.customerAmount);
        tipPercent = (TextView) findViewById(R.id.tipPercent);
        tipSlider = (SeekBar) findViewById(R.id.tipSlider);
        tipDollar = (TextView) findViewById(R.id.tipDollar);
        textGTotal = (TextView) findViewById(R.id.textGTotal);
        taxDollar = (EditText) findViewById(R.id.taxDollar);




        dinerList = new ArrayList<>();
        Diner diner = new Diner(firstCustomer, amount1of1, addMeal, customerName, customerAmount);
        dinerList.add(diner);
        firstCustomer.setOnFocusChangeListener(this);
        amount1of1.setOnFocusChangeListener(this);
        addDinerButton.setOnClickListener(this);
        addMeal.setOnClickListener(this);
        tipSlider.setOnSeekBarChangeListener(this);
        tipPercent.setOnFocusChangeListener(this);
        taxDollar.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addDinerButton) {
            TableRow row1 = new TableRow(this);
            EditText et1 = new EditText(this);
            et1.setText("Diner");
            et1.setSelectAllOnFocus(true);
            et1.setInputType(firstCustomer.getInputType());
            et1.setBackground(firstCustomer.getBackground());
            et1.setTextSize(16);
            et1.setGravity(firstCustomer.getGravity());
            et1.setLayoutParams(firstCustomer.getLayoutParams());
            et1.setWidth(firstCustomer.getWidth());
            et1.setOnFocusChangeListener(this);
            et1.requestFocus();
            EditText et2 = new EditText(this);
            et2.setText("0.00");
            et2.setSelectAllOnFocus(true);
            et2.setInputType(amount1of1.getInputType());
            et2.setBackground(amount1of1.getBackground());
            et2.setGravity(amount1of1.getGravity());
            et2.setTextSize(16);
            et2.setLayoutParams(amount1of1.getLayoutParams());
            et2.setWidth(amount1of1.getWidth());
            et2.setOnFocusChangeListener(this);
            et2.requestFocus();

            ImageButton ib = new ImageButton(this);
            ib.setImageResource(R.drawable.ic_addmeal);
            ib.setOnClickListener(this);
            ib.setBackgroundColor(Color.rgb(251, 251, 251));
            row1.addView(et1);
            row1.addView(et2);
            row1.addView(ib);
            int rowIndex = 1;
            for (int i = 0; i < dinerList.size(); i++) {
                rowIndex += dinerList.get(i).orderList.size();
            }

            mainTable.addView(row1, rowIndex);
            TableRow row2 = new TableRow(this);
            TextView tv1 = new TextView(this);
            tv1.setText(et1.getText().toString());
            tv1.setGravity(customerName.getGravity());
            tv1.setTextSize(16);
            tv1.setTextColor(getColor(R.color.colorPrimary));
            TextView tv2 = new TextView(this);
            tv2.setText(et2.getText().toString());
            tv2.setGravity(customerAmount.getGravity());
            tv2.setTextColor(getColor(R.color.colorPrimary));
            tv2.setTextSize(16);
            row2.addView(tv1);
            row2.addView(tv2);
            mainTable.addView(row2, rowIndex + 10 + dinerList.size());
            Diner diner = new Diner(et1, et2, ib, tv1, tv2);
            dinerList.add(diner);
        } else {
            for (int i = 0; i < dinerList.size(); i++) {
                if (v == dinerList.get(i).ibAddItem) {
                    TableRow row3 = new TableRow(this);
                    EditText emptyEditText = new EditText(this);
                    emptyEditText.setVisibility(View.INVISIBLE);
                    EditText newOrder = new EditText(this);
                    newOrder.setText("0.00");
                    newOrder.setSelectAllOnFocus(true);
                    newOrder.setInputType(amount1of1.getInputType());
                    newOrder.setBackground(amount1of1.getBackground());
                    newOrder.setGravity(amount1of1.getGravity());
                    newOrder.setTextSize(16);
                    newOrder.setLayoutParams(amount1of1.getLayoutParams());
                    newOrder.setOnFocusChangeListener(this);
                    newOrder.requestFocus();
                    row3.addView(emptyEditText);
                    row3.addView(newOrder);
                    int rowIndex2 = 1;
                    for (int j = 0; j <= i; j++) {
                        rowIndex2 += dinerList.get(j).orderList.size();
                    }
                    mainTable.addView(row3, rowIndex2);
                    dinerList.get(i).newOrder(newOrder);
                }
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == tipPercent && hasFocus == false){
            tipPercentValue = Double.parseDouble(((EditText) v).getText().toString().replace("$", "").replace("%", ""));
            tipSlider.setProgress((int) tipPercentValue);
            if (tipPercentValue > 0.5) {
                tipPercentValue = tipPercentValue / 100;

            }

        } else if (v == taxDollar && hasFocus == false) {
            tax = Double.parseDouble(((EditText) v).getText().toString().replace("$", "").replace(",",""));
            ((EditText) v).setText("$" + String.format("%,.2f", tax));
            calcGrandTotal();

        } else {




        }
        for (int i = 0; i < dinerList.size(); i++) {
            if (v == dinerList.get(i).etName && hasFocus == false) {
                dinerList.get(i).setName();
            } else {
                for (int j = 0; j < dinerList.get(i).orderList.size(); j++)
                {
                    if (v == dinerList.get(i).orderList.get(j) && hasFocus == false) ;
                }
                try {
                    Double.parseDouble(((EditText) v).getText().toString().replace("$", ""));
                    dinerList.get(i).updateTotal((EditText) v, tipPercentValue);
                } catch (Exception ex) {

                }
                calcGrandTotal();

            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == tipSlider) {
            tipPercentValue = progress;
            tipPercent.setText(String.format("%.0f", tipPercentValue)+"%");
            tipPercentValue = tipPercentValue/100;


            calcGrandTotal();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    //not needed
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    //not needed
    }

    public void calcGrandTotal() {
        grandTotal = 0.0;
        for (int i = 0; i < dinerList.size(); i++) {
            grandTotal += dinerList.get(i).total;
    }
        taxPercentValue = tax / grandTotal;
        for (int i = 0; i < dinerList.size(); i++) {
            dinerList.get(i).setTotalText(tipPercentValue, taxPercentValue);
        }
        totalTip = grandTotal * tipPercentValue;
        tipDollar.setText("$" + String.format("%,.2f", totalTip));
        textGTotal.setText("$" + String.format("%,.2f", grandTotal + totalTip + tax));
    }
}


